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

    @GetMapping()
    fun getAllSegments() =
        segmentService.getAllSegments()

    @GetMapping("/{id}")
    fun getSegmentById(@PathVariable id: Long): ResponseEntity<SegmentToReturn> {
        val segmentFinded: SegmentToReturn = segmentService.getSegmentById(id)
        return if (!segmentFinded.segment.segment?.isEmpty()!!)
            ResponseEntity.ok(segmentFinded)
        else ResponseEntity.notFound().build()
    }

    @PostMapping()
    fun createSegment(
        @RequestBody segment: Segment,
        uriBuilder: UriComponentsBuilder
    ): ResponseEntity<SegmentToReturn> {
        val idSequence = segmentService.getAllSegments().count().toLong() + 1
        val uri = uriBuilder.path("/${idSequence}/").build().toUri()
        return ResponseEntity.created(uri).body(
            segmentService.createSegment(
                idSequence, segment
            )
        )
    }

    @PutMapping("/{id}")
    fun editSegment(
        @PathVariable id: Long,
        @RequestBody segment: Segment
    ): ResponseEntity<SegmentToReturn> {
        val segmentToEdit = Segment(id, segment.segment)
        return ResponseEntity.ok(segmentService.updateSegment(segmentToEdit))
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteSegment(@PathVariable id: Long) {
        segmentService.deleteSegment(id)
    }
}


