package api;

import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class OrderApi {

    @Step("Создание заказа")
    public static Response createOrder(OrderCreationData order) {
        return given()
                .spec(Specifications.requestSpec())
                .body(order)
                .when()
                .post("/api/v1/orders");
    }

    @Step("Отмена заказа")
    public static Response cancelOrder(int trackId) {
        return given()
                .spec(Specifications.requestSpec())
                .body(trackId)
                .when()
                .put("/api/v1/orders/cancel");
    }

    @Step("Получить список заказов")
    public static Response getList() {
        return given()
                .spec(Specifications.requestSpec())
                .when()
                .get("/api/v1/orders");
    }
}
