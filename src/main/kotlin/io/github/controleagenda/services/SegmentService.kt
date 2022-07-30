package io.github.controleagenda.services

import io.github.controleagenda.model.Segment
import java.util.*

interface SegmentService {

    fun addSegment(id: Long, segment: Segment): Segment
    fun deleteSegment(id: Long)
    fun getSegmentById(id: Long): Optional<Segment?>
    fun getAllSegments() : List<Segment?>
    fun updateSegments(id: Long, segment: Segment): Segment
}