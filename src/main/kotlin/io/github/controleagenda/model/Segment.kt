package io.github.controleagenda.model

import com.fasterxml.jackson.annotation.JsonProperty
import javax.persistence.Entity
import javax.persistence.Id
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.Size

@Entity
data class Segment(

    @Id
    val id: Long = -1,
    @JsonProperty("segment_name")
    @field:NotEmpty(message = "Campo 'segment_name' - Nome do Segemento nao pode ser em branco")
    @field:Size(max = 20, message = "Campo 'segment_name' - Excedeu max de 20 Caracteres para o nome do Segmento")
    val segmentName: String = ""


)

