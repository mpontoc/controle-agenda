package io.github.controleagenda.model

import javax.persistence.*

@Entity
data class SubSegment @JvmOverloads constructor(

    @Id
    val id: Long? = null,
    val subSegment: String? = "",
    val message: String? = "",

    @OneToOne
    val segment: Segment = Segment(1, "")

)
