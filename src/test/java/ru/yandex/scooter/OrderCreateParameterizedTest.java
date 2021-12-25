package ru.yandex.scooter;

import Factory.Order;
import Clients.OrderClient;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;

@RunWith(Parameterized.class)
public class OrderCreateParameterizedTest {
    private final List<String> color;

    public OrderCreateParameterizedTest(List<String> color) {
        this.color = color;
    }

    @Parameterized.Parameters
    public static Object[][] getColorsData() {
        return new Object[][]{
                {List.of("BLACK")},
                {List.of("GREY")},
                {List.of("BLACK", "GREY")},
                {List.of("")},
                {List.of("", "")},
                {null}
        };
    }

    @Test
    @DisplayName("Проверка успешного создания заказа")
    public void testCreateOrder() {
        Order order = new Order(color);
        OrderClient orderClient = new OrderClient();

        int track_ID = orderClient.orderCreate(order);

        assertThat("Не удалось получить ID заказа", track_ID, notNullValue());
    }
}
