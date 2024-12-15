package com.beemer.unofficial.fromis9.fromis9.controller

import com.beemer.unofficial.fromis9.fromis9.dto.Fromis9Dto
import com.beemer.unofficial.fromis9.fromis9.dto.MemberProfileDto
import com.beemer.unofficial.fromis9.fromis9.service.Fromis9Service
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class Fromis9Controller(private val fromis9Service: Fromis9Service) {

    @GetMapping("/fromis9")
    fun getFromis9() : ResponseEntity<Fromis9Dto> {
        return fromis9Service.getFromis9()
    }

    @GetMapping("/fromis9/profile/{name}")
    fun getMemberProfile(@PathVariable name: String) : ResponseEntity<MemberProfileDto> {
        return fromis9Service.getMemberProfile(name)
    }
}