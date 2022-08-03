package io.github.controleagenda.services.impl

import io.github.controleagenda.model.Segment
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

    override fun addSubSegment(id: Long, subSegment: SubSegment): Segment {

        var segment: Segment = segmentRepository.getById(id)

        var subSegments: List<SubSegment?> = segment.let { it.subSegment }

        val subSegment: SubSegment =
            subSegmentRepository.save(SubSegment(subSegment.id, subSegment.subSegment, subSegment.message))

        subSegments = subSegments.plus(subSegment)


        segment = segmentRepository.save(
            Segment(
                segment.id, segment.segment,
//                listOf(
                    subSegments,
//                    SubSegment(subSegment.id, subSegment.subSegment, subSegment.message))
//                )
            )
        )

        return segment


    }

    override fun updateSubSegment(id: Long, subSegment: SubSegment): Segment {
        TODO("Not yet implemented")
    }

    override fun deleteSubSegment(id: Long, idSubSegment: Long) {
        TODO("Not yet implemented")
    }
}