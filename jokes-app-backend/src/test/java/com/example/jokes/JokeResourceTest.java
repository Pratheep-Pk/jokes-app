package com.example.jokes;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;

import static org.hamcrest.CoreMatchers.equalTo;

@QuarkusTest
public class JokeResourceTest {

    @Test
    public void testGetJokesValidCount() {
        RestAssured.given()
            .queryParam("count", 10)
            .when().get("/jokes")
            .then()
            .statusCode(200)
            .contentType(ContentType.JSON)
            .body("size()", equalTo(10));
    }

    @Test
    public void testGetJokesInvalidCount() {
        RestAssured.given()
            .queryParam("count", 0)
            .when().get("/jokes")
            .then()
            .statusCode(400)
            .contentType(ContentType.JSON)
            .body("message", equalTo("Request number of jokes is out of limits"));
    }
}