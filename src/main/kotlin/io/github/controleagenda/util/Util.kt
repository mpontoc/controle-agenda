package io.github.controleagenda.util

import io.github.controleagenda.model.Segment
import io.github.controleagenda.model.SubSegment
import io.github.controleagenda.repository.SegmentRepository
import io.github.controleagenda.repository.SubSegmentRepository

open class Util {

    fun initSegments(repository: SegmentRepository, subSegmentRepository: SubSegmentRepository) {

        val subSegmentDefault = SubSegment(1, "crie a sua tarefa", "aqui descreva sua tarefa")

        repository.save(
            Segment(
                1, "Academia"
            )
        )

        subSegmentRepository.save(
            SubSegment(
                subSegmentRepository.findAll().count().toLong() + 1,
                subSegmentDefault.subSegment,
                subSegmentDefault.message,
                Segment(1, "Academia")
            )
        )

        repository.save(
            Segment(
                2, "Alimentação"
            )
        )

        subSegmentRepository.save(
            SubSegment(
                subSegmentRepository.findAll().count().toLong() + 1,
                subSegmentDefault.subSegment,
                subSegmentDefault.message,
                Segment(2, "Alimentação")
            )
        )

        repository.save(
            Segment(
                3, "Educação"
            )
        )

        subSegmentRepository.save(
            SubSegment(
                subSegmentRepository.findAll().count().toLong() + 1,
                subSegmentDefault.subSegment,
                subSegmentDefault.message,
                Segment(3, "Educação")
            )
        )

        repository.save(
            Segment(
                4, "Esporte"
            )
        )

        subSegmentRepository.save(
            SubSegment(
                subSegmentRepository.findAll().count().toLong() + 1,
                subSegmentDefault.subSegment,
                subSegmentDefault.message,
                Segment(4, "Esporte")
            )
        )
        repository.save(
            Segment(
                5, "Familiar"
            )
        )

        subSegmentRepository.save(
            SubSegment(
                subSegmentRepository.findAll().count().toLong() + 1,
                subSegmentDefault.subSegment,
                subSegmentDefault.message,
                Segment(5, "Familiar")
            )
        )
        repository.save(
            Segment(
                6, "Saúde"
            )
        )

        subSegmentRepository.save(
            SubSegment(
                subSegmentRepository.findAll().count().toLong() + 1,
                subSegmentDefault.subSegment,
                subSegmentDefault.message,
                Segment(6, "Saúde")
            )
        )
    }
}