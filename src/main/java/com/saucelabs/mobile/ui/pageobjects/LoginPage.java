package com.saucelabs.mobile.ui.pageobjects;

public class LoginPage extends BasePage{

    //Locators
    private final String ACID_USERNAME = "test-Username";
    private final String ACID_PASSWORD = "test-Password";
    private final String ACID_LOGIN = "test-LOGIN";

    private final String ACID_STANDARD_USER = "test-standard_user";
    private final String ACID_LOCKED_OUT_USER = "test-locked_out_user";
    private final String ACID_PROBLEM_USER = "test-problem_user";


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
}
