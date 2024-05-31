package com.beemer.unofficial.fromis9.fromis9.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class Fromis9ResponseDto(
    val facebook: String,
    val twitter: String,
    val youtube: String,
    val insta: String,
    val detail: String,
    val content1: String,
    @JsonProperty("profile_list") val profileList: List<ProfileList>
)

data class ProfileList(
    val subject: String,
    @JsonProperty("bbs_file") val bbsFile: String,
    val data3: String
)
