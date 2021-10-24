package com.saucelabs.mobile.ui.pageobjects;

import org.openqa.selenium.By;

public class BasePage {

    private By navigation_bar = By.xpath("//XCUIElementTypeStaticText[@name=\"${field_name}\"]|//XCUIElementTypeButton[@name=\"${field_name}\"]");

    public By getNavigation_bar(){
        return navigation_bar;
    }
}
