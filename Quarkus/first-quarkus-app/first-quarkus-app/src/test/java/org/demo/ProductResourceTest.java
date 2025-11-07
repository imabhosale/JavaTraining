package org.demo;
import io.quarkus.test.junit.QuarkusTest;

import io.restassured.http.ContentType;
import org.demo.model.Product;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;

@QuarkusTest
public class ProductResourceTest {

    private static String accessToken;

    @BeforeAll
    static void loginAndGenerateAccessToken() {
        Map<String, String> loginPayload = new HashMap<>();
        loginPayload.put("username", "abhishek7517");
        loginPayload.put("password", "testpassword");

        accessToken=
        given()
                .contentType(ContentType.JSON)
                .body(loginPayload)
                .when()
                .post("/users/login")
                .then()
                .statusCode(200)
                .body("accessToken",notNullValue())
                .extract()
                .path("accessToken");
        System.out.println("accessToken for Test:"+accessToken);

    }

    @Test
    public void testCreateProduct()
    {
        Product product = new Product("Laptop","this is test product",new BigDecimal("70000"),10);

        given()
                .header("Authorization", "Bearer " + accessToken)
                .contentType(ContentType.JSON)
                .body(product)
                .when()
                .post("/products")
                .then()
                .statusCode(201)
                .body("description", is("this is test product"))
                .body("price", is(70000));
    }


    @Test
     void testFindAllProducts()
    {
        given()
                .header("Authorization","Bearer "+accessToken)
                .when()
                .get("/products")
                .then()
                .statusCode(200);
    }

    @Test
    void testFindProductById()
    {
        Product product=new Product("Mobile","this is test product:Mobile",new BigDecimal("30000"),05);

        Long id=((Number)
        given()
                .header("Authorization", "Bearer "+accessToken)
                .contentType(ContentType.JSON)
                .body(product)
                .when()
                .post("/products")
                .then()
                .statusCode(201)
                .extract()
                .path("id")).longValue();

        given()
              .header("Authorization", "Bearer "+accessToken)
                .contentType(ContentType.JSON)
                .when()
                .body(product)
                .put("/products/"+id)
                .then()
                .statusCode(200)
                .body("name", is("Mobile"));


    }



}
