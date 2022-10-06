package io.github.controleagenda.controller

import io.github.controleagenda.model.Segment
import io.github.controleagenda.model.SegmentOnlyOneToReturn
import io.github.controleagenda.model.SegmentToReturn
import io.github.controleagenda.repository.SegmentRepository
import io.github.controleagenda.services.SegmentService
import io.github.controleagenda.util.Util
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.util.UriComponentsBuilder
import javax.servlet.http.HttpServletRequest

@RestController
@RequestMapping("/user/{userId}/segmentos")
class SegmentController {

    @Autowired
    lateinit var segmentService: SegmentService

    @Autowired
    lateinit var segmentRepository: SegmentRepository

    val util = Util()

    @GetMapping("/all")
    fun getAllSegments(@PathVariable("userId") userId: Long) =
        segmentService.getAllSegments(userId)

    @GetMapping("/{segmentId}")
    fun getSegmentById(
        @PathVariable("userId") userId: Long,
        @PathVariable("segmentId") segmentId: Long,
    ): ResponseEntity<SegmentOnlyOneToReturn> {
        return ResponseEntity.ok(segmentService.getSegmentById(userId, segmentId))
    }

    @PostMapping
    fun createSegment(
        @PathVariable("userId") userId: Long,
        @RequestBody segment: Segment,
        uriComponentsBuilder: UriComponentsBuilder,
        request: HttpServletRequest
    ): ResponseEntity<*> {
        val idSequence = util.idSequenceSegment(segmentRepository)
        val _segment = segment
        _segment.id = idSequence
        val response = segmentService.createSegment(userId, _segment)
        val uri =
            uriComponentsBuilder.path("/segmentos/${response.user.id}/segmento").queryParam("id=${idSequence}").build()
                .toUri()
        return ResponseEntity.created(uri).body(response)
    }

    @PutMapping
    fun editSegment(
        @PathVariable("userId") userId: Long,
        @RequestBody segment: Segment
    ): ResponseEntity<SegmentToReturn> {
        return ResponseEntity.ok(segmentService.updateSegment(userId, segment))
    }

    @DeleteMapping("/{segmentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteSegment(
        @PathVariable("userId") userId: Long,
        @PathVariable("segmentId") segmentId: Long,
    ): ResponseEntity<Void> {
        segmentService.deleteSegment(userId, segmentId)
        return ResponseEntity.noContent().build()
    }
}


