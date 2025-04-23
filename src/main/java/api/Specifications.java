package api;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class Specifications {
    private static final String URL = "https://qa-scooter.praktikum-services.ru";

    public static RequestSpecification requestSpec() {
        return new RequestSpecBuilder()
                .setBaseUri(URL)
                .setContentType(ContentType.JSON)
                .build();
    }

    public static ResponseSpecification responseSpec(int statusCode) {
        return new ResponseSpecBuilder()
                .expectStatusCode(statusCode)
                .build();
    }

    /*public static RequestSpecification requestSpec(String URL) {
        return new RequestSpecBuilder()
                .setBaseUri(URL)
                .setContentType(ContentType.JSON)
                .build();
    }
    */

    public static ResponseSpecification responseSpec201Created(){
        return responseSpec(201);
    }

    public static ResponseSpecification responseSpec400BadReq(){
        return responseSpec(400);
    }

    public static ResponseSpecification responseSpec409Conflict(){
        return responseSpec(409);
    }

    public static ResponseSpecification responseSpec200Ok(){
        return responseSpec(200);
    }

    public static ResponseSpecification responseSpec404NotFound(){
        return responseSpec(404);
    }

    public static void installSpecification(RequestSpecification request, ResponseSpecification response){
        RestAssured.requestSpecification = request;
        RestAssured.responseSpecification = response;
    }
}
