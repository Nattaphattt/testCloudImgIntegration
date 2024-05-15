package com.demooms.demooms.repository

import com.demooms.demooms.entities.UserEntity
import jakarta.transaction.Transactional
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface UserEntityRepository : JpaRepository<UserEntity, Long>{

    @Query("from UserEntity u where u.username like %:uname%")
    fun findByUsername(uname: String): List<UserEntity>

    @Query("from UserEntity u where u.username like %:uname% and u.firstName like %:fname% and u.lastName like %:lname%")
    fun findFiltered(uname: String, fname: String, lname: String): List<UserEntity>

//    @Quert("from ")
//    @Modifying(clearAutomatically = true)
//    @Transactional
//    @Query("")
//    fun addUser(userEntity: UserEntity)
}