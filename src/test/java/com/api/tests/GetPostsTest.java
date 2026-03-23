package com.api.tests;

import com.api.config.ApiConfig;
import io.qameta.allure.*;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

@Epic("Posts API")
@Feature("GET endpoints")
public class GetPostsTest {

    @Test
    @Story("Fetch all posts")
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("GET /posts returns 100 posts with status 200")
    void getAllPosts_returns200AndCorrectCount() {
        Response response = given()
                .spec(ApiConfig.getRequestSpec())
            .when()
                .get("/posts")
            .then()
                .statusCode(200)        // assert status inline
                .extract().response();  // extract for further assertions

        // Assert response body content
        int postCount = response.jsonPath().getList("$").size();
        assertEquals(100, postCount, "Should return exactly 100 posts");
    }

    @Test
    @Story("Fetch single post")
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("GET /posts/1 returns correct post data")
    void getPostById_returnsCorrectPost() {
        Response response = given()
                .spec(ApiConfig.getRequestSpec())
            .when()
                .get("/posts/1")
            .then()
                .statusCode(200)
                .extract().response();

        // Assert specific fields
        assertEquals(1, (int) response.jsonPath().get("id"));
        assertEquals(1, (int) response.jsonPath().get("userId"));
        assertNotNull(response.jsonPath().getString("title"));
        assertNotNull(response.jsonPath().getString("body"));
    }

    @Test
    @Story("Fetch single post")
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("GET /posts/999 returns 404 for non-existent post")
    void getPostById_nonExistent_returns404() {
        given()
            .spec(ApiConfig.getRequestSpec())
        .when()
            .get("/posts/999")
        .then()
            .statusCode(404);
    }

    @Test
    @Story("Fetch posts by user")
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("GET /posts?userId=1 returns only posts for that user")
    void getPostsByUserId_returnsFilteredPosts() {
        Response response = given()
                .spec(ApiConfig.getRequestSpec())
                .queryParam("userId", 1)    // query parameter
            .when()
                .get("/posts")
            .then()
                .statusCode(200)
                .extract().response();

        // Every post in the response should belong to userId 1
        response.jsonPath().getList("userId", Integer.class)
                .forEach(userId -> assertEquals(1, userId,
                        "All posts should belong to userId 1"));
    }
}