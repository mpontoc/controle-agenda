package io.github.controleagenda.controller

import io.github.controleagenda.model.Segment
import io.github.controleagenda.model.SubSegment
import io.github.controleagenda.services.SubSegmentService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.util.UriComponentsBuilder

@RestController
@RequestMapping("/subsegmento")
class SubSegmentController {

    @Autowired
    lateinit var subSegmentService: SubSegmentService

    @PostMapping()
    fun createSegment(@RequestBody segmento: Segment, uriBuilder: UriComponentsBuilder): ResponseEntity<*> {
//        val idSequence = segmentService.getAllSegments().count().toLong() + 1
        val uri = uriBuilder.path("/1/").build().toUri()
        return ResponseEntity.created(uri).body(subSegmentService.addSubSegment(segmento?.id!!.toLong() ,
            segmento.subSegment!![0]!!
        ))
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


