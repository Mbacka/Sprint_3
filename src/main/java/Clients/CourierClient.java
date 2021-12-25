package Clients;

import Configurations.RestAssuredClient;
import Factory.Courier;
import Factory.CourierCredentials;
import io.qameta.allure.Step;

import static io.restassured.RestAssured.given;

public class CourierClient extends RestAssuredClient {

    private static final String COURIER_PATH = "api/v1/courier/";

    @Step("Создаем курьера")
    public boolean createSuccess(Courier courier){
        return given()
                .spec(getBaseSpec())
                .body(courier)
                .when()
                .post(COURIER_PATH)
                .then()
                .assertThat()
                .statusCode(201)
                .extract()
                .path("ok");
    }

    @Step("Неудачное создание курьера")
    public String createConflict(Courier courier){
        return given()
                .spec(getBaseSpec())
                .body(courier)
                .when()
                .post(COURIER_PATH)
                .then()
                .assertThat()
                .statusCode(409)
                .extract()
                .path("message");
    }

    @Step("Некорректный запрос на создание курьера")
    public String createBadRequest(Courier courier){
        return given()
                .spec(getBaseSpec())
                .body(courier)
                .when()
                .post(COURIER_PATH)
                .then()
                .assertThat()
                .statusCode(400)
                .extract()
                .path("message");
    }

    @Step("Авторизуемся и получаем ID")
    public int login(CourierCredentials courierCredentials){
        return given()
                .spec(getBaseSpec())
                .body(courierCredentials)
                .when()
                .post(COURIER_PATH + "login")
                .then()
                .assertThat()
                .statusCode(200)
                .extract()
                .path("id");
    }

    @Step("Авторизация несуществующим курьером")
    public String loginFailed(CourierCredentials courierCredentials){
        return given()
                .spec(getBaseSpec())
                .body(courierCredentials)
                .when()
                .post(COURIER_PATH + "login")
                .then()
                .assertThat()
                .statusCode(404)
                .extract()
                .path("message");
    }

    @Step("Авторизация пустым логином или паролем")
    public String loginBadRequest(CourierCredentials courierCredentials){
        return given()
                .spec(getBaseSpec())
                .body(courierCredentials)
                .when()
                .post(COURIER_PATH + "login")
                .then()
                .assertThat()
                .statusCode(400)
                .extract()
                .path("message");
    }

    @Step("Удаляем курьера")
    public boolean delete(int courierId){
        return given()
                .spec(getBaseSpec())
                .when()
                .delete(COURIER_PATH + courierId)
                .then()
                .assertThat()
                .statusCode(200)
                .extract()
                .path("ok");
    }
}
