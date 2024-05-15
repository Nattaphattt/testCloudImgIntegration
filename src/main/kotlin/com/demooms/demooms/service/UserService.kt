package com.demooms.demooms.service

import com.demooms.demooms.entities.UserEntity


interface UserService {
    fun findAllUser(): List<UserEntity>
    fun findUserByUsername(uname: String): List<UserEntity>
    fun createUser(user: UserEntity): UserEntity
    fun deleteUser(id: Long)
    fun findFilteredUsers(id: Long?, userName: String, firstName: String, lastName: String): List<UserEntity>
    fun testUploadImage()
    fun retrieveAllImages(): List<String>
}