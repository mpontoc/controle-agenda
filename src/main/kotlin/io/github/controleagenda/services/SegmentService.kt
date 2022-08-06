package io.github.controleagenda.services

import io.github.controleagenda.model.Segment
import io.github.controleagenda.model.SegmentToReturn
import java.util.*

interface SegmentService {

    fun getAllSegments(): List<SegmentToReturn?>
    fun getSegmentById(id: Long): Optional<Segment?>
    fun addSegment(id: Long, segment: Segment): SegmentToReturn
    fun updateSegment(id: Long, segment: Segment): SegmentToReturn
    fun deleteSegment(id: Long)
}