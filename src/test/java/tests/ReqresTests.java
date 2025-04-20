package tests;

import models.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static specs.CreateUserSpec.createUserRequestSpec;
import static specs.CreateUserSpec.createUserResponseSpec;
import static specs.DeleteUserSpec.deleteUserRequestSpec;
import static specs.DeleteUserSpec.deleteUserResponseSpec;
import static specs.LoginSpec.*;
import static specs.RegisterSpec.registerRequestSpec;
import static specs.RegisterSpec.registerResponseSpec;
import static specs.SearchUserSpec.searchUserRequestSpec;
import static specs.SearchUserSpec.searchUserResponseSpec;
import static specs.UpdateUserSpec.updateUserRequestSpec;
import static specs.UpdateUserSpec.updateUserResponseSpec;

public class ReqresTests extends TestBase {


    @Test
    @DisplayName("Создание пользователя")
    void createUserTest() {
        UserBodyModel userData = new UserBodyModel();
        userData.setName("morpheus");
        userData.setJob("leader");

        UserCreateModel response = step("Отправляем запрос на создание пользователя", () ->
                given()
                        .spec(createUserRequestSpec)
                        .body(userData)
                        .when()
                        .post()
                        .then()
                        .spec(createUserResponseSpec)
                        .extract().as(UserCreateModel.class));

        step("Проверяем, что пользователь создан", () ->
                assertThat(response.getCreatedAt(), notNullValue())
        );
    }

    @Test
    @DisplayName("Успешная авторизация")
    void successfulLoginTest() {
        LoginBodyModel authData = new LoginBodyModel();
        authData.setEmail("eve.holt@reqres.in");
        authData.setPassword("cityslicka");

        LoginResponseModel response = step("Отправляем запрос на авторизацию", () ->
                given()
                        .spec(successfulLoginRequestSpec)
                        .body(authData)
                        .when()
                        .post()
                        .then()
                        .spec(successfulLoginResponseSpec)
                        .extract().as(LoginResponseModel.class));

        step("Проверям, что пришел токен", () ->
                assertThat(response.getToken(), notNullValue())
        );
    }

    @Test
    @DisplayName("Неуспешная авторизация")
    void unsuccessfulLoginTest() {
        LoginBodyModel authData = new LoginBodyModel();
        authData.setEmail("peter@klaven");

        LoginUnsuccessfulModel response = step("Отправляем запрос на авторизацию", () ->
                given()
                        .spec(successfulLoginRequestSpec)
                        .body(authData)
                        .when()
                        .post()
                        .then()
                        .spec(unsuccessfulLoginResponseSpec)
                        .extract().as(LoginUnsuccessfulModel.class));

        step("Проверям, пришла ошибка авторизации", () ->
                assertThat(response.getError(), notNullValue())
        );
    }

    @Test
    @DisplayName("Неуспешная регистрация")
    void unsuccessfulRegisterTest() {
        RegisterBodyModel registrationData = new RegisterBodyModel();
        registrationData.setEmail("sydney@fife");


        RegisterUnsuccessfulModel response = step("Отправляем запрос на регистрацию", () ->
                given()
                        .spec(registerRequestSpec)
                        .body(registrationData)
                        .when()
                        .post()
                        .then()
                        .spec(registerResponseSpec)
                        .extract().as(RegisterUnsuccessfulModel.class));

        step("Проверяем, что пришла ошибка при регистрации", () ->
                assertThat(response.getError(), notNullValue())
        );

    }

    @Test
    @DisplayName("Удаление пользователя")
    void deleteUserTest() {

        step("Отправляем запрос на удаление пользователя", () ->
                given()
                        .spec(deleteUserRequestSpec)
                        .when()
                        .delete("/api/{userId}")
                        .then()
                        .spec(deleteUserResponseSpec));
    }


    @Test
    @DisplayName("Поиск пользователя")
    void searchUserTest() {

        UserResponseModel response = step("Отправляем запрос на поиск пользователя", () ->
                given()
                        .spec(searchUserRequestSpec)
                        .when()
                        .get("/api/unknown/{userId}")
                        .then()
                        .spec(searchUserResponseSpec)
                        .extract().as(UserResponseModel.class)
        );

        step("Проверяем, что получили юзера", () ->
                assertThat(response.getData(), notNullValue())
        );
    }

    @Test
    @DisplayName("Обновление данных пользователя")
    void updateUserTest() {
        UserBodyModel userData = new UserBodyModel();
        userData.setName("morpheus");
        userData.setJob("zion resident");

        UpdatedUserModel response = step("Отправляем запрос на обновление пользователя", () ->
                given()
                        .spec(updateUserRequestSpec)
                        .body(userData)
                        .when()
                        .put("/api/users/{userId}")
                        .then()
                        .spec(updateUserResponseSpec)
                        .extract().as(UpdatedUserModel.class)
        );

        step("Проверяем, что пришло время обновления юзера", () ->
                assertThat(response.getUpdatedAt(), notNullValue())
        );
    }

    @Test
    @DisplayName("Обновление данных пользователя")
    void updatePatchUserTest() {
        UserBodyModel userData = new UserBodyModel();
        userData.setName("morpheus");
        userData.setJob("zion resident");

        UpdatedUserModel response = step("Отправляем запрос на обновление пользователя", () ->
                given()
                        .spec(updateUserRequestSpec)
                        .body(userData)
                        .when()
                        .patch("/api/users/{userId}")
                        .then()
                        .spec(updateUserResponseSpec)
                        .extract().as(UpdatedUserModel.class)
                );

        step("Проверяем, что пришло время обновления юзера", () ->
                assertThat(response.getUpdatedAt(), notNullValue())
        );
        assertThat(response.getUpdatedAt(), notNullValue());
    }

}
