package io.github.controleagenda.util

import io.github.controleagenda.model.Segment
import io.github.controleagenda.model.SubSegment
import io.github.controleagenda.repository.SegmentRepository
import io.github.controleagenda.repository.SubSegmentRepository

open class Util() {

    val subSegmentDefault = SubSegment(1, "crie a sua tarefa", "aqui descreva sua tarefa")

    fun initSegments(segmentRepository: SegmentRepository, subSegmentRepository: SubSegmentRepository) {

        segmentRepository.save(
            Segment(
                1, "Academia"
            )
        )

        subSegmentRepository.save(
            SubSegment(
                idSequentSubSegment(subSegmentRepository),
                subSegmentDefault.subSegment,
                subSegmentDefault.message,
                Segment(1, "Academia")
            )
        )

        segmentRepository.save(
            Segment(
                2, "Alimentação"
            )
        )

        subSegmentRepository.save(
            SubSegment(
                idSequentSubSegment(subSegmentRepository),
                subSegmentDefault.subSegment,
                subSegmentDefault.message,
                Segment(2, "Alimentação")
            )
        )

        segmentRepository.save(
            Segment(
                3, "Educação"
            )
        )

        subSegmentRepository.save(
            SubSegment(
                idSequentSubSegment(subSegmentRepository),
                subSegmentDefault.subSegment,
                subSegmentDefault.message,
                Segment(3, "Educação")
            )
        )

        segmentRepository.save(
            Segment(
                4, "Esporte"
            )
        )

        subSegmentRepository.save(
            SubSegment(
                idSequentSubSegment(subSegmentRepository),
                subSegmentDefault.subSegment,
                subSegmentDefault.message,
                Segment(4, "Esporte")
            )
        )
        segmentRepository.save(
            Segment(
                5, "Familiar"
            )
        )

        subSegmentRepository.save(
            SubSegment(
                idSequentSubSegment(subSegmentRepository),
                subSegmentDefault.subSegment,
                subSegmentDefault.message,
                Segment(5, "Familiar")
            )
        )
        segmentRepository.save(
            Segment(
                6, "Saúde"
            )
        )

        subSegmentRepository.save(
            SubSegment(
                idSequentSubSegment(subSegmentRepository),
                subSegmentDefault.subSegment,
                subSegmentDefault.message,
                Segment(6, "Saúde")
            )
        )
    }

    fun idSequentSubSegment(subSegmentRepository: SubSegmentRepository): Long {
        return (subSegmentRepository.findAll().count() + 1).toLong()
    }
}