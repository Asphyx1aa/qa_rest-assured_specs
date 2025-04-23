package tests;

import models.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static specs.BaseSpecs.*;
import static specs.DeleteUserSpec.deleteUserRequestSpec;
import static specs.SearchUserSpec.searchUserRequestSpec;
import static specs.UpdateUserSpec.updateUserRequestSpec;

public class ReqresTests extends TestBase {


    @Test
    @DisplayName("Создание пользователя")
    void createUserTest() {
        UserBodyRequest userData = new UserBodyRequest("morpheus", "leader");

        UserCreateResponse response = step("Отправляем запрос на создание пользователя", () ->
                given()
                        .spec(baseSpec)
                        .body(userData)
                        .when()
                        .post("/users")
                        .then()
                        .spec(responseSpec(201))
                        .extract().as(UserCreateResponse.class));

        step("Проверяем, что пользователь создан", () ->
                assertThat(response.getCreatedAt(), notNullValue())
        );
    }

    @Test
    @DisplayName("Успешная авторизация")
    void successfulLoginTest() {
        LoginRequest authData = new LoginRequest("eve.holt@reqres.in", "cityslicka");

        LoginResponse response = step("Отправляем запрос на авторизацию", () ->
                given()
                        .spec(baseSpec)
                        .body(authData)
                        .when()
                        .post("/login")
                        .then()
                        .spec(responseSpec(200))
                        .extract().as(LoginResponse.class));

        step("Проверям, что пришел токен", () ->
                assertThat(response.getToken(), notNullValue())
        );
    }

    @Test
    @DisplayName("Неуспешная авторизация")
    void unsuccessfulLoginTest() {
        LoginRequest authData = new LoginRequest("peter@klaven", "");

        LoginUnsuccessfulResponse response = step("Отправляем запрос на авторизацию", () ->
                given()
                        .spec(baseSpec)
                        .body(authData)
                        .when()
                        .post("/login")
                        .then()
                        .spec(responseSpec(400))
                        .extract().as(LoginUnsuccessfulResponse.class));

        step("Проверям, пришла ошибка авторизации", () ->
                assertThat(response.getError(), notNullValue())
        );
    }

    @Test
    @DisplayName("Неуспешная регистрация")
    void unsuccessfulRegisterTest() {
        RegisterBodyRequest registrationData = new RegisterBodyRequest("sydney@fife", "");


        RegisterUnsuccessfulResponse response = step("Отправляем запрос на регистрацию", () ->
                given()
                        .spec(baseSpec)
                        .body(registrationData)
                        .when()
                        .post("/register")
                        .then()
                        .spec(responseSpec(400))
                        .extract().as(RegisterUnsuccessfulResponse.class));

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
                        .delete("/{userId}")
                        .then()
                        .spec(responseSpec(204)));
    }


    @Test
    @DisplayName("Поиск пользователя")
    void searchUserTest() {

        UserResponse response = step("Отправляем запрос на поиск пользователя", () ->
                given()
                        .spec(searchUserRequestSpec)
                        .when()
                        .get("/unknown/{userId}")
                        .then()
                        .spec(responseSpec(200))
                        .extract().as(UserResponse.class)
        );

        step("Проверяем, что получили юзера", () ->
                assertThat(response.getData(), notNullValue())
        );
    }

    @Test
    @DisplayName("Обновление данных пользователя")
    void updateUserTest() {
        UserBodyRequest userData = new UserBodyRequest("morpheus", "zion resident");

        UpdatedUserRequest response = step("Отправляем запрос на обновление пользователя", () ->
                given()
                        .spec(updateUserRequestSpec)
                        .body(userData)
                        .when()
                        .put("/users/{userId}")
                        .then()
                        .spec(responseSpec(200))
                        .extract().as(UpdatedUserRequest.class)
        );

        step("Проверяем, что пришло время обновления юзера", () ->
                assertThat(response.getUpdatedAt(), notNullValue())
        );
    }

    @Test
    @DisplayName("Обновление данных пользователя")
    void updatePatchUserTest() {
        UserBodyRequest userData = new UserBodyRequest("morpheus", "zion resident");

        UpdatedUserRequest response = step("Отправляем запрос на обновление пользователя", () ->
                given()
                        .spec(updateUserRequestSpec)
                        .body(userData)
                        .when()
                        .patch("/users/{userId}")
                        .then()
                        .spec(responseSpec(200))
                        .extract().as(UpdatedUserRequest.class)
                );

        step("Проверяем, что пришло время обновления юзера", () ->
                assertThat(response.getUpdatedAt(), notNullValue())
        );
        assertThat(response.getUpdatedAt(), notNullValue());
    }

}
