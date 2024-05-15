package com.demooms.demooms.entities

import jakarta.persistence.*

@Table(name = "users")
@Entity
//Entity directly connect to database but dto is more flexible
// DTO เอาไว้ใส่ข้อมูลบางส่วนที่ต้องการ query, response
data class UserEntity (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    val id: Long,
    @Column(name = "first_name")
    val firstName: String,
    @Column(name = "last_name")
    val lastName: String,
    @Column(name = "username")
    val username: String,

)