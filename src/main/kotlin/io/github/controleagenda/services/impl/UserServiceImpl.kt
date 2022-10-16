package io.github.controleagenda.services.impl

import io.github.controleagenda.model.Users
import io.github.controleagenda.model.dto.UsersDTO
import io.github.controleagenda.repository.SegmentRepository
import io.github.controleagenda.repository.SubSegmentRepository
import io.github.controleagenda.repository.UsersRepository
import io.github.controleagenda.services.UsersService
import io.github.controleagenda.util.Util
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class userserviceImpl : UsersService {

    @Autowired
    lateinit var usersRepository: UsersRepository

    @Autowired
    lateinit var segmentRepository: SegmentRepository

    @Autowired
    lateinit var subSegmentRepository: SubSegmentRepository

    val util = Util()

    override fun addusers(users: Users): UsersDTO {

        usersRepository.save(users)

        util.initSegments(users.id!!, usersRepository, segmentRepository, subSegmentRepository)

        return UsersDTO(
            users.id!!,
            users.usersName!!,
        )
    }

    override fun deleteusers(id: Long) {
        val usersDelete = usersRepository.deleteById(id)
        println("O usuario $usersDelete foi deletado com sucesso")
    }

    override fun getusersById(id: Long): UsersDTO {

        return usersRepository.findUsersById(id)

    }

    override fun getAllusers(): MutableList<Users?> {

        return usersRepository.findAll()
    }

    override fun updateusers(id: Long, users: Users): Users {

        return users
    }


}
