package com.demooms.demooms.controller

import com.demooms.demooms.entities.UserEntity
import com.demooms.demooms.service.UserService
import org.springframework.beans.factory.annotation.Autowired

import org.springframework.web.bind.annotation.*
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import org.springframework.web.multipart.MultipartFile
import java.io.File
import javax.imageio.ImageIO

@Serializable
@RestController
@RequestMapping("/test")
@CrossOrigin(origins = ["*"])
class UserController {
    // dependency injection
    @Autowired
    lateinit var  userService: UserService

    @GetMapping("/getAll")
    fun findAllUser(): List<UserEntity> {
        val allUsers = userService.findAllUser()

//        val allUserMapJson: MutableList<JsonObject> = mutableListOf()`
//
//        for (user in allUsers) {
//            allUserMapJson.add(
//                JsonObject(mapOf(
//                "msg" to "OK".toJsonElement()
//            ))
//            )
//        }

//        return allUserMapJson.toString();
        return allUsers
    }

    @GetMapping("/getFilteredUser")
    fun findFilteredUser(@RequestParam id: Long?, userName: String, firstName: String, lastName: String): List<UserEntity> {
        return userService.findFilteredUsers(id, userName, firstName, lastName)
    }

//    @GetMapping("/getUser")
//    fun getUser(@RequestParam id: Long): String() {
//        return service
//    }

    @GetMapping("/user/{uname}")
    fun getUserByUsername(@PathVariable("uname") uname: String): List<UserEntity> {
        return userService.findUserByUsername(uname)
    }

    @PostMapping("/addUser")
    fun addUser(@RequestBody user: UserEntity) {
        userService.createUser(user)
    }

    @DeleteMapping("/deleteUser")
    fun deleteUser(@RequestParam id: Long) {
        userService.deleteUser(id);
    }

    @GetMapping("/testUploadImages")
    fun testuploadImage() : String {
        userService.testUploadImage()
        return "ok maybe"
    }

    @GetMapping("/getAllImages")
    fun getAllImages(): List<String> {
        return userService.retrieveAllImages()
    }

    @PostMapping("/addImage")
    fun addSomeImage(@RequestBody image: MultipartFile, @RequestParam id: String): List<String> {
        return userService.uploadSomeImage(image, id)
    }
}