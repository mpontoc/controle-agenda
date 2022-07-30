package io.github.controleagenda.services

import io.github.controleagenda.model.SubSegment
import io.github.controleagenda.repository.SubSegmentRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class SubSegmentServiceImpl : SubSegmentService {

    @Autowired
    lateinit var repository: SubSegmentRepository

    override fun addSubSegment(id: Long, subSegment: SubSegment): SubSegment {

        val allSubSegments = repository.findAll()
        var idSequenceOK = false
        var idSequence: Long = 5

        if (allSubSegments.count() < id && !repository.findById(id).isPresent) {
            return repository.save(SubSegment(id, subSegment.subSegment, ""))
        } else {
            while (!idSequenceOK) {
                if (repository.findById(idSequence).isPresent) {
                    idSequence++
                } else {
                    idSequenceOK = true
                }
            }
        }
        return repository.save(SubSegment(idSequence, subSegment.subSegment, ""))
    }

    override fun deleteSubSegment(id: Long) {

        if (id in 1..6) {
            throw RuntimeException("Não é possível apagar os valores default")
        } else {
            val subSegment = repository.findById(id)
            repository.deleteById(id)
            println("O usuario ${subSegment.get().subSegment} foi deletado com sucesso")
        }
    }

    override fun getSubSegmentById(id: Long): Optional<SubSegment?> {
        return repository.findById(id)
    }

    override fun getAllSubSegments(): List<SubSegment?> {
        return repository.findAll()
    }

    override fun updateSubSegments(id: Long, SubSegment: SubSegment): SubSegment {

        deleteSubSegment(id)

        if (SubSegment.id!! >= 1) {
            return addSubSegment(SubSegment.id, SubSegment)
        }
        return addSubSegment(id, SubSegment)
    }
}
