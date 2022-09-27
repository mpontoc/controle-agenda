package io.github.controleagenda.services

import io.github.controleagenda.model.Segment
import io.github.controleagenda.model.SegmentToReturn

interface SegmentService {

    fun getSegmentById(userId: Long): SegmentToReturn
    fun createSegment(userId: Long, segment: Segment): SegmentToReturn
    fun updateSegment(userId:Long, segment: Segment): SegmentToReturn
    fun deleteSegment(id: Long): String
}