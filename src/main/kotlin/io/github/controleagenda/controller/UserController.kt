package io.github.controleagenda.controller

import io.github.controleagenda.model.User
import io.github.controleagenda.services.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import java.util.*
import javax.persistence.Id
import javax.websocket.server.PathParam

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


//    @PostMapping()
//
////    fun createSegments(@RequestBody user: User): User? {
////
////
////
////        val userRetorned : User? = userRepository?.findByName(name = user.login)
////
////        return userRetorned
////    }
}