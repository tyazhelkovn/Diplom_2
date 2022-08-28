package tests;

import java.util.Random;

import io.restassured.response.Response;
import models.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import steps.TestSteps;

public class CreateUserTests {

    TestSteps steps = new TestSteps();
    User user;
    @Before
    public void setUp() {
        int random = new Random().nextInt(10);
        user = new User("test_nt_" + random + "@ya.ru",
                "test_pass",
                "test_nt_" + random);
    }

    @After
    public void tearDown() throws InterruptedException {
        steps.deleteUserStep();
        steps.clearCashStep();
    }

    @Test
    public void createNewUserTest() {
        Response response = steps.createUserStep(user);
        steps.compareBodyAndStatusCodeForSuccessStep(response);
    }

    @Test
    public void createDoubleUserTest() {
        steps.createUserStep(user);
        Response response = steps.createUserStep(user);
        steps.compareBodyAndStatusCodeForExistingUserCreateStep(response);
    }

    @Test
    public void createUserWithoutRequiredFieldTest() {
        user.setName("");
        Response response = steps.createUserStep(user);
        steps.compareBodyAndStatusCodeForIncorrectStep(response);
    }
}
