package tests;

import java.util.Random;

import io.restassured.response.Response;
import models.Ingredient;
import models.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import steps.TestSteps;

public class OrderTests {

    TestSteps steps = new TestSteps();
    Ingredient ingredients = new Ingredient();
    User user;

    @Before
    public void setUp() {
        ingredients.setIngredient(steps.getIngredientStep());
        int random = new Random().nextInt(10);
        user = new User("test_nt_" + random + "@ya.ru",
                "test_pass",
                "test_nt_" + random);
        System.out.println(user.getName());
    }

    @After
    public void tearDown() throws InterruptedException {
        steps.deleteUserStep();
        steps.clearCashStep();
    }

    @Test
    public void createNewOrderWithAuthTest() {
        steps.createUserStep(user);
        Response response = steps.createOrderStep(ingredients, true);
        steps.compareBodyAndStatusCodeForCreateSuccessOrderStep(response);
    }

    @Test
    public void createNewOrderWithoutAuthTest() {
        steps.createUserStep(user);
        Response response = steps.createOrderStep(ingredients, false);
        steps.compareBodyAndStatusCodeForCreateSuccessOrderStep(response);
    }

    @Test
    public void createNewOrderWithoutIngredientsWithAuthTest() {
        steps.createUserStep(user);
        Ingredient ingredients = new Ingredient();
        ingredients.setIngredient(null);
        Response response = steps.createOrderStep(ingredients, true);
        steps.compareBodyAndStatusCodeForCreateOrderWithoutIngredientsStep(response);
    }

    @Test
    public void createNewOrderWithoutIngredientsWithoutAuthTest() {
        steps.createUserStep(user);
        Ingredient ingredients = new Ingredient();
        ingredients.setIngredient(null);
        Response response = steps.createOrderStep(ingredients, false);
        steps.compareBodyAndStatusCodeForCreateOrderWithoutIngredientsStep(response);
    }

    @Test
    public void createNewOrderWithWrongIngredientTest() {
        steps.createUserStep(user);
        Ingredient ingredients = new Ingredient();
        ingredients.setIngredient("67c0c5a71d1f82001bdabb6d");
        Response response = steps.createOrderStep(ingredients, false);
        steps.compareBodyAndStatusCodeForCreateOrderWithoutIngredientsStep(response);
    }

    @Test
    public void getOrderWithAuthTest() {
        steps.createUserStep(user);
        steps.createOrderStep(ingredients, true);
        Response response = steps.getOrderStep(true);
        steps.compareBodyAndStatusCodeForGetOrderWithAuthStep(response, ingredients.getIngredients().get(0));
    }

    @Test
    public void getOrderWithoutAuthTest() {
        steps.createUserStep(user);
        steps.createOrderStep(ingredients, true);
        Response response = steps.getOrderStep(false);
        steps.compareBodyAndStatusCodeForGetOrderWithoutAuthStep(response);
    }


}
