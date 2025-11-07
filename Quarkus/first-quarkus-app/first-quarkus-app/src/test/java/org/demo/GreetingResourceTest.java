package org.demo;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
class GreetingResourceTest {

    @Test
    void testGreetingEndpoint() {
        given()
                .pathParam("name", "Abhishek")
                .when().get("/hello/greeting/{name}")
                .then()
                .statusCode(200)
                .body(is("Hello Abhishek"));
    }

}