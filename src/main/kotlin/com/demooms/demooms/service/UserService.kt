package com.demooms.demooms.service

import com.demooms.demooms.entities.UserEntity
import org.springframework.web.multipart.MultipartFile
import java.io.File
import javax.imageio.ImageIO


interface UserService {
    fun findAllUser(): List<UserEntity>
    fun findUserByUsername(uname: String): List<UserEntity>
    fun createUser(user: UserEntity): UserEntity
    fun deleteUser(id: Long)
    fun findFilteredUsers(id: Long?, userName: String, firstName: String, lastName: String): List<UserEntity>
    fun testUploadImage()
    fun retrieveAllImages(): List<Map<String,String>>
    fun uploadSomeImage(img: MultipartFile, publicId: String, userId: Long): List<String>
    fun deleteImage(userID: Long, imagePublicId: String): List<String>
}