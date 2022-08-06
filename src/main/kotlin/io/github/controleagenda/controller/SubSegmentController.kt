package io.github.controleagenda.controller

import io.github.controleagenda.model.Segment
import io.github.controleagenda.model.SubSegment
import io.github.controleagenda.services.SubSegmentService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.util.UriComponentsBuilder

@RestController
@RequestMapping("/segmentos")
class SubSegmentController {

    @Autowired
    lateinit var subSegmentService: SubSegmentService

    @PostMapping("/{id}")
    fun createSubSegment(
        @PathVariable id: Long,
        @RequestBody subSegment: SubSegment,
        uriBuilder: UriComponentsBuilder
    ): ResponseEntity<*> {
        val uri = uriBuilder.path("/1/").build().toUri()
        return ResponseEntity.created(uri).body(
            subSegmentService.addSubSegment(
                id!!.toLong(),
                subSegment
            )
        )
    }

//    @PutMapping("/{id}")
//    fun editSegment(
//        @PathVariable id: Long,
//        @RequestBody segment: Segment, subSegment: SubSegment
//    ): ResponseEntity<Segment> {
//        return ResponseEntity.ok(segmentService.updateSegment(id, segment, subSegment))
//    }
//
//    @DeleteMapping("/{id}")
//    @ResponseStatus(HttpStatus.NO_CONTENT)
//    fun deleteSegment(@PathVariable id: Long) {
//        segmentService.deleteSegment(id)
//    }
}


