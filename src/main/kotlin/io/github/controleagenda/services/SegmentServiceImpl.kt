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

    override fun getAllSegments(): MutableIterable<Segment?> {

        return repository.findAll()
    }

    override fun updateSegments(id: Long, segment: Segment): Segment {

        deleteSegment(id)

        if (segment.id != null) {
            return addSegment(segment.id, segment)
        } else {
            return addSegment(id, segment)
        }
    }


}
