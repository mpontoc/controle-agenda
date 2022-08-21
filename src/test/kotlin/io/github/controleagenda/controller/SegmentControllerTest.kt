package io.github.controleagenda.controller

import io.github.controleagenda.commons.Utils
import io.github.controleagenda.services.SegmentService
import io.restassured.RestAssured
import io.restassured.http.ContentType
import io.restassured.response.Response
import net.minidev.json.JSONValue
import org.json.JSONObject
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.Mock
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class SegmentControllerTest {

    @Mock
    lateinit var util: Utils

    @Autowired
    lateinit var segmentService: SegmentService

    @Test
    fun testGetAllSegmentsByController() {

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
            util.listSegmentsDefault()[3].segmentName,
            response.body.jsonPath().getJsonObject<JSONObject>("segment[3].segment_name")
        )
    }

    @Test
    fun testGetSegmentsByIdController() {
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
            util.listSegmentsDefault()[1].segmentName,
            response.body.jsonPath().getJsonObject<JSONObject>("segment.segment_name")
        )
    }

    @Test
    fun testGetSegmentsByIdControllerNotFound() {

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

        val body = mapOf(
            "id" to "98",
            "segment_name" to "test-rest-assured"
        )
        val response: Response =
            RestAssured
                .given()
                .contentType("application/json")
                .`when`()
                .body(body)
                .post("/segmentos")
                .then()
                .statusCode(201)
                .log()
                .all().extract().response()

        Assertions.assertEquals(
            "test-rest-assured",
            response.body.jsonPath().getJsonObject<JSONObject>("segment.segment_name")
        )

        var idToRemove: String = JSONValue.toJSONString(response.body.jsonPath().getJsonObject<JSONObject>("segment.id"))
        segmentService.deleteSegment(idToRemove.toLong())
    }

    @Test
    fun deleteSegmentByController() {
        util.createSegment(segmentService, 98, "testUnitario")

        RestAssured
            .given()
            .contentType("application/json")
            .`when`()
            .delete("/segmentos/98")
            .then()
            .statusCode(204)
            .log()
            .all().extract().response()
    }

    @Test
    fun editSegmentByController() {
        util.createSegment(segmentService, 110, "segmentToEdit")

        RestAssured
            .given()
            .contentType("application/json")
            .`when`()
            .body(
                "{\"segment_name\": \"segmentEdited\"}"
            )
            .put("/segmentos/110")
            .then()
            .statusCode(200)
            .log()
            .all().extract().response()

        segmentService.deleteSegment(110)
    }

}