package tests;

import java.util.Random;

import io.restassured.response.Response;
import models.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import steps.TestSteps;

public class EditUserTests {

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
    public void editUserNameWithAuthTest() {
        String editedName = "editedName";
        steps.createUserStep(user);
        User editUser = new User(user.getEmail(), user.getPassword(), editedName);
        Response response = steps.editUserStep(editUser, true);
        steps.compareBodyAndStatusCodeForEditedUserWithAuthStep(response, editedName, "user.name");
    }

    @Test
    public void editUserEmailWithAuthTest() {
        String editedEmail = "editedemail@ya.ru";
        steps.createUserStep(user);
        User editUser = new User(editedEmail, user.getPassword(), user.getName());
        Response response = steps.editUserStep(editUser, true);
        steps.compareBodyAndStatusCodeForEditedUserWithAuthStep(response, editedEmail, "user.email");
    }

    @Test
    public void editUserNameWithoutAuthTest() {
        String editedName = "editedName";
        steps.createUserStep(user);
        User editUser = new User(user.getEmail(), user.getPassword(), editedName);
        Response response = steps.editUserStep(editUser, false);
        steps.compareBodyAndStatusCodeForEditedUserWithoutAuthStep(response);
    }

    @Test
    public void editUserEmailWithoutAuthTest() {
        String editedEmail = "editedemail@ya.ru";
        steps.createUserStep(user);
        User editUser = new User(editedEmail, user.getPassword(), user.getPassword());
        Response response = steps.editUserStep(editUser, false);
        steps.compareBodyAndStatusCodeForEditedUserWithoutAuthStep(response);
    }
}
