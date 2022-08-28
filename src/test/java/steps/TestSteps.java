package steps;


import builder.OrderRequests;
import builder.UserRequests;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import models.Ingredient;
import models.User;

import static org.apache.http.HttpStatus.SC_BAD_REQUEST;
import static org.apache.http.HttpStatus.SC_FORBIDDEN;
import static org.apache.http.HttpStatus.SC_OK;
import static org.apache.http.HttpStatus.SC_UNAUTHORIZED;
import static org.hamcrest.Matchers.equalTo;

public class TestSteps {

    UserRequests userRequests = new UserRequests();
    OrderRequests orderRequests = new OrderRequests();

    @Step("Creating new user")
    public Response createUserStep(User user) {
        return userRequests.createUser(user);
    }

    @Step("Remove user")
    public void deleteUserStep() {
        userRequests.deleteUser();
    }

    @Step("login user")
    public Response loginUserStep(User user) {
        return userRequests.loginUser(user);
    }

    @Step("login user")
    public Response editUserStep(User user, boolean withAuth) {
        return userRequests.editUser(user, withAuth);
    }

    @Step("Clear cash")
    public void clearCashStep() {
        userRequests.clearTokens();
    }

    @Step("Get ingredient")
    public String getIngredientStep() {
        return orderRequests.getIngredient();
    }

    @Step("Create new order")
    public Response createOrderStep(Ingredient ingredients, boolean withAuth) {
        return orderRequests.createOrder(ingredients, withAuth);
    }

    @Step("Get order")
    public Response getOrderStep(boolean withAuth) {
        return orderRequests.getOrder(withAuth);
    }

    @Step("Comparing body and status code for success creating user")
    public void compareBodyAndStatusCodeForSuccessStep(Response response) {
        response.then().assertThat().body("success", equalTo(true))
                .and()
                .statusCode(SC_OK);
    }

    @Step("Comparing body and status code for existing user")
    public void compareBodyAndStatusCodeForExistingUserCreateStep(Response response) {
        response.then().assertThat().body("success", equalTo(false),
                        "message", equalTo("User already exists"))
                .and()
                .statusCode(SC_FORBIDDEN);
    }

    @Step("Comparing body and status code for incorrect request")
    public void compareBodyAndStatusCodeForIncorrectStep(Response response) {
        response.then().assertThat().body("success", equalTo(false),
                        "message", equalTo("Email, password and name are required fields"))
                .and()
                .statusCode(SC_FORBIDDEN);
    }

    @Step("Comparing body and status code for incorrect request")
    public void compareBodyAndStatusCodeForLoginWithInvalidFieldsStep(Response response) {
        response.then().assertThat().body("success", equalTo(false),
                        "message", equalTo("email or password are incorrect"))
                .and()
                .statusCode(SC_UNAUTHORIZED);
    }

    @Step("Comparing body and status code for edited user with authorization")
    public void compareBodyAndStatusCodeForEditedUserWithAuthStep(Response response, String expectedValue, String path) {
        response.then().assertThat().body("success", equalTo(true),
                        path, equalTo(expectedValue))
                .and()
                .statusCode(SC_OK);
    }

    @Step("Comparing body and status code for edited user without authorization")
    public void compareBodyAndStatusCodeForEditedUserWithoutAuthStep(Response response) {
        response.then().assertThat().body("success", equalTo(false),
                        "message", equalTo("You should be authorised"))
                .and()
                .statusCode(SC_UNAUTHORIZED);
    }

    @Step("Comparing body and status code for create success order")
    public void compareBodyAndStatusCodeForCreateSuccessOrderStep(Response response) {
        response.then().assertThat().body("success", equalTo(true))
                .and()
                .statusCode(SC_OK);
    }

    @Step("Comparing body and status code for create order without ingredients")
    public void compareBodyAndStatusCodeForCreateOrderWithoutIngredientsStep(Response response) {
        response.then().assertThat().body("success", equalTo(false),
                        "message", equalTo("One or more ids provided are incorrect"))
                .and()
                .statusCode(SC_BAD_REQUEST);
    }
    @Step("Comparing body and status code dor get order with authtorization")
    public void compareBodyAndStatusCodeForGetOrderWithAuthStep(Response response, String ingredient) {
        response.then().assertThat().body("success", equalTo(true),
                        "orders[0].ingredients[0]", equalTo(ingredient))
                .and()
                .statusCode(SC_OK);
    }

    @Step("Comparing body and status code for get order without authtorization")
    public void compareBodyAndStatusCodeForGetOrderWithoutAuthStep(Response response) {
        response.then().assertThat().body("success", equalTo(false),
                        "message", equalTo("You should be authorised"))
                .and()
                .statusCode(SC_UNAUTHORIZED);
    }
}
