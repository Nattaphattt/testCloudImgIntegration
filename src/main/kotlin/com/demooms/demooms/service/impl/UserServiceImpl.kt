package com.demooms.demooms.service.impl

import com.cloudinary.Cloudinary
import com.cloudinary.utils.ObjectUtils
import com.demooms.demooms.entities.UserEntity
import com.demooms.demooms.entities.UserImageEntity
import com.demooms.demooms.repository.UserEntityRepository
import com.demooms.demooms.repository.UserImageEntityRepository
import com.demooms.demooms.service.UserService
import jakarta.persistence.Column
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.io.IOException
import javax.imageio.ImageIO


@Service
class UserServiceImpl : UserService {
    @Autowired
    lateinit var userEntityRepository: UserEntityRepository

    @Autowired
    lateinit var userImageEntityRepository: UserImageEntityRepository

    private val cloudinary = Cloudinary("cloudinary://621514455121694:YD01JCqZa_XU5prqvUwgSbJGQSo@dq27kvbkg")

    override fun findAllUser(): List<UserEntity> {
        return userEntityRepository.findAll()
    }

    override fun findUserByUsername(uname: String): List<UserEntity>  {
        return userEntityRepository.findByUsername(uname)
    }

    override fun createUser(user: UserEntity): UserEntity {
        return userEntityRepository.save(user)
    }

    override fun deleteUser(id: Long) {
        return userEntityRepository.deleteById(id)
    }

    override fun findFilteredUsers(id: Long?, userName: String, firstName: String, lastName: String): List<UserEntity> {
        val findById: MutableList<UserEntity> = mutableListOf()
        //bug above don't forget fix
        id?: return userEntityRepository.findFiltered(userName, firstName, lastName)
        if (userEntityRepository.findById(id).isPresent) findById.add(userEntityRepository.findById(id).get())
        return findById
    }

    override fun testUploadImage() {
        cloudinary.uploader().upload("https://upload.wikimedia.org/wikipedia/commons/a/ae/Olympic_flag.jpg",
            ObjectUtils.asMap("public_id", "olympic_flag"));
    }

    override fun retrieveAllImages() : List<Map<String,String>> {
        var resBody: Any;
        try {
            val apiResponse = cloudinary.search().execute()
            resBody = apiResponse.get("resources").toString()
        } catch (exception: IOException) {
            return listOf(mapOf("msg" to exception.message!!))
        }

        val trimmedData = resBody.trim('[', ']')
        val keyValuePairs = trimmedData.split("}, {")
        val addressList: MutableList<Map<String, String>> = mutableListOf()

        for (item in keyValuePairs) {
            val url = item.trim().substringAfter("url=").substringBefore(", created_at")
            val publicId = item.trim().substringAfter("public_id=").substringBefore(", created_at")

            val address: Map<String, String> = mapOf(
                "url" to url,
                "public_id" to publicId
            )
            addressList.add(address)
        }

        return addressList;
    }

    override fun uploadSomeImage(img: MultipartFile, publicId: String, userId: Long): List<String> {

        val params = ObjectUtils.asMap(
            "public_id", publicId,

        )
        val uploadResult = cloudinary.uploader().upload(img.bytes, params)
        if(uploadResult["secure_url"] != null) userImageEntityRepository.save(
            UserImageEntity(
                userId,
            userId,
            uploadResult["secure_url"] as String,
            publicId)
        )
        return listOf(uploadResult["secure_url"] as String)
    }

    override fun deleteImage(userID: Long, imagePublicId: String): List<String> {

        val deleteResult = cloudinary.uploader().destroy(imagePublicId, null)
        if(deleteResult != null) userImageEntityRepository.deleteByUsrIdAndPublicId(
            userID, imagePublicId
        )
        return listOf(deleteResult["result"] as String);

    }
}