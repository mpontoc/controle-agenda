package io.github.controleagenda.controller

import io.github.controleagenda.model.User
import io.github.controleagenda.model.dto.UserDTO
import io.github.controleagenda.repository.UserRepository
import io.github.controleagenda.services.UserService
import io.github.controleagenda.util.Util
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.util.UriComponentsBuilder
import javax.servlet.http.HttpServletRequest
import javax.validation.Valid

@RestController
@RequestMapping("/user")
class UserController {

    @Autowired
    lateinit var userService: UserService

    @Autowired
    lateinit var userRepository: UserRepository

    val util = Util()

    @GetMapping
    fun getAllUsers() =
        userService.getAllUsers()

    @GetMapping("/{id}")
    fun getUserById(@PathVariable id: Long): UserDTO {
        return userService.getUserById(id)
    }

    @PostMapping
    fun addUser(
        @RequestBody @Valid user: User,
        uriComponentsBuilder: UriComponentsBuilder,
        request: HttpServletRequest
    ): ResponseEntity<*> {

        val idSequence = util.idSequenceUser(userRepository)
        user.id = idSequence
        val response = userService.addUser(user)
        val uri = uriComponentsBuilder.path("segmentos/${idSequence}/").build().toUri()
        return ResponseEntity.created(uri).body(response)

    }
}