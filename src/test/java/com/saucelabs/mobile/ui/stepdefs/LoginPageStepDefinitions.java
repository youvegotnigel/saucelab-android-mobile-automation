package com.saucelabs.mobile.ui.stepdefs;

import com.saucelabs.mobile.ui.base.TestBase;
import com.saucelabs.mobile.ui.pageobjects.LoginPage;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import org.testng.Assert;

public class LoginPageStepDefinitions extends TestBase {
    LoginPage loginPage = new LoginPage();

    @Given("The Application has been launched")
    public void application_is_launched() {
        Assert.assertTrue(isDisplayedByAccessibilityId("usernameIcon"));
        Assert.assertTrue(isDisplayedByAccessibilityId("passwordIcon"));
    }

    @And("User is logged into DOCit")
    public void login_as_valid_user() {
        LoadConfigProperty();
        sendText(loginPage.get_username_textbox_locator(), getGlobalVariable("_username"));
        sendText(loginPage.get_password_textbox_locator(), decodeText(config.getProperty("_password")));
        click(loginPage.get_login_button_locator());
        click(loginPage.get_login_button_locator());
        Assert.assertTrue(isDisplayedByAccessibilityId("AS"));
    }

    @And("I log in as {string} user")
    public void login_as_any_user(String string) {
        LoadConfigProperty();
        sendText(loginPage.get_username_textbox_locator(), string);
        sendText(loginPage.get_password_textbox_locator(), decodeText(config.getProperty("_password")));
        click(loginPage.get_login_button_locator());
    }

    @And("I enter {string} in Username text box")
    public void enter_username(String string) {
        sendText(loginPage.get_username_textbox_locator(), string);
    }

    @And("I enter {string} in Password text box")
    public void enter_password(String string) {
        sendText(loginPage.get_password_textbox_locator(), string);
    }

    @And("I tap on Login button")
    public void tab_login_button() {
        click(loginPage.get_login_button_locator());
    }

    @And("System should display {string} Error Message")
    public void display_error_message(String errorMsg) {

    }
}
