package io.github.controleagenda.controller

import io.github.controleagenda.commons.Utils
import io.github.controleagenda.exception.NotFoundException
import io.github.controleagenda.model.Segment
import io.github.controleagenda.model.SegmentToReturn
import io.github.controleagenda.services.SegmentService
import io.restassured.RestAssured
import io.restassured.http.ContentType
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
class SegmentControllerTest {

    @Mock
    lateinit var util: Utils

    @Mock
    lateinit var segmentService: SegmentService

    @InjectMocks
    @Resource
    lateinit var segmentController: SegmentController

    @Test
    fun testGetAllSegmentsByController() {

        Mockito.`when`(segmentService.getAllSegments()).thenReturn(util.listSegmentsDefault())

        val response: Response =
            RestAssured
                .given()
                .accept(ContentType.JSON)
                .`when`()
                .get("/segmentos")
                .then()
                .statusCode(200)
                .log()
                .all().extract().response()

        Assertions.assertEquals(
            util.listSegmentsDefault()[3].segment.segmentName,
            response.body.jsonPath().getJsonObject<JSONObject>("segment[3].segment_name")
        )
    }

    @Test
    fun testGetSegmentsByIdController() {

        Mockito.`when`(segmentService.getSegmentById(2)).thenReturn(util.listSegmentsDefault()[1])

        val response: Response =
            RestAssured
                .given()
                .accept(ContentType.JSON)
                .`when`()
                .get("/segmentos/2")
                .then()
                .statusCode(200)
                .log()
                .all().extract().response()

        Assertions.assertEquals(
            util.listSegmentsDefault()[1].segment.segmentName,
            response.body.jsonPath().getJsonObject<JSONObject>("segment.segment_name")
        )
    }

    @Test
    fun testGetSegmentsByIdControllerNotFound() {

        Mockito.`when`(segmentService.getSegmentById(99)).thenThrow(NotFoundException::class.java)

        RestAssured
            .given()
            .accept(ContentType.JSON)
            .`when`()
            .get("/segmentos/99")
            .then()
            .statusCode(404)
            .log()
            .all().extract().response()
    }


    @Test
    fun testPostToCreateNewSegmentByController() {

        val segment = Segment(9, "test-rest-assured")

        val segmentToReturn = SegmentToReturn(
            segment,
        )

        Mockito.`when`(segmentService.createSegment(segment)).thenReturn(segmentToReturn)

        val response: Response =
            RestAssured
                .given()
                .contentType("application/json")
                .`when`()
                .body(segment)
                .post("/segmentos")
                .then()
                .statusCode(201)
                .log()
                .all().extract().response()

        Assertions.assertEquals(
            "test-rest-assured",
            response.body.jsonPath().getJsonObject<JSONObject>("segment.segment_name")
        )
    }

    @Test
    fun deleteSegmentByController() {

        Mockito.`when`(segmentService.deleteSegment(1)).thenReturn(
            "O usuario ${util.listSegmentsDefault()[0].segment.segmentName} foi deltado com sucesso"
        )

        val response = RestAssured
            .given()
            .contentType("application/json")
            .`when`()
            .delete("/segmentos/1")
            .then()
            .statusCode(204)
            .log()
            .all().extract().response()

        println(response)
    }

    @Test
    fun editSegmentByController() {

        Mockito.`when`(segmentService.updateSegment(Segment(1, "segmentEdited")))
            .thenReturn(util.segmentToReturn("segmentEdited"))

        val response = RestAssured
            .given()
            .contentType("application/json")
            .`when`()
            .body(
                "{\"segment_name\": \"segmentEdited\"}"
            )
            .put("/segmentos/1")
            .then()
            .statusCode(200)
            .log()
            .all().extract().response()

        Assertions.assertEquals(
            "segmentEdited",
            response.body.jsonPath().getJsonObject<JSONObject>("segment.segment_name")
        )
    }

}


