package io.github.controleagenda.services

import io.github.controleagenda.commons.Utils
import io.github.controleagenda.model.Segment
import io.github.controleagenda.model.SegmentToReturn
import io.github.controleagenda.model.SubSegment
import io.github.controleagenda.repository.SegmentRepository
import io.github.controleagenda.repository.SubSegmentRepository
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.util.UriComponents
import org.springframework.web.util.UriComponentsBuilder

@ExtendWith(MockitoExtension::class)
class SegmentServiceTest {

    @Autowired
    lateinit var segmentService: SegmentService

    @Mock
    lateinit var segmentRepository: SegmentRepository

    @Mock
    lateinit var subSegmentRepository: SubSegmentRepository

    @Mock
    lateinit var uriComponentsBuilder: UriComponentsBuilder

    @Mock
    lateinit var uriComponents: UriComponents

    @Mock
    lateinit var util: Utils

    @Test
    fun getAllSegmentsService() {

        val segments = segmentService.getAllSegments()

        if (segments.toString().contains(util.listSegmentsDefault()[1].segment.segmentName!!))
            Assertions.assertTrue(true)
        else
            Assertions.assertTrue(false)
    }

    @Test
    fun getSegmentByIdService() {
        val segment = segmentService.getSegmentById(2)

        if (segment.toString().contains(util.listSegmentsDefault()[1].segment.segmentName!!))
            Assertions.assertTrue(true)
        else
            Assertions.assertTrue(false)
    }

    @Test
    fun getSegmentByInvalidIdService() {
        var segment: SegmentToReturn

        try {
            segment =  segmentService.getSegmentById(99)
        } catch (e: Exception) {
            segment = SegmentToReturn()
            println("Segment not founded")
        }

        if (segment.segment.segmentName == "")
            Assertions.assertTrue(true)
        else
            Assertions.assertTrue(false)
    }


    @Test
    fun deleteSegmentService() {
        util.createSegment(segmentService, 98, "testUnitario")
        segmentService.deleteSegment(98)
    }

    @Test
    fun deleteSegmentNoPermissionService() {
        val exception = assertThrows<RuntimeException> {
            segmentService.deleteSegment(3)
        }
        val exceptionExpected = "Não é possível apagar os valores default"
        Assertions.assertEquals(exceptionExpected, exception.message)

    }

    @Test
    fun editSegmentService() {
        util.createSegment(segmentService, 110, "segmentToEdit")
        segmentService.updateSegment(Segment(110, "segmentEdited"))
        segmentService.deleteSegment(110)
    }

    @Test
    fun createSegment() {

        val segment = Segment(
            9,
            "test-rest-assured"
        )
        Mockito.`when`(segmentRepository.save(Mockito.any(Segment::class.java))).thenReturn(
            segment
        )

        Mockito.`when`(subSegmentRepository.save(Mockito.any(SubSegment::class.java))).thenReturn(
            SubSegment(
                11,
                "test",
                "",
                segment
            )
        )
    }


}