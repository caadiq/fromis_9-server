package com.beemer.unofficial.fromis9.changelog.service

import com.beemer.unofficial.fromis9.changelog.dto.Changelog
import com.beemer.unofficial.fromis9.changelog.dto.ChangelogListDto
import com.beemer.unofficial.fromis9.changelog.repository.AppVersionRepository
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
class ChangelogService(
    private val appVersionRepository: AppVersionRepository
) {
    fun getChangelogList() : ResponseEntity<List<ChangelogListDto>> {
        val appVersions = appVersionRepository.findAll()
        val changelogList = appVersions.map {
            ChangelogListDto(
                version = it.version,
                date = it.date,
                changelog = it.changelogs.map { changelog ->
                    Changelog(
                        icon = changelog.type.icon,
                        type = changelog.type.type,
                        feature = changelog.feature
                    )
                }
            )
        }
        return ResponseEntity.ok(changelogList)
    }
}