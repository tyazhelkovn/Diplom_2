package builder;

import java.util.HashMap;
import java.util.Map;

import base.BaseClass;
import io.restassured.response.Response;
import models.Ingredient;

import static io.restassured.RestAssured.given;

public class OrderRequests extends BaseClass {

    private final static String ORDER_PATH = "/api/orders";
    UserRequests userRequests = new UserRequests();

    public Response createOrder(Ingredient ingredients, boolean withAuth) {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-type", "application/json");
        if (withAuth) {
            headers.put("Authorization", userRequests.getToken());
        }
        Response response =
                given()
                        .spec(getRequestSpecification())
                        .headers(headers)
                        .and()
                        .body(ingredients)
                        .when()
                        .post(ORDER_PATH);
        return response;
    }

    public String   getIngredient() {
        String ingredientId =
                given()
                        .spec(getRequestSpecification())
                        .when()
                        .get("/api/ingredients")
                        .then()
                        .extract()
                        .body()
                        .path("data[0]._id");
        return ingredientId;
    }

    public Response getOrder(boolean withAuth) {
        Map<String, String> headers = new HashMap<>();
        if (withAuth) {
            headers.put("Authorization", userRequests.getToken());
        }
        Response response =
                given()
                        .spec(getRequestSpecification())
                        .headers(headers)
                        .when()
                        .get(ORDER_PATH);
        return response;
    }
}
