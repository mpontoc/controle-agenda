package io.github.controleagenda.model.dto

data class SubSegmentDTO(

    val subSegmentId: Long,
    val subSegmentName: String,
    val message: String,
    val segment: SegmentResponse

)
