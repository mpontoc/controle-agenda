package io.github.controleagenda.model

import javax.persistence.Entity
import javax.persistence.Id
import javax.transaction.Transactional

@Entity
@Transactional
data class SubSegment (

    @Id
    val id: Long?,
    val subSegment: String?,
    val message: String?

) {
    constructor() : this(
        -1, "", ""
    )

}