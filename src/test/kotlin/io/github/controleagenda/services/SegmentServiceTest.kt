package io.github.controleagenda.services

import io.github.controleagenda.commons.Utils
import io.github.controleagenda.model.Segment
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.Mock
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class SegmentServiceTest {

    @Autowired
    lateinit var segmentService: SegmentService

    @Mock
    lateinit var util: Utils

    @Test
    fun getAllSegmentsService() {

        val segments = segmentService.getAllSegments()

        if (segments.toString().contains(util.listSegmentsDefault()[1].segment))
            Assertions.assertTrue(true)
        else
            Assertions.assertTrue(false)
    }

    @Test
    fun getSegmentByIdService() {
        val segment = segmentService.getSegmentById(2)

        if (segment.toString().contains(util.listSegmentsDefault()[1].segment))
            Assertions.assertTrue(true)
        else
            Assertions.assertTrue(false)
    }

    @Test
    fun getSegmentByInvalidIdService() {
        val segment = segmentService.getSegmentById(99)

        if (segment.isEmpty)
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
        segmentService.updateSegments(110, Segment(110, "segmentEdited"))
        segmentService.deleteSegment(110)
    }


}