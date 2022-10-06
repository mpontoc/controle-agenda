package io.github.controleagenda.controller

import io.github.controleagenda.model.User
import io.github.controleagenda.model.dto.UserDTO
import io.github.controleagenda.services.UserService
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
        val response = userService.addUser(user)
        val idSequence = response.id
        val uri = uriComponentsBuilder.path("segmentos/${idSequence}/").build().toUri()
        return ResponseEntity.created(uri).body(response)

    }
}