package io.github.controleagenda.model

import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.OneToMany
import javax.persistence.Table
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.Size

@Entity
@Table(name = "users")
data class Users  @JvmOverloads constructor(

    @Id
    var id: Long? = 1,
    @field:NotEmpty
    @field:Size(max = 20, message = "Campo 'sub_segment_name' - Excedeu max de 20 Caracteres para o nome de Usuario")
    val usersName: String? = "",
    val password: String? = "",

    @OneToMany(mappedBy = "users", orphanRemoval = true)
    val segment: MutableList<Segment?> = mutableListOf(),

    @OneToMany(mappedBy = "users", orphanRemoval = true)
    val subSegment: MutableList<SubSegment?> = mutableListOf()

)
