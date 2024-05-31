package com.beemer.unofficial.fromis9.fromis9.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "Socials")
data class Socials(
    @Id
    @Column(name = "sns", nullable = false)
    var sns: String,

    @Column(name = "url", nullable = false)
    var url: String
)
