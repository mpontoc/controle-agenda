package io.github.controleagenda.services

import io.github.controleagenda.model.Segments
import io.github.controleagenda.model.User
import java.util.*

interface SegmentsService {

    fun addSegment(segments: Segments): Segments
    fun deleteSegment(id: Long)
    fun getSegmentById(id: Long): Optional<Segments?>
    fun getSegments() : MutableList<Segments?>
    fun updateSegments(id: Long, user: User): User
}