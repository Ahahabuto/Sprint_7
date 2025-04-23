import api.CourierApi;
import api.LoginData;
import api.RegData;
import api.Specifications;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


import static org.hamcrest.CoreMatchers.equalTo;

public class CourierCreationTest {
    private RegData courier;
    private String courierId;
    private boolean skipTearDown;

    @Before
    public void setUp() {
        this.courier = new RegData("MugiwaraNo", "1234", "Luffy");
        this.skipTearDown = false;
    }

    @Test
    public void successRegistrationTest() {
        Specifications.installSpecification(Specifications.requestSpec(), Specifications.responseSpec201Created());

        CourierApi.createCourier(courier)
                .then()
                .log().all()
                .body("ok", equalTo(true));
    }

    @Test
    public void duplicateCourierCreationTest() {
        Specifications.installSpecification(Specifications.requestSpec(), Specifications.responseSpec201Created()); // сначала создаём курьера

        CourierApi.createCourier(courier)
                .then()
                .log().all(); // создание

        Specifications.installSpecification(Specifications.requestSpec(), Specifications.responseSpec409Conflict()); // пытаемся создать курьера снова
        CourierApi.createCourier(courier)
                .then()
                .log().all()
                .body("message", equalTo("Этот логин уже используется"));// test failed Expected: Этот логин уже используется Actual: Этот логин уже используется. Попробуйте другой.
    }

    @Test
    public void unsuccessfulRegWithoutLoginTest(){
        skipTearDown = true;
        Specifications.installSpecification(Specifications.requestSpec(), Specifications.responseSpec400BadReq());

        CourierApi.createCourier(new RegData("", "1234", "Luffy"))
                .then()
                .log().all()
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    public void unsuccessfulRegWithoutPasswordTest(){
        skipTearDown = true;
        Specifications.installSpecification(Specifications.requestSpec(), Specifications.responseSpec400BadReq());

        CourierApi.createCourier(new RegData("MugiwaraNo", "", "Luffy"))
                .then()
                .log().all()
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    //имя не является обязательным

    @After
    public void tearDown() {
        if (!skipTearDown) {
            loginAndGetCourierId();
            deleteCourier();
        }
    }

    @Step("Логин и получение ID курьера")
    private void loginAndGetCourierId() {

        Specifications.installSpecification(Specifications.requestSpec(), Specifications.responseSpec200Ok());

        Response response = CourierApi.loginCourier(new LoginData(courier.getLogin(), courier.getPassword()));
        this.courierId = response.jsonPath().getString("id");
    }
    @Step("Удаление курьера по id")
    private void deleteCourier(){
        if (courierId != null) {
            Specifications.installSpecification(Specifications.requestSpec(), Specifications.responseSpec200Ok());
            CourierApi.deleteCourier(courierId);
        }
    }
}
