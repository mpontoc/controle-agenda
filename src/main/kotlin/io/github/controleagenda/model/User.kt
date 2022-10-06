package io.github.controleagenda.model

import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.OneToMany
import javax.persistence.Table
import javax.validation.constraints.NotEmpty

@Entity
@Table(name = "user")
data class User  @JvmOverloads constructor(

    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = 1,
    @field:NotEmpty
//    @field:Size(max = 20, message = "Campo 'sub_segment_name' - Excedeu max de 20 Caracteres para o nome de Usuario")
    val userName: String? = "",
    val password: String? = "",

    @OneToMany(mappedBy = "user", orphanRemoval = true)
    val segment: MutableList<Segment?> = mutableListOf(),

    @OneToMany(mappedBy = "user", orphanRemoval = true)
    val subSegment: MutableList<SubSegment?> = mutableListOf()

)
