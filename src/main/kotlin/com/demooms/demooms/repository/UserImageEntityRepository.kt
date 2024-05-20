package com.demooms.demooms.repository

import com.demooms.demooms.entities.UserImageEntity
import jakarta.transaction.Transactional
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface UserImageEntityRepository: JpaRepository<UserImageEntity, Long> {
    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("delete from UserImageEntity where userId = :userId and imagePublicId = :imgId")
    fun deleteByUsrIdAndPublicId(@Param("userId") userId: Long, @Param("imgId") imgId: String)
}