package com.beemer.unofficial.fromis9.fromis9.service

import com.beemer.unofficial.fromis9.album.dto.AlbumListDto
import com.beemer.unofficial.fromis9.album.repository.AlbumRepository
import com.beemer.unofficial.fromis9.common.exception.CustomException
import com.beemer.unofficial.fromis9.common.exception.ErrorCode
import com.beemer.unofficial.fromis9.common.utils.RedisUtil
import com.beemer.unofficial.fromis9.fromis9.dto.*
import com.beemer.unofficial.fromis9.fromis9.repository.MemberRepository
import com.beemer.unofficial.fromis9.fromis9.repository.SocialRepository
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import java.time.format.DateTimeFormatter

@Service
class Fromis9Service(
    private val memberRepository: MemberRepository,
    private val socialRepository: SocialRepository,
    private val albumRepository: AlbumRepository,
    private val redisUtil: RedisUtil
) {
    private val formatter = DateTimeFormatter.ofPattern("yyyy")

    fun getFromis9() : ResponseEntity<Fromis9Dto> {
        val debut = redisUtil.getData("fromis9_debut") ?: ""
        val end = redisUtil.getData("fromis9_end") ?: ""
        val socials = socialRepository.findAll().map { Social(it.sns, it.url) }
        val members = memberRepository.findAll().sortedBy { it.birth }.map { Member(it.name, it.profileImage) }
        val pageable = PageRequest.of(0, 3, Sort.by(Sort.Direction.DESC, "release"))
        val albums = albumRepository.findAll(pageable).content.map { AlbumListDto(it.albumName, it.type, it.cover, it.release.format(formatter), it.colorMain, it.colorPrimary, it.colorSecondary) }

        val fromis9Dto = Fromis9Dto(
            debut = debut,
            end = end,
            socials = socials,
            members = members,
            albums = albums
        )

        return ResponseEntity.status(HttpStatus.OK).body(fromis9Dto)
    }

    fun getMemberProfile(name: String) : ResponseEntity<MemberProfileDto> {
        val member = memberRepository.findById(name)
            .orElseThrow { throw CustomException(ErrorCode.MEMBER_NOT_FOUND) }

        val memberProfile = MemberProfileDto(
            name = member.name,
            birth = member.birth,
            profileImage = member.profileImage,
            position = member.details?.position,
            instagram = member.details?.instagram
        )

        return ResponseEntity.status(HttpStatus.OK).body(memberProfile)
    }
}