package io.github.controleagenda.services

import io.github.controleagenda.model.SubSegment
import java.util.*

interface SubSegmentService {

    fun addSubSegment(id: Long, subSegment: SubSegment): SubSegment
    fun deleteSubSegment(id: Long)
    fun getSubSegmentById(id: Long): Optional<SubSegment?>
    fun getAllSubSegments() : List<SubSegment?>
    fun updateSubSegments(id: Long, subSegment: SubSegment): SubSegment
}