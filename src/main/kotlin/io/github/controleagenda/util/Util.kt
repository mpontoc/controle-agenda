package io.github.controleagenda.util

import io.github.controleagenda.model.Segment
import io.github.controleagenda.repository.SegmentRepository

@Suppress("UNCHECKED_CAST")
open class Util {

    fun initSegments(repository: SegmentRepository): List<Segment> {
        repository.save(
            Segment(
                1, "Academia"
            )
        )
        repository.save(
            Segment(
                2, "Alimentação"
            )
        )
        repository.save(
            Segment(3, "Educação"
            )
        )
        repository.save(
            Segment(4, "Esporte"
            )
        )
        repository.save(
            Segment(5, "Familiar"
            )
        )
        repository.save(
            Segment(6, "Saúde"
            )
        )
        return repository.findAll() as List<Segment>
    }
}