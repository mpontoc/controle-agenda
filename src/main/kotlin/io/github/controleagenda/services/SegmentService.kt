package io.github.controleagenda.services

import io.github.controleagenda.model.Segment
import io.github.controleagenda.model.SegmentToReturn

interface SegmentService {

    fun getAllSegments(idUser: Long): SegmentToReturn
    fun getSegmentById(idUser: Long, idSugment: Long): SegmentToReturn
    fun createSegment(idUser: Long, segment: Segment): SegmentToReturn
    fun updateSegment(idUser:Long, segment: Segment): SegmentToReturn
    fun deleteSegment(id: Long): String
}