package builder;


import java.util.HashMap;
import java.util.Map;

import base.BaseClass;
import io.restassured.response.Response;
import models.User;

import static io.restassured.RestAssured.given;

public class UserRequests extends BaseClass {
    private static String accessToken = null;

    private final static String USER_PATH = "/api/auth/";

    public Response createUser(User user) {
        Response response =
                given()
                        .spec(getRequestSpecification())
                        .and()
                        .body(user)
                        .when()
                        .post(USER_PATH + "register");
        if (accessToken == null) {
            saveAccessTokens(response);
        }
        return response;
    }

    public void deleteUser() {
        if (accessToken != null) {
            given()
                    .spec(getRequestSpecification())
                    .header("Authorization", accessToken)
                    .when()
                    .delete(USER_PATH + "user");
        }
    }

    public Response loginUser(User user) {
        Response response =
                given()
                        .spec(getRequestSpecification())
                        .and()
                        .body(user)
                        .when()
                        .post(USER_PATH + "login");
        return response;
    }

    public Response editUser(User user, boolean withAuth) {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-type", "application/json");
        if (withAuth) {
            headers.put("Authorization", accessToken);
        }
        Response response =
                given()
                        .spec(getRequestSpecification())
                        .headers(headers)
                        .and()
                        .body(user)
                        .when()
                        .patch(USER_PATH + "user");
        return response;
    }

    public void saveAccessTokens(Response response) {
        accessToken = response.then().extract().body().path("accessToken");
    }

    public String getToken() {
        return accessToken;
    }

    public void clearTokens() {
        accessToken = null;
    }
}
