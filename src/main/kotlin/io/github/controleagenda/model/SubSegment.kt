package io.github.controleagenda.model

import com.fasterxml.jackson.annotation.JsonProperty
import javax.persistence.*
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.Size

@Entity
@Table(name = "sub_segment")
data class SubSegment @JvmOverloads constructor(

    @Id
    var id: Long = 1,
    @JsonProperty("sub_segment_name")
    @field:NotEmpty(message = "Campo 'sub_segment_name' - Nome da Tarefa nao pode ser em branco")
    @field:Size(max = 20, message = "Campo 'sub_segment_name' - Excedeu max de 20 Caracteres para o nome da Tarefa")
    val subSegmentName: String = "crie a sua tarefa",
    val message: String = "aqui descreva sua tarefa",

    @ManyToOne(fetch = FetchType.EAGER)
    val users: Users? = null,

    @ManyToOne(fetch = FetchType.EAGER)
    val segment: Segment? = null

)
