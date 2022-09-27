package io.github.controleagenda.model

import com.fasterxml.jackson.annotation.JsonProperty
import javax.persistence.*
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.Size

@Entity
data class Segment @JvmOverloads constructor(

    @Id
    val segmentId: Long = 1,
    @JsonProperty("segment_name")
    @field:NotEmpty(message = "Campo 'segment_name' - Nome do Segemento nao pode ser em branco")
//    @field:Size(max = 20, message = "Campo 'segment_name' - Excedeu max de 20 Caracteres para o nome do Segmento")
    val segmentName: String = "segmentDefault",

    @OneToOne
    val user: User? = null,

    @OneToOne
    val subSegment: SubSegment? = null


)

