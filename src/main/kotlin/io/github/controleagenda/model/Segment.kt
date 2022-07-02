package io.github.controleagenda.model

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
data class Segment(

    @Id
    val id: Long?,
    val segment: String

) {
    constructor() : this(
        -1, "",
    )

}
