package io.github.controleagenda.model

import com.fasterxml.jackson.annotation.JsonAutoDetect
import com.fasterxml.jackson.annotation.JsonProperty
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.OneToOne

@Entity
@JsonAutoDetect
data class SubSegment @JvmOverloads constructor(

    @Id
    var id: Long? = null,
    @JsonProperty("sub_segment_name")
    val subSegmentName: String? = "",
    val message: String? = "",

    @OneToOne
    val segment: Segment = Segment(1, "")

)
