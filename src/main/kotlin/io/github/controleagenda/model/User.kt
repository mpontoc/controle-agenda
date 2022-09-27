package io.github.controleagenda.model

import javax.persistence.*
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.Size

@Entity
data class User  @JvmOverloads constructor(

    @Id
    val id: Long? = 1,
    @field:NotEmpty
//    @field:Size(max = 20, message = "Campo 'sub_segment_name' - Excedeu max de 20 Caracteres para o nome de Usuario")
    val userName: String? = "",
    val password: String? = "",

    @OneToOne
    val segment: Segment? = null,
    @OneToOne
    val subSegment: SubSegment? = null

)
