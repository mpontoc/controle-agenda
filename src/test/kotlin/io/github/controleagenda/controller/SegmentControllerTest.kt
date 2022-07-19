package io.github.controleagenda.controller

import io.github.controleagenda.commons.Utils
import io.restassured.RestAssured
import io.restassured.http.ContentType
import io.restassured.response.Response
import org.json.JSONObject
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.Mock
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
@AutoConfigureMockMvc
class SegmentControllerTest {

    @Mock
    lateinit var util: Utils

    @Test
    fun testGetAllSegmentsByController() {

        var response: Response =
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
            util.listSegmentsDefault()[3].segment,
            response.body.jsonPath().getJsonObject<JSONObject>("segment[3]")
        )
    }

    @Test
    fun testGetSegmentosByIdController() {
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
            util.listSegmentsDefault()[1].segment,
            response.body.jsonPath().getJsonObject<JSONObject>("segment")
        )
    }

    @Test
    fun testGetSegmentosByIdControllerNotFound() {
        val response: Response =
            RestAssured
                .given()
                .accept(ContentType.JSON)
                .`when`()
                .get("/segmentos/99")
                .then()
                .statusCode(200)
                .log()
                .all().extract().response()

        Assertions.assertEquals(
            "null",
            response.body.asString()
        )
    }

}