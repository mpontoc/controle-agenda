package io.github.controleagenda.controller

import io.github.controleagenda.model.Segment
import io.github.controleagenda.model.SegmentToReturn
import io.github.controleagenda.services.SegmentService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.util.UriComponentsBuilder
import javax.servlet.http.HttpServletRequest
import javax.validation.Valid

@RestController
@RequestMapping("/segmentos")
class SegmentController {

    @Autowired
    lateinit var segmentService: SegmentService

    @PostMapping("/{userId}")
    fun getAllSegments(@PathVariable userId: Long) =
        segmentService.getAllSegments(userId)

    @GetMapping("/{id}")
    fun getSegmentById(@PathVariable id: Long): ResponseEntity<SegmentToReturn> {
        return ResponseEntity.ok(segmentService.getSegmentById(1, id))
    }

    @PostMapping
    fun createSegment(
        @RequestBody @Valid segment: Segment,
        uriComponentsBuilder: UriComponentsBuilder,
        request: HttpServletRequest
    ): ResponseEntity<*> {
        val response = segmentService.createSegment(1,segment)
        val idSequence = 1
        val uri = uriComponentsBuilder.path("segmentos/${idSequence}/").build().toUri()
        return ResponseEntity.created(uri).body(response)
    }

    @PutMapping("/{id}")
    fun editSegment(
        @PathVariable id: Long,
        @RequestBody segment: Segment
    ): ResponseEntity<SegmentToReturn> {
        val segmentToEdit = Segment(id, segment.segmentName)
        return ResponseEntity.ok(segmentService.updateSegment(1,segmentToEdit))
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteSegment(@PathVariable id: Long): ResponseEntity<Void> {
        segmentService.deleteSegment(id)
        return ResponseEntity.noContent().build()
    }
}


