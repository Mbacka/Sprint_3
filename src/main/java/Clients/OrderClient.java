package Clients;

import Configurations.RestAssuredClient;
import Factory.Order;
import io.qameta.allure.Step;

import java.util.List;

import static io.restassured.RestAssured.given;

public class OrderClient extends RestAssuredClient {
    private static final String ORDER_PATH = "api/v1/orders/";

    @Step("Создаем заказов")
    public int orderCreate(Order order) {
        return given()
                .spec(getBaseSpec())
                .body(order)
                .when()
                .post(ORDER_PATH)
                .then()
                .assertThat()
                .statusCode(201)
                .extract()
                .path("track");
    }

    @Step("Получаем список заказ")
    public List<String> getListOrders() {
        return given()
                .spec(getBaseSpec())
                .body("")
                .when()
                .get(ORDER_PATH)
                .then()
                .assertThat()
                .statusCode(200)
                .extract()
                .path("orders");
    }


}
