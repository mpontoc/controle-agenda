package io.github.controleagenda.services

import io.github.controleagenda.model.User
import io.github.controleagenda.model.dto.UserDTO

interface UserService {

    fun addUser(user: User): UserDTO
    fun deleteUser(id: Long)
    fun getUserById(id: Long): UserDTO
    fun getAllUsers(): List<User?>
    fun updateUser(id: Long, user: User): User
}