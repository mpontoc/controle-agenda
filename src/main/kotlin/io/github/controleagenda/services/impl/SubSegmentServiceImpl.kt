package io.github.controleagenda.services.impl

import io.github.controleagenda.exception.BackendException
import io.github.controleagenda.exception.NotFoundException
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

        if (segmentRepository.findById(idSegment).isPresent) {
            val segment: Segment = segmentRepository.getById(idSegment)
            if (subSegmentRepository.findSubSegmentFromSegmentID(segment.id).count() < 20) {
                subSegmentRepository.save(
                    SubSegment(
                        util.idSequenceSubSegment(subSegmentRepository), subSegment.subSegmentName, subSegment.message,
                        Segment(segment.id, segment.segmentName)
                    )
                )
                val subSegments = subSegmentRepository.findAll()

                val subSegmentToSegment =
                    subSegments.filter {
                        it.segment.id == (segment.id)
                    } as MutableList<SubSegment>
                return SegmentToReturn(
                    Segment(segment.id, segment.segmentName),
                    subSegmentToSegment.toMutableList()
                )
            } else throw BackendException("Excedeu a quantidade de 20 Tarefas por Segmento")
        } else throw NotFoundException("Segmento com o id ${idSegment} não existe no banco de dados")
    }

    override fun updateSubSegment(subSegment: SubSegment): SubSegment {
        if (subSegmentRepository.findById(subSegment.id).isPresent) {
            val subSegmentBase = subSegmentRepository.findSubSegmentById(subSegment.id)
            return subSegmentRepository.save(
                SubSegment(
                    subSegment.id, subSegment.subSegmentName, subSegment.message,
                    Segment(subSegmentBase.segment.id, subSegmentBase.subSegmentName)
                )
            )
        } else throw NotFoundException("SubSegmento com o id ${subSegment.id} não existe no banco de dados")
    }

    override fun deleteSubSegment(idSubSegment: Long) {
        if (subSegmentRepository.findById(idSubSegment).isPresent) {
            subSegmentRepository.deleteById(idSubSegment)
        } else throw NotFoundException("SubSegmento com o id ${idSubSegment} não existe no banco de dados")

    }
}