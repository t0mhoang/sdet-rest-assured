package com.api.tests;

import com.api.config.ApiConfig;
import io.qameta.allure.*;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

@Epic("Posts API")
@Feature("POST endpoints")
public class CreatePostTest {

    // Request body as a simple string — we'll use POJOs later
    private final String validPostBody = """
            {
                "title": "SDET Practice Post",
                "body": "This is a test post created by REST Assured",
                "userId": 1
            }
            """;

    @Test
    @Story("Create post")
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("POST /posts with valid body returns 201 and echoes data")
    void createPost_validBody_returns201() {
        Response response = given()
                .spec(ApiConfig.getRequestSpec())
                .body(validPostBody)
            .when()
                .post("/posts")
            .then()
                .statusCode(201)        // 201 Created — not 200
                .extract().response();

        // API should echo back what we sent plus assign an id
        assertEquals("SDET Practice Post", response.jsonPath().getString("title"));
        assertEquals(1, (int) response.jsonPath().get("userId"));
        assertNotNull(response.jsonPath().get("id"), "Response should include a generated id");
    }

    @Test
    @Story("Create post")
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("POST /posts response time is under 3 seconds")
    void createPost_responseTimeUnder3Seconds() {
        given()
            .spec(ApiConfig.getRequestSpec())
            .body(validPostBody)
        .when()
            .post("/posts")
        .then()
            .statusCode(201)
            .time(org.hamcrest.Matchers.lessThan(3000L));  // performance check
    }
}