package api;

import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class CourierApi {

    @Step ("Создание куры")
    public static Response createCourier(RegData courier) {
        return given()
                .spec(Specifications.requestSpec())
                .body(courier)
                .when()
                .post("/api/v1/courier");
    }

    @Step ("Логин курьера")
    public static Response loginCourier(LoginData loginData) {
        return given()
                .spec(Specifications.requestSpec())
                .body(loginData)
                .when()
                .post("/api/v1/courier/login");
    }

    @Step("Удаление курьера")
    public static Response deleteCourier(String courierId) {
        return given()
                .spec(Specifications.requestSpec())
                .pathParam("id", courierId)
                .when()
                .delete("/api/v1/courier/{id}");
    }
}
