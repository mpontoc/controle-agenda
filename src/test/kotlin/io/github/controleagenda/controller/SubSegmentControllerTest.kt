package io.github.controleagenda.controller

import io.github.controleagenda.commons.UtilTest
import io.github.controleagenda.model.Segment
import io.github.controleagenda.model.SegmentToReturn
import io.github.controleagenda.model.SubSegment
import io.github.controleagenda.services.SubSegmentService
import io.restassured.RestAssured
import io.restassured.response.Response
import org.json.JSONObject
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.boot.test.context.SpringBootTest
import javax.annotation.Resource

@ExtendWith(MockitoExtension::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class SubSegmentControllerTest {

    @Mock
    lateinit var utilTest: UtilTest

    @Mock
    lateinit var subSegmentService: SubSegmentService

    @InjectMocks
    @Resource
    lateinit var subSegmentController: SubSegmentController

    @Test
    fun testPostToCreateNewSubSegmentByController() {

        val segmentToReturn = SegmentToReturn(
            Segment(9, "test-rest-assured"),
            mutableListOf(
                SubSegment(
                    9, "test-rest-assured",
                    "bicpes, braços, remada"
                )
            )
        )

        Mockito.`when`(subSegmentService.createSubSegment(9, segmentToReturn.subSegment[0]))
            .thenReturn(segmentToReturn)

        val response: Response =
            RestAssured
                .given()
                .contentType("application/json")
                .`when`()
                .body(segmentToReturn.subSegment[0])
                .post("/controleagenda/segmentos/sub-segmentos/9")
                .then()
                .statusCode(201)
                .log()
                .all().extract().response()

        Assertions.assertEquals(
            "test-rest-assured",
            response.body.jsonPath().getJsonObject<JSONObject>("sub_segment.sub_segment_name[0]")
        )
    }

//    @Test
//    fun deleteSubSegmentByController() {
//
//        Mockito.`when`(subSegmentService.deleteSubSegment(1).thenReturn(
//            "O usuario ${utilTest.listSegmentsDefault()[0].segment.segmentName} foi deltado com sucesso"
//        )
//
//        val response = RestAssured
//            .given()
//            .contentType("application/json")
//            .`when`()
//            .delete("/controleagenda/segmentos/sub-segmentos/1")
//            .then()
//            .statusCode(204)
//            .log()
//            .all().extract().response()
//
//        println(response)
//    }

//    @Test
//    fun editSegmentByController() {
//
//        Mockito.`when`(subSegmentService.updateSegment(Segment(1, "segmentEdited")))
//            .thenReturn(utilTest.segmentToReturn("segmentEdited"))
//
//        val response = RestAssured
//            .given()
//            .contentType("application/json")
//            .`when`()
//            .body(
//                "{\"segment_name\": \"segmentEdited\"}"
//            )
//            .put("/controleagenda/segmentos/sub-segmentos/1")
//            .then()
//            .statusCode(200)
//            .log()
//            .all().extract().response()
//
//        Assertions.assertEquals(
//            "segmentEdited",
//            response.body.jsonPath().getJsonObject<JSONObject>("segment.segment_name")
//        )
//    }

}


