package ru.yandex.scooter;

import Factory.Courier;
import Clients.CourierClient;
import Factory.CourierCredentials;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertTrue;

public class CourierCreateTest {
    public CourierClient courierClient;
    private int courierId;

    @Before
    public void setup() {
        courierClient = new CourierClient();
    }

    @After
    public void tearDown() {
        courierClient.delete(courierId);
    }


    @Test
    @DisplayName("Успешное создание курьера")
    public void testCourierIsCreated() {
        Courier courier = Courier.getRandom();

        boolean isCreated = courierClient.createSuccess(courier);
        courierId = courierClient.login(CourierCredentials.getCourierCredentials(courier));

        assertTrue("Неудалось создать курьера", isCreated);
        assertThat("Некорректный ID курьера", courierId, is(not(0)));
    }

    @Test
    @DisplayName("Попытка создать одинаковых курьеров")
    public void testCourierDoubleCreate() {
        Courier courier = Courier.getRandom();

        Boolean isCreated = courierClient.createSuccess(courier);
        String message = courierClient.createConflict(courier);

        courierId = courierClient.login(CourierCredentials.getCourierCredentials(courier));

        assertTrue("Неудалось создать курьера", isCreated);
        assertEquals("Этот логин уже используется. Попробуйте другой.", message);
        assertThat("Некорректный ID курьера", courierId, is(not(0)));
    }

    @Test
    @DisplayName("Попытка создать курьера когда одно из полей пустое")
    public void testCourierCreateWithBadRequest() {
        Courier courierWithoutLogin = new Courier(
                "",
                Courier.getRandom().getPassword(),
                Courier.getRandom().getFirstName());

        String message = courierClient.createBadRequest(courierWithoutLogin);

        assertEquals("Недостаточно данных для создания учетной записи", message);

        Courier courierWithPassword = new Courier(
                Courier.getRandom().getLogin(),
                "",
                Courier.getRandom().getFirstName());

        message = courierClient.createBadRequest(courierWithPassword);

        assertEquals("Недостаточно данных для создания учетной записи", message);

        Courier courierWithName = new Courier(
                Courier.getRandom().getLogin(),
                Courier.getRandom().getPassword(),
                "");

        boolean isCreated = courierClient.createSuccess(courierWithName);
        courierId = courierClient.login(CourierCredentials.getCourierCredentials(courierWithName));

        assertTrue("Неудалось создать курьера", isCreated);
        assertThat("Некорректный ID курьера", courierId, is(not(0)));
    }
}
