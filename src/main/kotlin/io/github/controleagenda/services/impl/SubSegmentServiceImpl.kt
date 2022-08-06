package io.github.controleagenda.services.impl

import io.github.controleagenda.model.Segment
import io.github.controleagenda.model.SegmentToReturn
import io.github.controleagenda.model.SubSegment
import io.github.controleagenda.repository.SegmentRepository
import io.github.controleagenda.repository.SubSegmentRepository
import io.github.controleagenda.services.SubSegmentService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class SubSegmentServiceImpl : SubSegmentService {

    @Autowired
    lateinit var segmentRepository: SegmentRepository

    @Autowired
    lateinit var subSegmentRepository: SubSegmentRepository

    override fun addSubSegment(id: Long, subSegment: SubSegment): SegmentToReturn {

        val segment: Segment = segmentRepository.getById(id)

        subSegmentRepository.save(
            SubSegment(
                subSegment.id, subSegment.subSegment, subSegment.message,
                Segment(segment.id, segment.segment)
            )
        )

        val subSegments = subSegmentRepository.findAll()

        subSegments.add(subSegment)

        var subSegmentToSegment =
            subSegments.filter {
                it!!.segment.id == (segment.id)
            } as MutableList<SubSegment>

        return SegmentToReturn(
            Segment(
                segment.id, segment.segment
            ),
            subSegmentToSegment.toMutableList()
        )

    }

    override fun updateSubSegment(id: Long, subSegment: SubSegment): SegmentToReturn {
        TODO("Not yet implemented")
    }

    override fun deleteSubSegment(id: Long, idSubSegment: Long) {
        TODO("Not yet implemented")
    }
}