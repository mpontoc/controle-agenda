package io.github.controleagenda.services

import io.github.controleagenda.model.Segment
import io.github.controleagenda.model.SegmentOnlyOneToReturn
import io.github.controleagenda.model.SegmentToReturn
import io.github.controleagenda.model.dto.SegmentDTO

interface SegmentService {

    fun getAllSegments(userId: Long): SegmentToReturn
    fun getSegmentById(userId: Long, segmentId: Long): SegmentOnlyOneToReturn
    fun createSegment(userId: Long, segment: Segment): SegmentToReturn
    fun updateSegment(userId:Long, segment: Segment): SegmentToReturn
    fun deleteSegment(id: Long): String
}