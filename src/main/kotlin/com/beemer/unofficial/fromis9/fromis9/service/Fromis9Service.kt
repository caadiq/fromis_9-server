package com.beemer.unofficial.fromis9.fromis9.service

import com.beemer.unofficial.fromis9.common.exception.CustomException
import com.beemer.unofficial.fromis9.common.exception.ErrorCode
import com.beemer.unofficial.fromis9.common.utils.RedisUtil
import com.beemer.unofficial.fromis9.fromis9.dto.*
import com.beemer.unofficial.fromis9.fromis9.entity.Members
import com.beemer.unofficial.fromis9.fromis9.entity.Socials
import com.beemer.unofficial.fromis9.fromis9.repository.BannerImageRepository
import com.beemer.unofficial.fromis9.fromis9.repository.MemberRepository
import com.beemer.unofficial.fromis9.fromis9.repository.SocialRepository
import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import java.text.SimpleDateFormat

@Service
class Fromis9Service(
    private val memberRepository: MemberRepository,
    private val socialRepository: SocialRepository,
    private val bannerImageRepository: BannerImageRepository,
    private val webClient: WebClient,
    private val redisUtil: RedisUtil
) {
    @Value("\${pledis.profile.url}")
    private lateinit var pledisProfileUrl: String

    @Value("\${pledis.file.url}")
    private lateinit var pledisFileUrl: String

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
            Socials("facebook", dto.facebook),
            Socials("twitter", dto.twitter),
            Socials("youtube", dto.youtube),
            Socials("insta", dto.insta)
        )

        socialRepository.saveAll(socialsList)

        val membersList = dto.profileList.map { profile ->
            Members(
                name = profile.subject,
                birth = SimpleDateFormat("yyyy.MM.dd").parse(profile.data3),
                profileImage = "$pledisFileUrl${profile.bbsFile}"
            )
        }

        memberRepository.saveAll(membersList)

        redisUtil.apply {
            setData("fromis9_detail", dto.detail)
            setData("fromis9_debut", dto.content1)
        }
    }

    fun getFromis9() : ResponseEntity<Fromis9Dto> {
        val bannerImages = bannerImageRepository.findAll().map { it.imageUrl }
        val detail = redisUtil.getData("fromis9_detail") ?: ""
        val debut = redisUtil.getData("fromis9_debut") ?: ""
        val socials = socialRepository.findAll().map { Social(it.sns, it.url) }
        val members = memberRepository.findAll().sortedBy { it.birth }.map { Member(it.name, it.profileImage) }

        val fromis9Dto = Fromis9Dto(
            bannerImages = bannerImages,
            detail = detail,
            debut = debut,
            socials = socials,
            members = members
        )

        return ResponseEntity.ok(fromis9Dto)
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