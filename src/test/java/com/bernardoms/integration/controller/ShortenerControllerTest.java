package com.bernardoms.integration.controller;


import com.bernardoms.dto.URLShortenerDTO;
import com.bernardoms.integration.IntegrationTest;
import com.bernardoms.integration.MongoEmbedded;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;

import static io.restassured.RestAssured.given;

@QuarkusTest
@QuarkusTestResource(MongoEmbedded.class)
public class ShortenerControllerTest extends IntegrationTest {

    @Test
    public void test_create_alias() {

        var urlShortenerDTO = URLShortenerDTO.builder().originalURL("http://www.test.com").build();

        given().body(urlShortenerDTO)
                .contentType(ContentType.JSON)
                .post("/shorteners")
                .then()
                .statusCode(Response.Status.CREATED.getStatusCode())
                .header("Location", CoreMatchers.notNullValue());
    }

    @Test
    public void test_create_invalidURL_alias()  {

        var urlShortenerDTO = URLShortenerDTO.builder().originalURL("test.com").build();

        given().body(urlShortenerDTO)
                .contentType(ContentType.JSON)
                .post("/shorteners").then()
                .statusCode(Response.Status.BAD_REQUEST.getStatusCode());
    }

    @Test
    public void test_find_all_shorteners_url() {
        given().when().get("/shorteners").then().statusCode(Response.Status.OK.getStatusCode());
    }

    @Test
    public void test_redirect() {
        given().pathParam("alias", "abdefg").redirects().follow(false).when().get("/shorteners/{alias}").then().statusCode(Response.Status.FOUND.getStatusCode());
    }

    @Test
    public void test_redirect_not_found_alias() {
        given().pathParam("alias", "abdef").redirects().follow(false).when().get("/shorteners/{alias}").then().statusCode(Response.Status.NOT_FOUND.getStatusCode());
    }
}
