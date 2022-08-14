package io.github.controleagenda.services

import io.github.controleagenda.model.Segment
import io.github.controleagenda.model.SegmentToReturn

interface SegmentService {

    fun getAllSegments(): MutableList<SegmentToReturn>
    fun getSegmentById(id: Long): SegmentToReturn
    fun createSegment(segment: Segment): SegmentToReturn
    fun updateSegment(segment: Segment): SegmentToReturn
    fun deleteSegment(id: Long)
}