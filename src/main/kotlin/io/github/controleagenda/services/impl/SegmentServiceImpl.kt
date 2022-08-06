package io.github.controleagenda.services.impl

import io.github.controleagenda.model.Segment
import io.github.controleagenda.model.SegmentToReturn
import io.github.controleagenda.model.SubSegment
import io.github.controleagenda.repository.SegmentRepository
import io.github.controleagenda.repository.SubSegmentRepository
import io.github.controleagenda.services.SegmentService
import io.github.controleagenda.util.Util
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class SegmentServiceImpl : SegmentService {

    @Autowired
    lateinit var repository: SegmentRepository

    @Autowired
    lateinit var subSegmentRepository: SubSegmentRepository

    val subSegmentDefault = SubSegment(1, "crie a sua tarefa", "aqui descreva sua tarefa")

    override fun getSegmentById(id: Long): Optional<Segment?> {
        return repository.findById(id)
    }

    override fun getAllSegments(): List<SegmentToReturn> {
        var allSegments = repository.findAll().count()
        val util = Util()
        var subSegmentToSegment: MutableList<SubSegment>

        if (allSegments <= 1) {
            util.initSegments(repository)
        }

        val allSegmentList: MutableList<Segment> = repository.findAll()
        var segmentos = mutableListOf<SegmentToReturn>()
        val subSegments = subSegmentRepository.findAll()

        for (segment in allSegmentList) {
            subSegmentToSegment =
                subSegments.filter {
                    it!!.segment.id == (segment.id)
                } as MutableList<SubSegment>

            segmentos.add(
                SegmentToReturn(
                    Segment(segment.id, segment.segment.toString()),
                    subSegmentToSegment.toMutableList()
                )
            )
            subSegmentToSegment.clear()
        }

        return segmentos
    }

    override fun addSegment(id: Long, segment: Segment): SegmentToReturn {

        val allSegments = repository.findAll()
        var idSequenceOK = false
        var idSequence: Long = 5

        if (allSegments.count() < id && !repository.findById(id).isPresent) {
            return SegmentToReturn(
                this.repository.save(Segment(id, segment.segment)),
                mutableListOf(
                    SubSegment(
                        subSegmentDefault.id,
                        subSegmentDefault.subSegment,
                        subSegmentDefault.message
                    )
                )
            )
        } else {
            while (!idSequenceOK) {
                if (repository.findById(idSequence).isPresent) {
                    idSequence++
                } else {
                    idSequenceOK = true
                }
            }
        }
        return SegmentToReturn(
            repository.save(
                Segment(idSequence, segment.segment)
            ),
            mutableListOf(SubSegment(subSegmentDefault.id, subSegmentDefault.subSegment, subSegmentDefault.message))
        )
    }

    override fun updateSegment(id: Long, segment: Segment): SegmentToReturn {

        var submentToEdited = repository.findById(id)

        if (segment.id!! >= 1) {
            return addSegment(segment.id, segment)
        }
        return addSegment(id, segment)
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

}

