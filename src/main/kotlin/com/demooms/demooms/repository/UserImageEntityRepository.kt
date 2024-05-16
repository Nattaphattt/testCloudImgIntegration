package com.demooms.demooms.repository

import com.demooms.demooms.entities.UserImageEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserImageEntityRepository: JpaRepository<UserImageEntity, Long> {

}