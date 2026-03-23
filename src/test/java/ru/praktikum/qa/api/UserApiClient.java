package ru.praktikum.qa.api;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import ru.praktikum.qa.config.TestConfig;
import ru.praktikum.qa.data.TestUser;

import java.util.HashMap;
import java.util.Map;

public class UserApiClient {
    public UserApiClient() {
        RestAssured.baseURI = TestConfig.API_URL;
    }

    public TestUser register(TestUser user) {
        Map<String, Object> body = new HashMap<>();
        body.put("email", user.getEmail());
        body.put("password", user.getPassword());
        body.put("name", user.getName());

        Response response = RestAssured
                .given()
                .filter(new AllureRestAssured())
                .contentType(ContentType.JSON)
                .body(body)
                .post("/signup")
                .then()
                .statusCode(201)
                .extract()
                .response();

        JsonPath json = response.jsonPath();
        user.setId(json.getInt("user.id"));
        user.setToken(resolveToken(json));
        return user;
    }

    public TestUser login(TestUser user) {
        Map<String, Object> body = new HashMap<>();
        body.put("email", user.getEmail());
        body.put("password", user.getPassword());

        Response response = RestAssured
                .given()
                .filter(new AllureRestAssured())
                .contentType(ContentType.JSON)
                .body(body)
                .post("/signin")
                .then()
                .statusCode(201)
                .extract()
                .response();

        JsonPath json = response.jsonPath();
        user.setId(json.getInt("user.id"));
        user.setToken(resolveToken(json));
        return user;
    }

    private String resolveToken(JsonPath json) {
        String token = json.getString("token.access_token");
        if (token == null) {
            token = json.getString("access_token.access_token");
        }
        if (token == null) {
            token = json.getString("access_token");
        }
        return token;
    }
}
