package io.github.controleagenda.model

import javax.persistence.Entity
import javax.persistence.Id
import javax.transaction.Transactional

@Entity
@Transactional
data class Segment(

    @Id
    val id: Long?,
    val segment: String,

    @Transient
    val subSegment: SubSegment? = SubSegment(1,
                                     "crie sua tarefa",
                                        "Descreva o seu compromisso")

) {
    constructor() : this(
        -1, "",
        SubSegment(1,
            "crie sua tarefa",
            "Descreva o seu compromisso")
    )

}
