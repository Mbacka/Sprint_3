package ru.yandex.scooter;

import Clients.OrderClient;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;

public class ListOrdersTest {


    @Test
    @DisplayName("Проверка получения списка заказов")
    public void testGetListOrders() {
        OrderClient orderClient = new OrderClient();

        List<String> orderList = orderClient.getListOrders();

        assertThat("Неудалось получить список заказов", orderList, notNullValue());
    }
}
