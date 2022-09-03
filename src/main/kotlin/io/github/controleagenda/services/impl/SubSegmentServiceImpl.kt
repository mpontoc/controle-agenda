package io.github.controleagenda.services.impl

import io.github.controleagenda.exception.BackendException
import io.github.controleagenda.model.Segment
import io.github.controleagenda.model.SegmentToReturn
import io.github.controleagenda.model.SubSegment
import io.github.controleagenda.repository.SegmentRepository
import io.github.controleagenda.repository.SubSegmentRepository
import io.github.controleagenda.services.SubSegmentService
import io.github.controleagenda.util.Util
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Suppress("UNCHECKED_CAST")
@Service
class SubSegmentServiceImpl : SubSegmentService {

    @Autowired
    lateinit var segmentRepository: SegmentRepository

    @Autowired
    lateinit var subSegmentRepository: SubSegmentRepository

    val util = Util()

    override fun createSubSegment(idSegment: Long, subSegment: SubSegment): SegmentToReturn {
        val segment: Segment = segmentRepository.getById(idSegment)
        if (subSegmentRepository.findSubSegmentToSegmentID(segment.id!!).count() < 20) {
            subSegmentRepository.save(
                SubSegment(
                    util.idSequenceSubSegment(subSegmentRepository), subSegment.subSegmentName, subSegment.message,
                    Segment(segment.id, segment.segmentName)
                )
            )
            val subSegments = subSegmentRepository.findAll()
            subSegments.add(subSegment)
            val subSegmentToSegment =
                subSegments.filter {
                    it!!.segment.id == (segment.id)
                } as MutableList<SubSegment>
            return SegmentToReturn(
                Segment(segment.id, segment.segmentName),
                subSegmentToSegment.toMutableList()
            )
        } else throw BackendException("Excedeu a quantidade de 20 Tarefas por Segmento")
    }

    override fun updateSubSegment(subSegment: SubSegment): SubSegment {
        val subSegmentBase = subSegmentRepository.findSubSegmentById(subSegment.id!!)
        return subSegmentRepository.save(
            SubSegment(
                subSegment.id, subSegment.subSegmentName, subSegment.message,
                Segment(subSegmentBase!!.segment.id, subSegmentBase.subSegmentName)
            )
        )
    }

    override fun deleteSubSegment(idSubSegment: Long) {
        subSegmentRepository.deleteById(idSubSegment)
    }
}