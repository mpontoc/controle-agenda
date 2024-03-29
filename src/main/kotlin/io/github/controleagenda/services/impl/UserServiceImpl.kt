package io.github.controleagenda.services.impl

import io.github.controleagenda.model.User
import io.github.controleagenda.model.dto.UserDTO
import io.github.controleagenda.repository.SegmentRepository
import io.github.controleagenda.repository.SubSegmentRepository
import io.github.controleagenda.repository.UserRepository
import io.github.controleagenda.services.UserService
import io.github.controleagenda.util.Util
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UserServiceImpl : UserService {

    @Autowired
    lateinit var userRepository: UserRepository

    @Autowired
    lateinit var segmentRepository: SegmentRepository

    @Autowired
    lateinit var subSegmentRepository: SubSegmentRepository

    val util = Util()

    override fun addUser(user: User): UserDTO {

        userRepository.save(user)

        util.initSegments(user.id!!, userRepository, segmentRepository, subSegmentRepository)

        return UserDTO(
            user.id!!,
            user.userName!!,
        )
    }

    override fun deleteUser(id: Long) {
        val userDelete = userRepository.deleteById(id)
        println("O usuario $userDelete foi deletado com sucesso")
    }

    override fun getUserById(id: Long): UserDTO {

        return userRepository.findUserById(id)

    }

    override fun getAllUsers(): MutableList<User?> {

        return userRepository.findAll()
    }

    override fun updateUser(id: Long, user: User): User {

        return user
    }


}
