package io.github.controleagenda.services

import io.github.controleagenda.model.Segment
import io.github.controleagenda.model.SubSegment
import java.util.*

interface SubSegmentService {

    fun addSubSegment(idSegment: Long, subSegment: SubSegment): Segment
    fun updateSubSegment(idSegment: Long, subSegment: SubSegment): Segment
    fun deleteSubSegment(idSegment: Long, idSubSegment: Long)
}