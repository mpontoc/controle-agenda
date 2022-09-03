package io.github.controleagenda.controller

import io.github.controleagenda.model.SubSegment
import io.github.controleagenda.repository.SubSegmentRepository
import io.github.controleagenda.services.SubSegmentService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.util.UriComponentsBuilder
import javax.validation.Valid

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
        @RequestBody @Valid subSegment: SubSegment,
        uriBuilder: UriComponentsBuilder
    ): ResponseEntity<*> {
        val response = subSegmentService.createSubSegment(id, subSegment)
        val idSequence = response.segment.id
        val uri = uriBuilder.path("segmentos/${idSequence}/").build().toUri()
        return ResponseEntity.created(uri).body(response)
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


