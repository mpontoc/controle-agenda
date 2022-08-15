package io.github.controleagenda.services

import io.github.controleagenda.model.SegmentToReturn
import io.github.controleagenda.model.SubSegment

interface SubSegmentService {

    fun addSubSegment(idSegment: Long, subSegment: SubSegment): SegmentToReturn
    fun updateSubSegment(subSegment: SubSegment): SubSegment
    fun deleteSubSegment(idSubSegment: Long)
}