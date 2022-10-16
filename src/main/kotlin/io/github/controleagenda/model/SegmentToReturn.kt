package io.github.controleagenda.model

import com.fasterxml.jackson.annotation.JsonProperty
import io.github.controleagenda.model.dto.SegmentDTO
import io.github.controleagenda.model.dto.UsersDTO

data class SegmentToReturn(

    @JsonProperty("users")
    val users: UsersDTO,
    @JsonProperty("segment")
    val segment: MutableList<SegmentDTO>

)

data class SegmentOnlyOneToReturn(

    @JsonProperty("users")
    val users: UsersDTO,
    @JsonProperty("segment")
    val segment: SegmentDTO

)