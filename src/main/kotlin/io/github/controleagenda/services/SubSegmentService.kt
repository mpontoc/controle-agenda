package io.github.controleagenda.services

import io.github.controleagenda.model.SegmentToReturn
import io.github.controleagenda.model.SubSegment

interface SubSegmentService {

    fun addSubSegment(idSegment: Long, subSegment: SubSegment): SegmentToReturn
    fun updateSubSegment(idSegment: Long, subSegment: SubSegment): SegmentToReturn
    fun deleteSubSegment(idSegment: Long, idSubSegment: Long)
}