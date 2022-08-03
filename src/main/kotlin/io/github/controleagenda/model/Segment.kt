package io.github.controleagenda.model

import javax.persistence.*
import javax.transaction.Transactional

@Entity
@Transactional
data class Segment(

    @Id
    val id: Long?,
    val segment: String,

    @Embedded
    @ManyToMany
    val subSegment: List<SubSegment?> = listOf(
        SubSegment(
            1,
            "crie sua tarefa",
            "Descreva o seu compromisso"
        )
    )

) {
    constructor() : this(
        -1, "",
        listOf(
            SubSegment(
                1,
                "crie sua tarefa",
                "Descreva o seu compromisso"
            )
        )
    )
}
