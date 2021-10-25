package com.saucelabs.mobile.ui.sample_tests;

import com.saucelabs.mobile.ui.utils.ListenerClass;
import io.appium.java_client.ios.IOSDriver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.io.FileInputStream;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Properties;

@Listeners(ListenerClass.class)
public class POCTest {

    public static Properties config;
    public static final Logger log = LogManager.getLogger(POCTest.class.getName());
    private static ThreadLocal<IOSDriver> iOSDriver = new ThreadLocal<IOSDriver>();
    public static final long WAIT = 30;

    //Locators
    By username_text_box = By.xpath("//*[@type = 'XCUIElementTypeTextField']");
    By password_text_box = By.xpath("//*[@type = 'XCUIElementTypeSecureTextField']");
    By login_btn = By.xpath("//*[@type = 'XCUIElementTypeButton' and @label = 'Login' and @name = 'Login']");


    @BeforeMethod
    public void setup(Method method) throws MalformedURLException {

        LoadConfigProperty();
        String username = config.getProperty("_sauceLabsUserName");
        String accesskey = config.getProperty("_sauceLabsKey");
        String appUrl = config.getProperty("_sauceLabsAppUrl");
        String appName = config.getProperty("_sauceLabsAppName");
        String methodName = method.getName();

        String sauceUrl = "@ondemand.us-west-1.saucelabs.com:443";
        String SAUCE_REMOTE_URL = "https://" + username + ":" + accesskey + sauceUrl +"/wd/hub";
        log.debug(SAUCE_REMOTE_URL);

        URL url = new URL(SAUCE_REMOTE_URL);

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("deviceName", "iPhone XR");
        capabilities.setCapability("platformName", "iOS");
        capabilities.setCapability("platformVersion", "15.0.1");
        //capabilities.setCapability("app", "storage:filename="+appName);
        capabilities.setCapability("app", "storage:"+appUrl);
        capabilities.setCapability("autoAcceptAlerts", true);
        capabilities.setCapability("tunnelIdentifier", "nigel-tunnel");
        capabilities.setCapability("name", methodName);

        iOSDriver.set(new IOSDriver(url, capabilities));
    }

    @Test
    public void poc(){
        log.debug("iOS App is open");

        //Login
        sendText(username_text_box,"tvcc.sysadmin");
        sendText(password_text_box,"tested");
        click(login_btn);
        delay(10000);
        click(login_btn);
        delay(30000);
        Assert.assertTrue(getIosDriver().findElementByAccessibilityId("AS").isDisplayed());
        delay(10000);

        //Pre Steps
//        clickByAccessibilityId("Cherry Orchard - PSW B-Day");
//        clickByAccessibilityId("PSW B-Day");
//        clickByAccessibilityId("Close");

        //Steps
        clickByAccessibilityId("Tap to Select Shift Assignments");
        clickByAccessibilityId("PSW");
        clickByAccessibilityId("Day");
        clickByAccessibilityId("Cherry Orchard");
        clickByAccessibilityId("PSW B-Day");
        clickByAccessibilityId("Close");

        //Assertions
        Assert.assertTrue(isDisplayedByAccessibilityId("Hubbard, Bruce (I5BEZDQJQY)"));
        Assert.assertTrue(isDisplayedByAccessibilityId("Steele, Domingo (JVF7PQHWAT)"));

        //Closing Steps
        clickByAccessibilityId("Cherry Orchard - PSW B-Day");
        clickByAccessibilityId("PSW B-Day");
        clickByAccessibilityId("Close");
    }

    @AfterMethod
    public void teardown(ITestResult result) {
        ((JavascriptExecutor)getIosDriver()).executeScript("sauce:job-result=" + (result.isSuccess() ? "passed" : "failed"));
        getIosDriver().quit();
    }

    public  IOSDriver getIosDriver() {
        return iOSDriver.get();
    }

    public void waitForVisibility(By by) {
        IOSDriver driver = getIosDriver();
        try {
            WebDriverWait wait = new WebDriverWait(driver, WAIT);
            wait.until(ExpectedConditions.visibilityOf(driver.findElement(by)));
        }catch (Exception e){
            log.debug("Element not found ::: " + by);
        }
    }

    public void waitForVisibilityByAccessibilityId(String id) {
        IOSDriver driver = getIosDriver();
        try {
            WebDriverWait wait = new WebDriverWait(driver, WAIT);
            wait.until(ExpectedConditions.visibilityOf(driver.findElementByAccessibilityId(id)));
        }catch (Exception e){
            log.debug("Element not found ::: " + id);
        }
    }

    public void click(By by) {
        IOSDriver driver = getIosDriver();
        waitForVisibility(by);
        driver.findElement(by).click();
    }

    public void clickByAccessibilityId(String id) {
        IOSDriver driver = getIosDriver();
        waitForVisibilityByAccessibilityId(id);
        driver.findElementByAccessibilityId(id).click();
        log.debug("Element clicked on ::: "+ id);
        delay(10000);
    }

    public void setCheckBox(By by) {
        IOSDriver driver = getIosDriver();
        waitForVisibility(by);
        driver.findElement(by).click();
    }

    public void sendText(By by, String text) {
        IOSDriver driver = getIosDriver();
        waitForVisibility(by);
        driver.findElement(by).sendKeys(text);
    }

    public String getText(By by) {
        IOSDriver driver = getIosDriver();
        waitForVisibility(by);
        return  driver.findElement(by).getText();
    }

    public boolean isDisplayed(By by){
        IOSDriver driver = getIosDriver();
        //waitForVisibility(by);
        try {
            return  driver.findElement(by).isDisplayed();
        }catch (NoSuchElementException e){
            log.error("Element not found ::: " + by);
            return false;
        }
    }

    public boolean isDisplayedByAccessibilityId(String id){
        IOSDriver driver = getIosDriver();
        try {
            return  driver.findElementByAccessibilityId(id).isDisplayed();
        }catch (NoSuchElementException e){
            log.error("Element not found by AccessibilityId::: " + id);
            return false;
        }
    }

    public void delay(int n){
        try {
            Thread.sleep(n);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void hideKeyboard() {
        IOSDriver driver = getIosDriver();
        driver.hideKeyboard();
    }

    public void LoadConfigProperty(){
        try {
            config = new Properties();
            FileInputStream ip = new FileInputStream(
                    System.getProperty("user.dir") + "/src/test/java/resources/config/config.properties");
            config.load(ip);
            log.info("Properties file loaded successfully");
        }catch (Exception e){
            log.error("Configuration Properties file not found." + Arrays.toString(e.getStackTrace()));
        }

    }
}
