package org.acme.hibernate.orm.panache;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.text.IsEmptyString.emptyString;

@QuarkusTest
public class ProductsEndpointTest {

    @Test
    public void testProductsEndpoint() {
    	
        //Create Product
        given()
                .when()
                .body("{\"name\" : \"Created Product\"}")
                .contentType("application/json")
                .post("/products")
                .then()
                .statusCode(201)
                .body(
                        containsString("\"id\":"),
                        containsString("\"name\":\"Created Product\""));

        //List all to check new created product
        Response response = given()
                .when()
                .get("/products")
                .then()
                .statusCode(200)
                .extract().response();
        
        assertThat(response.jsonPath().getList("name"))
                .contains("Created Product");
    	
        //List all, should have all products
         response = given()
                .when()
                .get("/products")
                .then()
                .statusCode(200)
                .contentType("application/json")
                .extract().response();
        
        assertThat(response.jsonPath().getList("name")).isNotEmpty();

        // Update Product
        given()
                .when()
                .body("{\"name\" : \"updated product name\"}")
                .contentType("application/json")
                .put("/products/1")
                .then()
                .statusCode(200)
                .body(
                        containsString("\"id\":"),
                        containsString("\"name\":"));

        //List all, Pineapple should've replaced Cherry:
        response = given()
                .when()
                .get("/products")
                .then()
                .statusCode(200)
                .contentType("application/json")
                .extract().response();
        
        assertThat(response.jsonPath().getList("name"))
                .contains("updated product name");

        //Delete Product:
        given()
                .when()
                .delete("/products/1")
                .then()
                .statusCode(204);

        response = given()
                .when()
                .get("/products")
                .then()
                .statusCode(200)
                .contentType("application/json")
                .extract().response();
        
        assertThat(response.jsonPath().getList("id"))
                .isEmpty();


    }

}
