package io.github.controleagenda.controller

import io.github.controleagenda.model.Segment
import io.github.controleagenda.model.SegmentOnlyOneToReturn
import io.github.controleagenda.model.SegmentToReturn
import io.github.controleagenda.model.dto.SegmentDTO
import io.github.controleagenda.services.SegmentService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.util.UriComponentsBuilder
import javax.servlet.http.HttpServletRequest

@RestController
@RequestMapping("/segmentos")
class SegmentController {

    @Autowired
    lateinit var segmentService: SegmentService

    @GetMapping("/{userId}")
    fun getAllSegments(@PathVariable userId: Long) =
        segmentService.getAllSegments(userId)

    @GetMapping("/{userId}/segmento")
    fun getSegmentById(
        @PathVariable userId: Long,
        @RequestParam("segmentId") segmentId: Long
    ): ResponseEntity<SegmentOnlyOneToReturn> {
        return ResponseEntity.ok(segmentService.getSegmentById(userId, segmentId))
    }

    @PostMapping("/{userId}")
    fun createSegment(
        @PathVariable userId: Long,
        @RequestBody segment: Segment,
        uriComponentsBuilder: UriComponentsBuilder,
        request: HttpServletRequest
    ): ResponseEntity<*> {
        val response = segmentService.createSegment(userId, segment)
        val idSequence = response.user.id
        val uri = uriComponentsBuilder.path("segmentos/${idSequence}/").build().toUri()
        return ResponseEntity.created(uri).body(response)
    }

    @PutMapping("/{userId}")
    fun editSegment(
        @PathVariable userId: Long,
        @RequestBody segment: Segment
    ): ResponseEntity<SegmentToReturn> {
        return ResponseEntity.ok(segmentService.updateSegment(userId, segment))
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteSegment(@PathVariable id: Long): ResponseEntity<Void> {
        segmentService.deleteSegment(id)
        return ResponseEntity.noContent().build()
    }
}


