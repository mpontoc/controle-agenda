package io.github.controleagenda.model

import com.fasterxml.jackson.annotation.JsonProperty
import io.github.controleagenda.model.dto.SegmentDTO
import io.github.controleagenda.model.dto.UserDTO

data class SegmentToReturn(

    @JsonProperty("user")
    val user: UserDTO,
    @JsonProperty("segment")
    val segment: MutableList<SegmentDTO>

)