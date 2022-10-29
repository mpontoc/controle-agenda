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
@RequestMapping("/users/{usersId}/segmentos")
class SegmentController {

    @Autowired
    lateinit var segmentService: SegmentService

    @Autowired
    lateinit var segmentRepository: SegmentRepository

    val util = Util()

    @CrossOrigin
    @GetMapping("/all")
    fun getAllSegments(@PathVariable("usersId") usersId: Long) =
        segmentService.getAllSegments(usersId)

    @CrossOrigin
    @GetMapping("/{segmentId}")
    fun getSegmentById(
        @PathVariable("usersId") usersId: Long,
        @PathVariable("segmentId") segmentId: Long,
    ): ResponseEntity<SegmentOnlyOneToReturn> {
        return ResponseEntity.ok(segmentService.getSegmentById(usersId, segmentId))
    }

    @CrossOrigin
    @PostMapping
    fun createSegment(
        @PathVariable("usersId") usersId: Long,
        @RequestBody segment: Segment,
        uriComponentsBuilder: UriComponentsBuilder,
        request: HttpServletRequest
    ): ResponseEntity<*> {
        val idSequence = util.idSequenceSegment(segmentRepository)
        val _segment = segment
        _segment.id = idSequence
        val response = segmentService.createSegment(usersId, _segment)
        val uri =
            uriComponentsBuilder.path("/segmentos/${response.users.id}/segmento").queryParam("id=${idSequence}").build()
                .toUri()
        return ResponseEntity.created(uri).body(response)
    }

    @CrossOrigin
    @PutMapping
    fun editSegment(
        @PathVariable("usersId") usersId: Long,
        @RequestBody segment: Segment
    ): ResponseEntity<SegmentToReturn> {
        return ResponseEntity.ok(segmentService.updateSegment(usersId, segment))
    }

    @CrossOrigin
    @DeleteMapping("/{segmentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteSegment(
        @PathVariable("usersId") usersId: Long,
        @PathVariable("segmentId") segmentId: Long,
    ): ResponseEntity<Void> {
        segmentService.deleteSegment(usersId, segmentId)
        return ResponseEntity.noContent().build()
    }
}


