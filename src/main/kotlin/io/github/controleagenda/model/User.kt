package io.github.controleagenda.model

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
data class User(

    @Id
    val id: Long,
    val usuario: String,
    val senha: String,

    ) {
    constructor() : this(
        -1, "",
        ""
    )
}
