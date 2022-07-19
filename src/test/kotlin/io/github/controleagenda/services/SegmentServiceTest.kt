package io.github.controleagenda.services

import io.github.controleagenda.model.Segment
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.util.Assert
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import java.util.*

@SpringBootTest
class SegmentServiceTest {

    @Autowired
    lateinit var segmentService: SegmentService

    @Test
    fun getAllSegments() {

        val segments = segmentService.getAllSegments()
        Assert.hasText("Academia" , segments[0].toString())


    }

    @GetMapping("/{id}")
    fun getSegmentById(@PathVariable id: Long): Optional<Segment?> {
        return segmentService.getSegmentById(id)
    }

//    @PostMapping()
//    fun createSegment(@RequestBody segment: Segment, uriBuilder: UriComponentsBuilder): ResponseEntity<Segment> {
//        val idSequence = getAllSegments().count().toLong() + 1
//        val uri = uriBuilder.path("/${idSequence}/").build().toUri()
//        return ResponseEntity.created(uri).body(segmentService.addSegment(idSequence, segment))
//    }
//
//    @PutMapping("/{id}")
//    fun editSegment(
//        @PathVariable id: Long,
//        @RequestBody segments: Segment
//    ): ResponseEntity<Segment> {
//        return ResponseEntity.ok(segmentService.updateSegments(id, segments))
//    }
//
//    @DeleteMapping("/{id}")
//    @ResponseStatus(HttpStatus.NO_CONTENT)
//    fun deleteSegment(@PathVariable id: Long) {
//        segmentService.deleteSegment(id)
//    }
}