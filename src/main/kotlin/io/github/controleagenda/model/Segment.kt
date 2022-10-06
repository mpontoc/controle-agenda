package io.github.controleagenda.model

import com.fasterxml.jackson.annotation.JsonProperty
import javax.persistence.*
import javax.validation.constraints.NotEmpty

@Entity
@Table(name = "segment")
data class Segment @JvmOverloads constructor(

    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 1,
    @JsonProperty("segment_name")
    @field:NotEmpty(message = "Campo 'segment_name' - Nome do Segemento nao pode ser em branco")
//    @field:Size(max = 20, message = "Campo 'segment_name' - Excedeu max de 20 Caracteres para o nome do Segmento")
    val segmentName: String = "segmentDefault",

    @ManyToOne(fetch = FetchType.EAGER)
    val user: User? = null,

    @OneToMany(mappedBy = "segment", cascade = [CascadeType.ALL], orphanRemoval = true)
    val subSegment: MutableList<SubSegment?> = mutableListOf()


)

