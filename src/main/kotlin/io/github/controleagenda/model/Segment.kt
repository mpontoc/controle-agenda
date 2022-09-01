package io.github.controleagenda.model

import com.fasterxml.jackson.annotation.JsonAutoDetect
import com.fasterxml.jackson.annotation.JsonProperty
import javax.persistence.Entity
import javax.persistence.Id
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.Size

@Entity
@JsonAutoDetect
data class Segment(

    @Id
    val id: Long? = null,
    @JsonProperty("segment_name")
    @field:NotEmpty(message = "Nome Segemento nao pode ser em branco")
    @field:Size(max = 10, message = "Excedeu max de 10 Caracteres para o nome do Segmento")
    val segmentName: String? = ""


)

