package ru.yandex.scooter;

import Factory.Courier;
import Clients.CourierClient;
import Factory.CourierCredentials;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertTrue;

public class CourierLoginTest {


    public CourierClient courierClient;
    private int courierId;

    @Before
    public void setup() {
        courierClient = new CourierClient();
    }

    @Test
    @DisplayName("Успешная авторизация курьера")
    public void testCourierLogin() {
        Courier courier = Courier.getRandom();

        boolean isCreated = courierClient.createSuccess(courier);
        courierId = courierClient.login(CourierCredentials.getCourierCredentials(courier));

        assertTrue("Неудалось создать курьера", isCreated);
        assertThat("Некорректный ID курьера", courierId, is(not(0)));

        courierClient.delete(courierId);

        assertThat("Неудалось получить ID курьера", courierId, is(not(0)));
    }


    @Test
    @DisplayName("Проверка авторизации несуществующим курьером")
    public void testCourierNonExistentIsLogin() {
        Courier courier = Courier.getRandom();

        String message = courierClient.loginFailed(CourierCredentials.getCourierCredentials(courier));

        assertEquals("Учетная запись не найдена", message);
    }

    @Test
    @DisplayName("Проверка авторизации c пустым логином или паролем")
    public void testCourierUncorrectLoginOrPassword() {
        Courier courier = Courier.getRandom();

        courierClient.createSuccess(courier);
        String message = courierClient.loginBadRequest(new CourierCredentials(courier.getLogin(), ""));

        assertEquals("Недостаточно данных для входа", message);

        message = courierClient.loginBadRequest(new CourierCredentials("", courier.getPassword()));

        assertEquals("Недостаточно данных для входа", message);

        courierId = courierClient.login(CourierCredentials.getCourierCredentials(courier));
        courierClient.delete(courierId);

        assertThat("Неудалось получить ID курьера", courierId, is(not(0)));

    }

    // Тест упадет т.к находит баг ожидаем 400 код
    @Test
    @DisplayName("Проверка авторизации только с полем логин")
    public void testCourierOnlyFieldLogin() {
        Courier courier = Courier.getRandom();
        courierClient.createSuccess(courier);

        String message = courierClient.loginBadRequest(new CourierCredentials(courier.getLogin(), null));

        assertEquals("Недостаточно данных для входа", message);

        courierId = courierClient.login(CourierCredentials.getCourierCredentials(courier));
        courierClient.delete(courierId);
    }

    @Test
    @DisplayName("Проверка авторизации только с полем пароль")
    public void testCourierOnlyFieldPassword() {
        Courier courier = Courier.getRandom();
        courierClient.createSuccess(courier);

        String message = courierClient.loginBadRequest(new CourierCredentials(null, courier.getPassword()));

        assertEquals("Недостаточно данных для входа", message);

        courierId = courierClient.login(CourierCredentials.getCourierCredentials(courier));
        courierClient.delete(courierId);
    }


}
