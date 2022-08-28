package tests;

import java.util.Random;

import io.restassured.response.Response;
import models.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import steps.TestSteps;

public class LoginUserTests {

    TestSteps steps = new TestSteps();
    User user;
    @Before
    public void setUp() {
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
    public void loginUserTest() {
        steps.createUserStep(user);
        Response response = steps.loginUserStep(user);
        steps.compareBodyAndStatusCodeForSuccessStep(response);

    }

    @Test
    public void loginUserWithInvalidEmailTest() {
        steps.createUserStep(user);
        user.setEmail("s" + user.getEmail());
        Response response = steps.loginUserStep(user);
        steps.compareBodyAndStatusCodeForLoginWithInvalidFieldsStep(response);
    }
}
