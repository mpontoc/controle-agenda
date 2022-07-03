package io.github.controleagenda.services

import io.github.controleagenda.model.Segment
import io.github.controleagenda.repository.SegmentsRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class SegmentServiceImpl : SegmentService {

    @Autowired
    lateinit var repository: SegmentsRepository

    override fun addSegment(id: Long, segment: Segment): Segment {

        println("Esse é o id $id")

        return repository.save(Segment(id = id, segment.segment))
    }

    override fun deleteSegment(id: Long) {
        val segment = repository.findById(id)
        repository.deleteById(id)
        println("O usuario ${segment.get().segment} foi deletado com sucesso")
    }

    override fun getSegmentById(id: Long): Optional<Segment?> {

        return repository.findById(id)

    }

    override fun getAllSegments(): List<Segment?> {

        if (repository.findAll().count() <= 1) {
            return initSegments()
        } else {
            return repository.findAll()
        }
    }

    override fun updateSegments(id: Long, segment: Segment): Segment {

        deleteSegment(id)

        if (segment.id!! >= 1) {
            return addSegment(segment.id, segment)
        } else {
            return addSegment(id, segment)
        }
    }

    fun initSegments(): List<Segment> {

        repository.save(
            Segment(1, "Academia")
        )
        repository.save(
            Segment(2, "Alimentação")
        )
        repository.save(
            Segment(3, "Educação")
        )
        repository.save(
            Segment(4, "Esporte")
        )
        repository.save(
            Segment(5, "Familiar")
        )
        repository.save(
            Segment(6, "Saúde")
        )
        repository.save(Segment(7, "Outros"))

        return repository.findAll() as List<Segment>

    }

}
