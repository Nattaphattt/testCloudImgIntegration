package com.demooms.demooms.service.impl

import com.cloudinary.Cloudinary
import com.cloudinary.utils.ObjectUtils
import com.demooms.demooms.entities.UserEntity
import com.demooms.demooms.repository.UserEntityRepository
import com.demooms.demooms.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.io.IOException
import java.util.Objects


@Service
class UserServiceImpl : UserService {
    @Autowired
    lateinit var userEntityRepository: UserEntityRepository

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

    override fun retrieveAllImages() : List<String> {
        var resBody: Any;
        try {
            val apiResponse = cloudinary.search().execute()
            resBody = apiResponse.get("resources").toString()!!
        } catch (exception: IOException) {
            return listOf(exception.message!!)
        }

        val trimmedData = resBody.trim('[', ']')
        var keyValuePairs = trimmedData.split("}, {")
        var addressList: MutableList<String> = mutableListOf()

        for (keyValuePair in keyValuePairs) {
            addressList.add(keyValuePair.trim().substringAfter("url=").substringBefore(", created_at"))
        }
        return addressList;

    }
}