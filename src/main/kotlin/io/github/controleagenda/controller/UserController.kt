package io.github.controleagenda.controller

import io.github.controleagenda.model.Users
import io.github.controleagenda.model.dto.UsersDTO
import io.github.controleagenda.repository.UsersRepository
import io.github.controleagenda.services.UsersService
import io.github.controleagenda.util.Util
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.util.UriComponentsBuilder
import javax.servlet.http.HttpServletRequest
import javax.validation.Valid

@CrossOrigin
@RestController
@RequestMapping("/users")
class usersController {

    @Autowired
    lateinit var userservice: UsersService

    @Autowired
    lateinit var usersRepository: UsersRepository

    val util = Util()

    @GetMapping
    fun getAllusers() =
        userservice.getAllusers()

    @GetMapping("/{id}")
    fun getusersById(@PathVariable id: Long): UsersDTO {
        return userservice.getusersById(id)
    }

    @CrossOrigin
    @PostMapping
    fun addusers(
        @RequestBody @Valid users: Users,
        uriComponentsBuilder: UriComponentsBuilder,
        request: HttpServletRequest
    ): ResponseEntity<*> {

        val idSequence = util.idSequenceusers(usersRepository)
        users.id = idSequence
        val response = userservice.addusers(users)
        val uri = uriComponentsBuilder.path("segmentos/${idSequence}/").build().toUri()
        return ResponseEntity.created(uri).body(response)

    }
}