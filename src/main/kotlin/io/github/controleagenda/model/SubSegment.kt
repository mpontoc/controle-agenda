package io.github.controleagenda.model

import com.fasterxml.jackson.annotation.JsonProperty
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.OneToOne
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.Size

@Entity
data class SubSegment @JvmOverloads constructor(

    @Id
    var id: Long? = null,
    @JsonProperty("sub_segment_name")
    @field:NotEmpty(message = "Campo 'sub_segment_name' - Nome da Tarefa nao pode ser em branco")
    @field:Size(max = 20, message = "Campo 'sub_segment_name' - Excedeu max de 20 Caracteres para o nome da Tarefa")
    val subSegmentName: String? = "",
    val message: String? = "",

    @OneToOne
    val segment: Segment = Segment(1, "")

)
