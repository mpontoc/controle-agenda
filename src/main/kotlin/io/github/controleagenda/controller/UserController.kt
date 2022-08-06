package io.github.controleagenda.controller

import io.github.controleagenda.model.User
import io.github.controleagenda.services.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping("/user")
class UserController {

    @Autowired
    lateinit var userService: UserService

    @GetMapping()
    fun getAllUsers() =
        userService.getAllUsers()

    @GetMapping("/{id}")
    fun getUserById(@PathVariable id: Long): Optional<User?> {

        return userService.getUserById(id)
    }
}