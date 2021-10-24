package com.saucelabs.mobile.ui.pageobjects;

import org.openqa.selenium.By;

public class LoginPage extends BasePage{

    //Locators
    private By login_btn = By.xpath("//*[@type = 'XCUIElementTypeButton' and @label = 'Login' and @name = 'Login']");
    private By username_tb = By.xpath("//*[@type = 'XCUIElementTypeTextField']");
    private By password_tb = By.xpath("//*[@type = 'XCUIElementTypeSecureTextField']");

    public By get_login_button_locator(){
        return login_btn;
    }

    public By get_username_textbox_locator(){
        return username_tb;
    }

    public By get_password_textbox_locator(){
        return password_tb;
    }
}
