package io.github.controleagenda.services

import io.github.controleagenda.model.SegmentToReturn
import io.github.controleagenda.model.SubSegment
import io.github.controleagenda.model.dto.SubSegmentDTO

interface SubSegmentService {

    fun createSubSegment(userId: Long, segmentId: Long, subSegment: SubSegment): SegmentToReturn
    fun updateSubSegment(userId: Long, subSegment: SubSegment): SubSegmentDTO
    fun deleteSubSegment(userId: Long, subSegmentId: Long, subSegmentId1: Long)
}