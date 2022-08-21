package io.github.controleagenda.model

import com.fasterxml.jackson.annotation.JsonAutoDetect
import com.fasterxml.jackson.annotation.JsonProperty
import javax.persistence.Entity
import javax.persistence.Id

@Entity
@JsonAutoDetect
data class Segment(

    @Id
    val id: Long? = null,
    @JsonProperty("segment_name")
    val segmentName: String? = ""

)

