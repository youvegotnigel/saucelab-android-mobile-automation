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
        Assert.assertTrue(isDisplayedByAccessibilityId(loginPage.get_ACID_USERNAME()));
        Assert.assertTrue(isDisplayedByAccessibilityId(loginPage.get_ACID_PASSWORD()));
    }

    @And("User is logged into SauceLabs Mobile App")
    public void login_as_valid_user() {
        LoadConfigProperty();
        sendTextByAccessibilityId(loginPage.get_ACID_USERNAME(), getGlobalVariable("_username"));
        sendTextByAccessibilityId(loginPage.get_ACID_PASSWORD(), decodeText(config.getProperty("_password")));
        clickByAccessibilityId(loginPage.get_ACID_LOGIN());
        Assert.assertTrue(isDisplayedByAccessibilityId(loginPage.get_ACID_MENU()));
    }

    @And("I log in as {string} user")
    public void login_as_any_user(String string) {
        LoadConfigProperty();
        sendTextByAccessibilityId(loginPage.get_ACID_USERNAME(), string);
        sendTextByAccessibilityId(loginPage.get_ACID_PASSWORD(), decodeText(config.getProperty("_password")));
        clickByAccessibilityId(loginPage.get_ACID_LOGIN());
    }

    @And("I log in as standard user")
    public void login_as_standard_user() {
        clickByAccessibilityId(loginPage.get_ACID_STANDARD_USER());
        clickByAccessibilityId(loginPage.get_ACID_LOGIN());
    }

    @And("I log in as locked out user")
    public void login_as_locked_out_user() {
        clickByAccessibilityId(loginPage.get_ACID_LOCKED_OUT_USER());
        clickByAccessibilityId(loginPage.get_ACID_LOGIN());
    }

    @And("I log in as problem user")
    public void login_as_problem_user() {
        clickByAccessibilityId(loginPage.get_ACID_PROBLEM_USER());
        clickByAccessibilityId(loginPage.get_ACID_LOGIN());
    }

    @And("I enter {string} in Username text box")
    public void enter_username(String string) {
        sendTextByAccessibilityId(loginPage.get_ACID_USERNAME(), string);
    }

    @And("I enter {string} in Password text box")
    public void enter_password(String string) {
        sendTextByAccessibilityId(loginPage.get_ACID_PASSWORD(), string);
    }

    @And("I tap on Login button")
    public void tab_login_button() {
        clickByAccessibilityId(loginPage.get_ACID_LOGIN());
    }

    @And("System should display {string} Error Message")
    public void display_error_message(String errorMsg) {
        Assert.assertEquals(getText(loginPage.get_XPATH_LOCKED_OUT_USER_ERROR_MSG()), errorMsg);

    }
}
