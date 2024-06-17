package com.beemer.unofficial.fromis9.changelog.controller

import com.beemer.unofficial.fromis9.changelog.dto.ChangelogListDto
import com.beemer.unofficial.fromis9.changelog.dto.LatestVersionDto
import com.beemer.unofficial.fromis9.changelog.service.ChangelogService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/fromis9/changelog/")
class ChangelogController(private val changelogService: ChangelogService) {

    @GetMapping("/list")
    fun getChangelogList() : ResponseEntity<List<ChangelogListDto>> {
        return changelogService.getChangelogList()
    }

    @GetMapping("/latest")
    fun getLatestVersion() : ResponseEntity<LatestVersionDto> {
        return changelogService.getLatestVersion()
    }
}