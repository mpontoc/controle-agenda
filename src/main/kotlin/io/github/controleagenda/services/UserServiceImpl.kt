package io.github.controleagenda.services

import io.github.controleagenda.model.User
import io.github.controleagenda.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class UserServiceImpl: UserService {

    @Autowired
    lateinit var repository: UserRepository

    override fun addUser(user: User): User {
        return repository.save(user)
    }

    override fun deleteUser(id: Long) {
        val userDelete = repository.deleteById(id)
        println("O usuario $userDelete foi deletado com sucesso")
    }

    override fun getUserById(id: Long): java.util.Optional<User?> {

       return repository.findById(id)

    }

    override fun getAllUsers(): MutableList<User?> {

        return repository.findAll()
    }

    override fun updateUser(id: Long, user: User): User {

        return user
    }


}
