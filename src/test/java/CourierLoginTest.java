import api.CourierApi;
import api.LoginData;
import api.RegData;
import api.Specifications;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

public class CourierLoginTest {
    private LoginData courierLogin;
    private String courierId;

    @Before
    public void setUp() {
        this.courierLogin = new LoginData("Strawhat", "1234");
        Specifications.installSpecification(Specifications.requestSpec(), Specifications.responseSpec201Created());

        CourierApi.createCourier(new RegData(courierLogin.getLogin(), courierLogin.getPassword(), "Luffy"));
    }

    @Test
    public void successfulCourierLoginTest(){
        Specifications.installSpecification(Specifications.requestSpec(), Specifications.responseSpec200Ok());

        CourierApi.loginCourier(courierLogin)
                .then()
                .log().all()
                .body("id", notNullValue());
    }

    @Test
    public void loginWithWrongLoginReturnErrorTest(){
        Specifications.installSpecification(Specifications.requestSpec(), Specifications.responseSpec404NotFound());

        CourierApi.loginCourier(new LoginData("Roronoa", courierLogin.getPassword()))
                .then()
                .log().all()
                .body("message", equalTo("Учетная запись не найдена"));
    }

    @Test
    public void loginWithWrongPasswordReturnErrorTest(){
        Specifications.installSpecification(Specifications.requestSpec(), Specifications.responseSpec404NotFound());

        CourierApi.loginCourier(new LoginData(courierLogin.getLogin(), "876456"))
                .then()
                .log().all()
                .body("message", equalTo("Учетная запись не найдена"));
    }

    @Test
    public void loginWithoutLoginReturnErrorTest(){
        Specifications.installSpecification(Specifications.requestSpec(), Specifications.responseSpec400BadReq());

        CourierApi.loginCourier(new LoginData("", courierLogin.getPassword()))
                .then()
                .log().all()
                .body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    public void loginWithoutPasswordReturnErrorTest(){
        Specifications.installSpecification(Specifications.requestSpec(), Specifications.responseSpec400BadReq());

        CourierApi.loginCourier(new LoginData(courierLogin.getLogin(), ""))
                .then()
                .log().all()
                .body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    public void notExistingCourierLoginTest(){
        Specifications.installSpecification(Specifications.requestSpec(), Specifications.responseSpec404NotFound());

        CourierApi.loginCourier(new LoginData("Roronoazoro", "7877"))
                .then()
                .log().all()
                .body("message", equalTo("Учетная запись не найдена"));
    }

    @After
    public void tearDown() {
        try {
            RestAssured.reset();

            Response response = CourierApi.loginCourier(courierLogin);
            this.courierId = response.jsonPath().getString("id");

            if (courierId != null) {
                Specifications.installSpecification(Specifications.requestSpec(), Specifications.responseSpec200Ok());
                CourierApi.deleteCourier(courierId);
            }
        } catch (Exception e) {
            System.out.println("Удаление курьера не произошло: " + e.getMessage());
        }
    }
}
