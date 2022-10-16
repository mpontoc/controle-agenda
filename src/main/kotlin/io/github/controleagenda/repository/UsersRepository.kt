package io.github.controleagenda.repository

import io.github.controleagenda.model.Users
import io.github.controleagenda.model.dto.UsersDTO
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository


@Repository
interface UsersRepository : JpaRepository<Users?, Long?> {

    override fun getById(id: Long): Users
    fun findUsersById(id: Long): UsersDTO

}