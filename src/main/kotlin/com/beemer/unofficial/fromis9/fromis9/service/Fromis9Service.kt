package com.beemer.unofficial.fromis9.fromis9.service

import com.beemer.unofficial.fromis9.album.dto.AlbumListDto
import com.beemer.unofficial.fromis9.album.repository.AlbumRepository
import com.beemer.unofficial.fromis9.common.exception.CustomException
import com.beemer.unofficial.fromis9.common.exception.ErrorCode
import com.beemer.unofficial.fromis9.common.utils.RedisUtil
import com.beemer.unofficial.fromis9.fromis9.dto.*
import com.beemer.unofficial.fromis9.fromis9.entity.Members
import com.beemer.unofficial.fromis9.fromis9.entity.Socials
import com.beemer.unofficial.fromis9.fromis9.repository.MemberRepository
import com.beemer.unofficial.fromis9.fromis9.repository.SocialRepository
import com.beemer.unofficial.fromis9.news.repository.DcinsidePostRepository
import com.beemer.unofficial.fromis9.news.repository.WeverseNoticeRepository
import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter

@Service
class Fromis9Service(
    private val memberRepository: MemberRepository,
    private val socialRepository: SocialRepository,
    private val albumRepository: AlbumRepository,
    private val weverseNoticeRepository: WeverseNoticeRepository,
    private val dcinsidePostRepository: DcinsidePostRepository,
    private val webClient: WebClient,
    private val redisUtil: RedisUtil
) {
    @Value("\${pledis.profile.url}")
    private lateinit var pledisProfileUrl: String

    @Value("\${pledis.file.bbsData}")
    private lateinit var pledisFileBbsData: String

    @Value("\${pledis.file.goodsImages}")
    private lateinit var pledisFileGoodsImages: String

    private val formatter = DateTimeFormatter.ofPattern("yyyy")

    @Transactional
    fun fetchFromis9Profile() {
        webClient.get()
            .uri(pledisProfileUrl)
            .retrieve()
            .bodyToMono(Fromis9ResponseDto::class.java)
            .subscribe(this::saveFromis9Profile)
    }

    private fun saveFromis9Profile(dto: Fromis9ResponseDto) {
        val socialsList = listOf(
            Socials("FaceBook", dto.facebook),
            Socials("X", dto.twitter),
            Socials("YouTube", dto.youtube),
            Socials("Instagram", dto.insta)
        )

        socialRepository.saveAll(socialsList)

        val membersList = dto.profileList.map { profile ->
            Members(
                name = profile.subject,
                birth = SimpleDateFormat("yyyy.MM.dd").parse(profile.data3),
                profileImage = "$pledisFileBbsData${profile.bbsFile}"
            )
        }

        memberRepository.saveAll(membersList)

        redisUtil.apply {
            setData("fromis9_image", "$pledisFileGoodsImages${dto.listImg}")
            setData("fromis9_debut", dto.content1)
        }
    }

    fun getFromis9() : ResponseEntity<Fromis9Dto> {
        val bannerImage = redisUtil.getData("fromis9_image") ?: ""
        val debut = redisUtil.getData("fromis9_debut") ?: ""
        val socials = socialRepository.findAll().map { Social(it.sns, it.url) }
        val members = memberRepository.findAll().sortedBy { it.birth }.map { Member(it.name, it.profileImage) }
        val pageable = PageRequest.of(0, 3, Sort.by(Sort.Direction.DESC, "release"))
        val albums = albumRepository.findAll(pageable).content.map { AlbumListDto(it.albumName, it.type, it.cover, it.release.format(formatter), it.colorMain, it.colorPrimary, it.colorSecondary) }

        val weverseNotice = weverseNoticeRepository.findTop10ByOrderByDateDesc().map {
            LatestNews(
                it.noticeId,
                it.title,
                it.url,
                it.date,
                it.portal.portal,
                it.portal.image
            )
        }

        val dcinsidePosts = dcinsidePostRepository.findTop10ByOrderByDateDesc().map {
            LatestNews(
                it.liveId,
                it.title,
                it.url,
                it.date,
                it.portal.portal,
                it.portal.image
            )
        }

        val latestNews = (weverseNotice + dcinsidePosts)
            .sortedByDescending { it.date }
            .take(5)

        val fromis9Dto = Fromis9Dto(
            bannerImage = bannerImage,
            debut = debut,
            socials = socials,
            members = members,
            albums = albums,
            latestNews = latestNews
        )

        return ResponseEntity.status(HttpStatus.OK).body(fromis9Dto)
    }

    fun getMemberProfile(name: String) : ResponseEntity<MemberProfileDto> {
        val member = memberRepository.findById(name)
            .orElseThrow { throw CustomException(ErrorCode.MEMBER_NOT_FOUND) }

        val memberProfile = MemberProfileDto(
            name = member.name,
            birth = SimpleDateFormat("yyyy.MM.dd").format(member.birth),
            profileImage = member.profileImage,
            position = member.details?.position,
            instagram = member.details?.instagram
        )

        return ResponseEntity.status(HttpStatus.OK).body(memberProfile)
    }
}