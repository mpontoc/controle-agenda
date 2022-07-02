package io.github.controleagenda.services

import io.github.controleagenda.model.Segments
import io.github.controleagenda.model.User
import io.github.controleagenda.repository.SegmentsRepository
import io.github.controleagenda.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class SegmentsServiceImpl: SegmentsService {

    @Autowired
    lateinit var repository: SegmentsRepository

    override fun addSegment(segments: Segments): Segments {
        return repository.save(segments)
    }

    override fun deleteSegment(id: Long) {
        val segmentoDelete = repository.deleteById(id)
        println("O usuario $segmentoDelete foi deletado com sucesso")
    }

    override fun getSegmentById(id: Long): Optional<Segments?> {

       return repository.findById(id)

    }

    override fun getSegments(): MutableList<Segments?> {

        return repository.findAll()
    }

    override fun updateSegments(id: Long, user: User): User {

        return user
    }


}
