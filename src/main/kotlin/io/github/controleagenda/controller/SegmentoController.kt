package io.github.controleagenda.controller

import io.github.controleagenda.model.Segment
import io.github.controleagenda.services.SegmentService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
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
    fun createSegment(@RequestBody segment: Segment): Segment {
        val idSequence = getAllSegments().count().toLong() + 1
        return segmentService.addSegment(idSequence , segment)
    }

    @DeleteMapping("/{id}")
    fun deleteSegment(@PathVariable id: Long) {
        segmentService.deleteSegment(id)
    }

    @PutMapping("/{id}")
    fun editSegment(
        @PathVariable id: Long,
        @RequestBody segments: Segment
    ) {
        segmentService.updateSegments(id, segments)
    }

}