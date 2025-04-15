package tests;

import models.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.*;

public class ReqresTests {

    private final String URL = "https://reqres.in";

    @Test
    @DisplayName("Создание пользователя")
    void createUserTest() {
        UserBodyModel userData = new UserBodyModel();
        userData.setName("morpheus");
        userData.setJob("leader");

        given()
                .log().uri()
                .log().method()
                .log().body()
                .body(userData)
                .contentType(JSON)
                .when()
                .post(URL + "/api/users")
                .then()
                .log().status()
                .log().body()
                .statusCode(201)
                .extract().as(UserResponseModel.class);}

    @Test
    @DisplayName("Успешная авторизация")
    void successfulLoginTest() {
        LoginBodyModel authData = new LoginBodyModel();
        authData.setEmail("eve.holt@reqres.in");
        authData.setPassword("cityslicka");

        given()
                .log().uri()
                .log().method()
                .log().body()
                .body(authData)
                .contentType(JSON)
                .when()
                .post(URL + "/api/login")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .extract().as(LoginResponseModel.class);
    }

    @Test
    @DisplayName("Неуспешная авторизация")
    void unsuccessfulLoginTest() {
        LoginBodyModel authData = new LoginBodyModel();
        authData.setEmail("peter@klaven");

        given()
                .log().uri()
                .log().method()
                .log().body()
                .body(authData)
                .contentType(JSON)
                .when()
                .post(URL + "/api/login")
                .then()
                .log().status()
                .log().body()
                .statusCode(400)
                .body("error", is("Missing password"));
    }

    @Test
    @DisplayName("Неуспешная регистрация")
    void unsuccessfulRegisterTest() {
        RegisterBodyModel registrationData = new RegisterBodyModel();
        registrationData.setEmail("sydney@fife");


        given()
                .log().uri()
                .log().method()
                .log().body()
                .body(registrationData)
                .contentType(JSON)
                .when()
                .post(URL + "/api/register")
                .then()
                .log().status()
                .log().body()
                .statusCode(400)
                .body("error", is("Missing password"));
    }

    @Test
    @DisplayName("Удаление пользователя")
    void deleteUserTest() {

        given()
                .log().uri()
                .log().method()
                .pathParam("userId", 2)
                .when()
                .delete(URL + "/api/{userId}")
                .then()
                .log().status()
                .log().body()
                .statusCode(204);
    }


    @Test
    @DisplayName("Поиск пользователя")
    void searchUserTest() {

        given()
                .log().uri()
                .log().method()
                .pathParam("userId", 2)
                .when()
                .get(URL + "/api/unknown/{userId}")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("data.id", notNullValue());
    }

    @Test
    @DisplayName("Обновление данных пользователя")
    void updateUserTest() {
        UserBodyModel userData = new UserBodyModel();
        userData.setName("morpheus");
        userData.setJob("zion resident");

        given()
                .log().uri()
                .log().method()
                .pathParam("userId", 2)
                .body(userData)
                .contentType(JSON)
                .when()
                .put(URL + "/api/users/{userId}")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("job", is("zion resident"));
    }

    @Test
    @DisplayName("Обновление данных пользователя")
    void updatePatchUserTest() {
        UserBodyModel userData = new UserBodyModel();
        userData.setName("morpheus");
        userData.setJob("zion resident");

        given()
                .log().uri()
                .log().method()
                .pathParam("userId", 2)
                .body(userData)
                .contentType(JSON)
                .when()
                .patch(URL + "/api/users/{userId}")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("updatedAt", notNullValue());
    }

}
