package io.github.controleagenda.services

import io.github.controleagenda.model.Segment
import io.github.controleagenda.model.SegmentOnlyOneToReturn
import io.github.controleagenda.model.SegmentToReturn

interface SegmentService {

    fun getAllSegments(usersId: Long): SegmentToReturn
    fun getSegmentById(usersId: Long, segmentId: Long): SegmentOnlyOneToReturn
    fun createSegment(usersId: Long, segment: Segment): SegmentToReturn
    fun updateSegment(usersId: Long, segment: Segment): SegmentToReturn
    fun deleteSegment(usersId: Long, segmentId: Long): String
}