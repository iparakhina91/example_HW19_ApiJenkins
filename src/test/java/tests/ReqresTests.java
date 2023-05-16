package tests;

import io.qameta.allure.Owner;
import models.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static specs.Specs.*;

public class ReqresTests {

    private static final int USER = 6;


    @Test
    @Tag("api")
    @Owner("korovinaiyu")
    @DisplayName("Создание пользователя POST api/users с валидными name и job")
    void createUserTestWithLombok() {
        CreateUserBody data = new CreateUserBody();
        data.setName("irina");
        data.setJob("qa");

        CreateUserResponse response = step("Создание пользователя", () ->
                given(requestSpec)
                .body(data)
                .when()
                .post("/users")
                .then()
                .spec(createResponse)
                .extract().as(CreateUserResponse.class));

        step("Проверка name созданного пользователя", () ->
                assertThat(response.getName()).isEqualTo("irina"));
        step("Проверка job созданного пользователя", () ->
                assertThat(response.getJob()).isEqualTo("qa"));
    }

    @Test
    @DisplayName("Изменение имени пользователя PUT api/users/:id с существующим id")
    void updateUserTestWithLombok() {
        UpdateUserBody updateData = new UpdateUserBody();
        updateData.setName("ira");
        updateData.setJob("qa");

        UpdateUserResponse response = step("Изменение имени пользователя c id " + USER, () ->
                given(requestSpec)
                        .body(updateData)
                        .when()
                        .put("/users/" + USER)
                        .then()
                        .spec(responseSuccess)
                        .extract().as(UpdateUserResponse.class));

        step("Проверка, что изменилось имя пользователя с id " + USER, () ->
                assertThat(response.getName()).isEqualTo("ira"));
    }

    @Test
    @DisplayName("Удаление пользователя DELETE api/users/:id с существующим id")
    void deleteUserTestWithLombok() {
        step("Удаление пользователя c id " + USER, () ->
                given()
                        .spec(requestSpec)
                        .when()
                        .delete("/users/"+ USER)
                        .then()
                        .spec(deleteResponse));
    }

    @Test
    @DisplayName("Регистрация пользователя POST api/register без password")
    void registerMissingPasswordTestWithLombok() {
        RegisterUserBody registerData = new RegisterUserBody();
        registerData.setEmail("irina@mail.ru");

        RegisterUserResponse response = step("Регистрация пользователя без password", () ->
        given(requestSpec)
                .body(registerData)
                .when()
                .post("/register")
                .then()
                .spec(responseFail)
                .extract().as(RegisterUserResponse.class));

        step("Проверка, что отображается ошибка с корректным текстом", () ->
                assertThat(response.getError().equals("Missing password")));
    }
}