package io.github.controleagenda.services

import io.github.controleagenda.model.Segment
import io.github.controleagenda.repository.SegmentRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import io.github.controleagenda.util.Util
import java.util.*

@Service
class SegmentServiceImpl : SegmentService {

    @Autowired
    lateinit var repository: SegmentRepository

    override fun addSegment(id: Long, segment: Segment): Segment {

        val allSegments = repository.findAll()
        var idSequenceOK = false
        var idSequence: Long = 5

        if (allSegments.count() < id && !repository.findById(id).isPresent) {
            return repository.save(Segment(id, segment.segment))
        } else {
            while (!idSequenceOK) {
                if (repository.findById(idSequence).isPresent) {
                    idSequence++
                } else {
                    idSequenceOK = true
                }
            }
        }
        return repository.save(Segment(idSequence, segment.segment))
    }

    override fun deleteSegment(id: Long) {

        if (id in 1..6) {
            throw RuntimeException("Não é possível apagar os valores default")
        } else {
            val segment = repository.findById(id)
            repository.deleteById(id)
            println("O usuario ${segment.get().segment} foi deletado com sucesso")
        }
    }

    override fun getSegmentById(id: Long): Optional<Segment?> {
        return repository.findById(id)
    }

    override fun getAllSegments(): List<Segment?> {

        val allSegments = repository.findAll().count()
        val util = Util()

        if (allSegments <= 1) {
            return util.initSegments(repository)
        }
        return repository.findAll()
    }

    override fun updateSegments(id: Long, segment: Segment): Segment {

        deleteSegment(id)

        if (segment.id!! >= 1) {
            return addSegment(segment.id, segment)
        }
        return addSegment(id, segment)
    }

}
