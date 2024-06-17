package com.beemer.unofficial.fromis9.news.entity

import jakarta.persistence.*

@Entity
@Table(name = "Portals")
data class Portals(
    @Id
    @Column(name = "portal", nullable = false)
    val portal: String,

    @Column(name = "image", nullable = false)
    val image: String,

    @OneToMany(mappedBy = "portal", cascade = [CascadeType.ALL])
    val dcinsidePosts: List<DcinsidePosts>,

    @OneToMany(mappedBy = "portal", cascade = [CascadeType.ALL])
    val weverseNotice: List<WeverseNotice>
)
