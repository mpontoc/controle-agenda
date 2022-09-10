package io.github.controleagenda.services

import io.github.controleagenda.commons.Util
import io.github.controleagenda.exception.BackendException
import io.github.controleagenda.exception.NotFoundException
import io.github.controleagenda.model.Segment
import io.github.controleagenda.repository.SegmentRepository
import io.github.controleagenda.repository.SubSegmentRepository
import io.github.controleagenda.services.impl.SegmentServiceImpl
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers.any
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.jupiter.MockitoExtension
import java.util.*

@ExtendWith(MockitoExtension::class)
class SegmentServiceTest {

    @InjectMocks
    lateinit var segmentService: SegmentServiceImpl

    @Mock
    lateinit var segmentRepository: SegmentRepository

    @Mock
    lateinit var subSegmentRepository: SubSegmentRepository

    @Mock
    lateinit var util: Util

    @Test
    fun getAllSegmentsService() {

        Mockito.`when`(segmentRepository.findAll()).thenReturn(util.getAllSegmentsDefault())
        val segments = segmentService.getAllSegments()
        if (segments.toString().contains(util.listSegmentsDefault()[1].segment.segmentName!!))
            Assertions.assertTrue(true)
        else
            Assertions.assertTrue(false)
    }

    @Test
    fun getSegmentByIdService() {
        Mockito.`when`(segmentRepository.findById(2)).thenReturn(
            Optional.of(util.listSegmentsDefault()[1].segment)
        )
        Mockito.`when`(segmentRepository.findSegmentById(2)).thenReturn(util.listSegmentsDefault()[1].segment)

        val segment = segmentService.getSegmentById(2)
        if (segment.toString().contains(util.listSegmentsDefault()[1].segment.segmentName!!))
            Assertions.assertTrue(true)
        else
            Assertions.assertTrue(false)
    }

    @Test
    fun getSegmentByInvalidIdService() {
        val exception = assertThrows<NotFoundException> { segmentService.getSegmentById(99) }
        val exceptionExpected = "Segmento com o id 99 não existe no banco de dados"
        Assertions.assertEquals(exceptionExpected, exception.message)
    }

    @Test
    fun deleteSegmentService() {
        Mockito.`when`(segmentRepository.findById(10)).thenReturn(
            Optional.of(Segment(10, "restTest"))
        )
        Mockito.`when`(subSegmentRepository.findSubSegmentToSegmentID(10)).thenReturn(
            util.segmentToReturn("restTest").subSegment
        )
        segmentService.deleteSegment(10)
    }

    @Test
    fun deleteSegmentNoPermissionService() {
        val exception = assertThrows<BackendException> {
            segmentService.deleteSegment(3)
        }
        val exceptionExpected = "Não é possível apagar os valores default"
        Assertions.assertEquals(exceptionExpected, exception.message)
    }

    @Test
    fun deleteSegmentNotFundedService() {
        val exception = assertThrows<NotFoundException> {
            segmentService.deleteSegment(11)
        }
        val exceptionExpected = "Segmento com o id 11 não existe no banco de dados"
        Assertions.assertEquals(exceptionExpected, exception.message)
    }

    @Test
    fun editSegmentService() {
        Mockito.`when`(segmentRepository.findById(2)).thenReturn(
            Optional.of(util.listSegmentsDefault()[1].segment)
        )
        Mockito.`when`(segmentRepository.save(any())).thenReturn(Segment(2, "segmentEdited"))
        val segment = segmentService.updateSegment(Segment(2, "segmentEdited"))
        Assertions.assertTrue(segment.segment.segmentName == "segmentEdited")
    }

    @Test
    fun editSegmentNotFoundedService() {
        val exception = assertThrows<NotFoundException> {
            segmentService.updateSegment(Segment(11, "segmentEdited"))
        }
        val exceptionExpected = "Segmento com o id 11 não existe no banco de dados"
        Assertions.assertEquals(exceptionExpected, exception.message)
    }

    @Test
    fun createSegment() {
        val segment = Segment(9, "test-rest-assured")
        Mockito.`when`(segmentRepository.findAll()).thenReturn(util.getAllSegmentsDefault())
        Mockito.`when`(segmentRepository.save(any())).thenReturn(segment)
        Mockito.`when`(subSegmentRepository.save(any()))
            .thenReturn(util.listSegmentsDefault()[1].subSegment[0])
        val segmentCreated = segmentService.createSegment(segment)
        Assertions.assertEquals(segment.segmentName, segmentCreated.segment.segmentName)
    }

    @Test
    fun createSegmentOverLimit() {
        val segment = Segment(11, "test-rest-assured")
        Mockito.`when`(segmentRepository.findAll()).thenReturn(util.gelAllSegmentsValueOnLimit())
        val exception = assertThrows<BackendException> { segmentService.createSegment(segment) }
        val exceptionExpected = "Atingiu a quantidade máxima Segmentos -> qtd max = 10"
        Assertions.assertEquals(exceptionExpected, exception.message)
    }
}


