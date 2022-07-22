package io.github.controleagenda.services

import io.github.controleagenda.commons.Utils
import io.github.controleagenda.repository.SegmentsRepository
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mock
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup

@SpringBootTest
class SegmentServiceTest {

    @Autowired
    lateinit var segmentService: SegmentService

    @Mock
    lateinit var util: Utils

    @Mock
    lateinit var repository: SegmentsRepository

    @BeforeEach
    fun setup() {
        standaloneSetup(segmentService)
    }

    @Test
    fun getAllSegmentsService() {

        val segments = segmentService.getAllSegments()

        if (segments.toString().contains(util.listSegmentsDefault().get(1).segment))
            Assertions.assertTrue(true)
        else
            Assertions.assertTrue(false)
    }

    @Test
    fun getSegmentByIdService() {
        val segment = segmentService.getSegmentById(2)

        if (segment.toString().contains(util.listSegmentsDefault().get(1).segment))
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

}