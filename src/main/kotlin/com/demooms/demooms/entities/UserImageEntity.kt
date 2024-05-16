package com.demooms.demooms.entities

import jakarta.persistence.*

@Table(name = "userImages")
@Entity
data class UserImageEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    val id: Long,
    @Column(name = "user_id")
    val userId : Long,
    @Column(name = "image_url")
    val imageUrl: String


)
