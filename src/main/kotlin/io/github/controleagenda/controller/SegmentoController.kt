package io.github.controleagenda.controller

import io.github.controleagenda.model.Segment
import io.github.controleagenda.services.SegmentService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.util.UriComponentsBuilder
import java.util.*

@RestController
@RequestMapping("/segmentos")
class SegmentoController {

    @Autowired
    lateinit var segmentService: SegmentService

    @GetMapping()
    fun getAllSegments() =
        segmentService.getAllSegments()

    @GetMapping("/{id}")
    fun getSegmentById(@PathVariable id: Long): Optional<Segment?> {
        return segmentService.getSegmentById(id)
    }

    @PostMapping()
    fun createSegment(@RequestBody segment: Segment, uriBuilder: UriComponentsBuilder): ResponseEntity<Segment> {
        val idSequence = getAllSegments().count().toLong() + 1
        val uri = uriBuilder.path("/${idSequence}/").build().toUri()
        return ResponseEntity.created(uri).body(segmentService.addSegment(idSequence, segment))
    }

    @PutMapping("/{id}")
    fun editSegment(
        @PathVariable id: Long,
        @RequestBody segments: Segment
    ): ResponseEntity<Segment> {
        return ResponseEntity.ok(segmentService.updateSegments(id, segments))
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteSegment(@PathVariable id: Long) {
        segmentService.deleteSegment(id)
    }
}