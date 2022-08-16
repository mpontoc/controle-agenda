package io.github.controleagenda.controller

import io.github.controleagenda.model.Segment
import io.github.controleagenda.model.SegmentToReturn
import io.github.controleagenda.model.SubSegment
import io.github.controleagenda.repository.SubSegmentRepository
import io.github.controleagenda.services.SubSegmentService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.util.UriComponentsBuilder

@RestController
@RequestMapping("/segmentos/sub-segmentos")
class SubSegmentController {

    @Autowired
    lateinit var subSegmentService: SubSegmentService

    @Autowired
    lateinit var subSegmentRepository: SubSegmentRepository

    @PostMapping("/{id}")
    fun createSubSegment(
        @PathVariable id: Long,
        @RequestBody subSegment: SubSegment,
        uriBuilder: UriComponentsBuilder
    ): ResponseEntity<*> {
        return if (subSegment.subSegmentName!!.isNotEmpty() && subSegment.subSegmentName.length <= 20 && subSegmentRepository.findSubSegmentToSegmentID(
                id
            ).count() < 20
        ) {
            val response = subSegmentService.addSubSegment(
                id,
                subSegment
            )
            val uri = uriBuilder.path("segmentos/${id}/").build().toUri()
            ResponseEntity.created(uri).body(
                response
            )
        } else if (subSegment.subSegmentName.isEmpty()) {
            ResponseEntity.badRequest().body(
                mapOf(
                    "statusCode" to "400",
                    "message" to "campo 'subSegmentName' nao permitido quando valor no envio estiver vazio",
                )
            )
        } else
            ResponseEntity.badRequest().body(
                mapOf(
                    "statusCode" to "400",
                    "message" to "Excedeu max de SubSegmentos/Caracteres",
                    "maxSubSegmentoLista" to "20",
                    "maxCaracteresNomeSegmento" to "20"
                )
            )
    }

    @PutMapping("/{id}")
    fun editSubSegment(
        @PathVariable id: Long,
        @RequestBody subSegment: SubSegment
    ): ResponseEntity<SubSegment> {
        var subSegmentToEdit =
            SubSegment(
                id, subSegment.subSegmentName, subSegment.message
            )
        return ResponseEntity.ok(subSegmentService.updateSubSegment(subSegmentToEdit))
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteSubSegment(@PathVariable id: Long) {
        subSegmentService.deleteSubSegment(id)
    }
}


