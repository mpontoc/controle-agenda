package io.github.controleagenda.controller

import io.github.controleagenda.model.SubSegment
import io.github.controleagenda.model.dto.SubSegmentDTO
import io.github.controleagenda.repository.SubSegmentRepository
import io.github.controleagenda.services.SubSegmentService
import io.github.controleagenda.util.Util
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.util.UriComponentsBuilder
import javax.validation.Valid

@RestController
@RequestMapping("/users/{usersId}/segmentos/{segmentId}/sub-segmentos")
class SubSegmentController {

    @Autowired
    lateinit var subSegmentService: SubSegmentService

    @Autowired
    lateinit var subSegmentRepository: SubSegmentRepository
    val util = Util()

    @PostMapping
    fun createSubSegment(
        @PathVariable usersId: Long,
        @PathVariable segmentId: Long,
        @RequestBody @Valid subSegment: SubSegment,
        uriBuilder: UriComponentsBuilder
    ): ResponseEntity<*> {
        val idSequence = util.idSequenceSubSegment(subSegmentRepository)
        subSegment.id = idSequence
        val response = subSegmentService.createSubSegment(usersId, segmentId, subSegment)
        val uri = uriBuilder.path("segmentos/${idSequence}/").build().toUri()
        return ResponseEntity.created(uri).body(response)
    }

    @PutMapping
    fun editSubSegment(
        @PathVariable usersId: Long,
        @PathVariable segmentId: Long,
        @RequestBody subSegment: SubSegment
    ): ResponseEntity<SubSegmentDTO> {
        return ResponseEntity.ok(subSegmentService.updateSubSegment(usersId, subSegment))
    }

    @DeleteMapping("/{subSegmentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteSubSegment(
        @PathVariable usersId: Long,
        @PathVariable segmentId: Long,
        @PathVariable subSegmentId: Long,
    ) {
        subSegmentService.deleteSubSegment(usersId, segmentId, subSegmentId)
    }
}


