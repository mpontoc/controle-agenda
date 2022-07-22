package io.github.controleagenda.commons

import io.github.controleagenda.model.Segment

open class Utils {

    fun listSegmentsDefault(): List<Segment> {
        return listOf(
            Segment(1, "Academia"),
            Segment(2, "Alimentação"),
            Segment(3, "Educação"),
            Segment(4, "Esporte"),
            Segment(5, "Familiar"),
            Segment(6, "Saúde")
        )
    }

    fun listSegmentOnlyOne(): List<Segment> {
        return listOf(
            Segment(1, "Academia")
        )
    }
}


