package io.github.controleagenda.services

import io.github.controleagenda.model.Segment
import io.github.controleagenda.model.SubSegment
import java.util.*

interface SegmentService {

    fun getAllSegments() : List<Segment?>
    fun getSegmentById(id: Long): Optional<Segment?>
    fun addSegment(id: Long, segment: Segment): Segment
    fun updateSegment(id: Long, segment: Segment): Segment
    fun deleteSegment(id: Long)
}