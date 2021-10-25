package com.saucelabs.mobile.ui.pageobjects;

import org.openqa.selenium.By;

public class LoginPage extends BasePage{

    //Locators
    private final String ACID_USERNAME = "test-Username";
    private final String ACID_PASSWORD = "test-Password";
    private final String ACID_LOGIN = "test-LOGIN";

    private final String ACID_STANDARD_USER = "test-standard_user";
    private final String ACID_LOCKED_OUT_USER = "test-locked_out_user";
    private final String ACID_PROBLEM_USER = "test-problem_user";

    private final By XPATH_LOCKED_OUT_USER_ERROR_MSG = By.xpath("//android.view.ViewGroup[@content-desc=\"test-Error message\"]/android.widget.TextView");


    public String get_ACID_USERNAME(){
        return ACID_USERNAME;
    }

    public String get_ACID_PASSWORD(){
        return ACID_PASSWORD;
    }

    public String get_ACID_LOGIN(){
        return ACID_LOGIN;
    }

    public String get_ACID_STANDARD_USER(){
        return ACID_STANDARD_USER;
    }

    public String get_ACID_LOCKED_OUT_USER(){
        return ACID_LOCKED_OUT_USER;
    }

    public String get_ACID_PROBLEM_USER(){
        return ACID_PROBLEM_USER;
    }

    public By get_XPATH_LOCKED_OUT_USER_ERROR_MSG(){
        return XPATH_LOCKED_OUT_USER_ERROR_MSG;
    }
}
