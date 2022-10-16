package io.github.controleagenda.services

import io.github.controleagenda.model.Users
import io.github.controleagenda.model.dto.UsersDTO

interface UsersService {

    fun addusers(users: Users): UsersDTO
    fun deleteusers(id: Long)
    fun getusersById(id: Long): UsersDTO
    fun getAllusers(): List<Users?>
    fun updateusers(id: Long, users: Users): Users
}