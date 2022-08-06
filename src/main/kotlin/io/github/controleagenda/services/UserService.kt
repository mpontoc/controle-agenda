package io.github.controleagenda.services

import io.github.controleagenda.model.User
import java.util.*

interface UserService {

    fun addUser(user: User): User
    fun deleteUser(id: Long)
    fun getUserById(id: Long): Optional<User?>
    fun getAllUsers(): List<User?>
    fun updateUser(id: Long, user: User): User
}