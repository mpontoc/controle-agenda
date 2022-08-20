package io.github.controleagenda.controller

import io.github.controleagenda.model.Segment
import io.github.controleagenda.model.SegmentToReturn
import io.github.controleagenda.services.SegmentService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.util.UriComponentsBuilder

@RestController
@RequestMapping("/segmentos")
class SegmentController {

    @Autowired
    lateinit var segmentService: SegmentService

    @GetMapping
    fun getAllSegments() =
        segmentService.getAllSegments()

    @GetMapping("/{id}")
    fun getSegmentById(@PathVariable id: Long): ResponseEntity<SegmentToReturn> {

        var segmentFinded: SegmentToReturn

        try {
            segmentFinded = segmentService.getSegmentById(id)
        } catch (e: Exception) {
            segmentFinded = SegmentToReturn()
        }

        return if (!segmentFinded.segment.segmentName?.isEmpty()!!)
            ResponseEntity.ok(segmentFinded)
        else ResponseEntity.notFound().build()
    }

    @PostMapping
    fun createSegment(
        @RequestBody segment: Segment,
        uriBuilder: UriComponentsBuilder
    ): ResponseEntity<*> {
        return if (segment.segmentName!!.isNotEmpty() && segment.segmentName.length <= 20 && segmentService.getAllSegments()
                .count() < 10
        ) {
            val response =
                segmentService.createSegment(
                    segment
                )
            val idSequence = response.segment.id
            val uri = uriBuilder.path("segmentos/${idSequence}/").build().toUri()
            ResponseEntity.created(uri).body(response)
        } else if (segment.segmentName.isEmpty()) {
            ResponseEntity.badRequest().body(
                mapOf(
                    "statusCode" to "400",
                    "message" to "campo 'segmentName' nao permitido quando valor no envio estiver vazio",
                )
            )
        } else
            ResponseEntity.badRequest().body(
                mapOf(
                    "statusCode" to "400",
                    "message" to "Excedeu max de Segmentos/Caracteres",
                    "maxSegmentoLista" to "10",
                    "maxCaracteresNomeSegmento" to "20"
                )
            )
    }

    @PutMapping("/{id}")
    fun editSegment(
        @PathVariable id: Long,
        @RequestBody segment: Segment
    ): ResponseEntity<SegmentToReturn> {
        val segmentToEdit = Segment(id, segment.segmentName)
        return ResponseEntity.ok(segmentService.updateSegment(segmentToEdit))
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteSegment(@PathVariable id: Long) {
        segmentService.deleteSegment(id)
    }
}


