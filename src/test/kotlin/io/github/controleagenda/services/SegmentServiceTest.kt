package io.github.controleagenda.services

import io.github.controleagenda.commons.Utils
import io.github.controleagenda.exception.BackendException
import io.github.controleagenda.model.Segment
import io.github.controleagenda.model.SegmentToReturn
import io.github.controleagenda.repository.SegmentRepository
import io.github.controleagenda.repository.SubSegmentRepository
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.web.util.UriComponents
import org.springframework.web.util.UriComponentsBuilder

@ExtendWith(MockitoExtension::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class SegmentServiceTest {

    @Mock
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

        Mockito.`when`(segmentService.getAllSegments()).thenReturn(util.listSegmentsDefault())
        val segments = segmentService.getAllSegments()
        if (segments.toString().contains(util.listSegmentsDefault()[1].segment.segmentName!!))
            Assertions.assertTrue(true)
        else
            Assertions.assertTrue(false)
    }

    @Test
    fun getSegmentByIdService() {
        Mockito.`when`(segmentService.getSegmentById(2)).thenReturn(util.listSegmentsDefault()[1])
        val segment = segmentService.getSegmentById(2)
        if (segment.toString().contains(util.listSegmentsDefault()[1].segment.segmentName!!))
            Assertions.assertTrue(true)
        else
            Assertions.assertTrue(false)
    }

    @Test
    fun getSegmentByInvalidIdService() {
        Mockito.`when`(segmentService.getSegmentById(99)).thenThrow(EmptyResultDataAccessException::class.java)
        var segment: SegmentToReturn
        try {
            segment = segmentService.getSegmentById(99)
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
        Mockito.`when`(segmentService.deleteSegment(1)).thenReturn(
            "O usuario ${util.listSegmentsDefault()[0].segment.segmentName} foi deltado com sucesso"
        )
        segmentService.deleteSegment(1)
    }

    @Test
    fun deleteSegmentNoPermissionService() {

        Mockito.`when`(segmentService.deleteSegment(3)).thenThrow(
            BackendException("Não é possível apagar os valores default")
        )

        val exception = assertThrows<BackendException> {
            segmentService.deleteSegment(3)
        }
        val exceptionExpected = "Não é possível apagar os valores default"
        Assertions.assertEquals(exceptionExpected, exception.message)

    }

    @Test
    fun editSegmentService() {

        Mockito.`when`(segmentService.updateSegment(Segment(1, "segmentEdited")))
            .thenReturn(util.segmentToReturn("segmentEdited"))

        val segment = segmentService.updateSegment(Segment(1, "segmentEdited"))

        Assertions.assertTrue(segment.segment.segmentName == "segmentEdited")

    }

    @Test
    fun createSegment() {

        val segment = Segment(
            9,
            "test-rest-assured"
        )

        val segmentToReturn = util.segmentToReturn("test-rest-assured")

        Mockito.`when`(segmentService.createSegment(segment)).thenReturn(segmentToReturn)

        val segmentCreated = segmentService.createSegment(segment)
        Assertions.assertEquals(segment.segmentName, segmentCreated.segment.segmentName)

    }


}