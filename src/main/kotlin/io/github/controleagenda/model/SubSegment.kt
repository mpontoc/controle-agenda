package io.github.controleagenda.model

import javax.persistence.*

@Entity
data class SubSegment @JvmOverloads constructor(

    @Id
    var id: Long? = null,
    val subSegmentName: String? = "",
    val message: String? = "",

    @OneToOne
    val segment: Segment = Segment(1, "")

)
