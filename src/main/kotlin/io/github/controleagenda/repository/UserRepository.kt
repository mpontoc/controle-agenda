package io.github.controleagenda.repository

import io.github.controleagenda.model.User
import io.github.controleagenda.model.dto.UserDTO
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository


@Repository
interface UserRepository : JpaRepository<User?, Long?> {

    override fun getById(id: Long): User
    fun findUserById(id: Long): UserDTO

}