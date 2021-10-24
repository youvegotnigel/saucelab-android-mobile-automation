package com.saucelabs.mobile.ui.base;

import com.google.common.io.Files;
import com.saucelabs.mobile.ui.utils.GlobalVariable;
import com.saucelabs.mobile.ui.utils.ListenerClass;
import io.appium.java_client.android.AndroidDriver;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.*;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;


public class TestBase {

    private static ThreadLocal<AndroidDriver> AndroidDriver = new ThreadLocal<AndroidDriver>();
    public static EventFiringWebDriver eventFiringWebDriver;
    public static Properties config;
    private static final String fileSeparator = File.separator;
    public static final Logger log = LogManager.getLogger(TestBase.class.getName());

    public void LoadConfigProperty(){
        try {
            config = new Properties();
            FileInputStream ip = new FileInputStream(
                    System.getProperty("user.dir") + "//src//test//resources//config//config.properties");
            config.load(ip);
            log.info("Properties file loaded successfully");
        }catch (Exception e){
            log.error("Configuration Properties file not found." + Arrays.toString(e.getStackTrace()));
        }

    }

    public void setup(){
        // loads the config options
        LoadConfigProperty();

        switch (config.getProperty("PLATFORM")) {
            case "saucelabs":
                setupSauceLabs();
                break;

            case "browserstack":
                setupBrowserStack();
                break;

            case "local":
                setupAndroidLocal();
                break;
        }

        eventFiringWebDriver = new EventFiringWebDriver((WebDriver) getAndroidDriver());
        ListenerClass listener = new ListenerClass();
        eventFiringWebDriver.register(listener);
    }

    private void setupAndroidLocal(){

        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("platformName", "Android");
        caps.setCapability("platformVersion", "10.0");
        caps.setCapability("deviceName", "Android Emulator");
        caps.setCapability("app",System.getProperty("user.dir") + "/apps/Android.SauceLabs.Mobile.Sample.app.2.7.1.apk");

        String REMOTE_URL = "http://localhost:4723/wd/hub";
        log.debug(REMOTE_URL);

        URL url = null;
        try {
            url = new URL(REMOTE_URL);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        AndroidDriver.set(new AndroidDriver(url, caps));
    }

    private void setupSauceLabs(){
        LoadConfigProperty();
        String username = config.getProperty("_sauceLabsUserName");
        String accesskey = config.getProperty("_sauceLabsKey");
        String appUrl = config.getProperty("_sauceLabsAppUrl");
        String appName = config.getProperty("_sauceLabsAppName");
        //String methodName = method.getName();

        String sauceUrl = "@ondemand.us-west-1.saucelabs.com:443";
        String SAUCE_REMOTE_URL = "https://" + username + ":" + accesskey + sauceUrl +"/wd/hub";
        log.debug(SAUCE_REMOTE_URL);

        URL url = null;
        try {
            url = new URL(SAUCE_REMOTE_URL);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("deviceName", "iPhone XR");
        capabilities.setCapability("platformName", "iOS");
        capabilities.setCapability("platformVersion", "15.0.1");
        //capabilities.setCapability("app", "storage:filename="+appName);
        capabilities.setCapability("app", "storage:"+appUrl);
        capabilities.setCapability("autoAcceptAlerts", true);
        capabilities.setCapability("tunnelIdentifier", "nigel-tunnel");
        //capabilities.setCapability("name", methodName);

        AndroidDriver.set(new AndroidDriver(url, capabilities));
    }

    private void setupBrowserStack(){

        LoadConfigProperty();
        String username = config.getProperty("_browserStackUsername");
        String accesskey = config.getProperty("_browserStackKey");
        String appUrl = config.getProperty("_browserStackAppUrl");
        String appName = config.getProperty("_browserStackAppName");
        //String methodName = method.getName();

        String browserStackUrl = "@hub-cloud.browserstack.com/wd/hub";
        String BROWSERSTACK_REMOTE_URL = "https://" + username + ":" + accesskey + browserStackUrl;
        log.debug(BROWSERSTACK_REMOTE_URL);

        URL url = null;
        try {
            url = new URL(BROWSERSTACK_REMOTE_URL);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        DesiredCapabilities capabilities = new DesiredCapabilities();

        capabilities.setCapability("device", "iPhone 12");
        capabilities.setCapability("os_version", "14.4");
        capabilities.setCapability("app", appUrl);
        capabilities.setCapability("noReset", false);
        capabilities.setCapability("autoAcceptAlerts", true);
        capabilities.setCapability("browserstack.local", true);

        AndroidDriver.set(new AndroidDriver(url, capabilities));
    }

    public void implicitWait(int time) {
        eventFiringWebDriver.manage().timeouts().implicitlyWait(time, TimeUnit.SECONDS);
    }

    public void explicitWait(WebElement element) {
        WebDriverWait wait = new WebDriverWait(eventFiringWebDriver, 3000);
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    public void pageLoad(int time) {
        eventFiringWebDriver.manage().timeouts().pageLoadTimeout(time, TimeUnit.SECONDS);
    }


    public static String getTimestamp() {
        String currentDate = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss").format(new Date());
        return currentDate;
    }


    public static String takeScreenshot(String screenshotName) throws IOException {

        TakesScreenshot ts = (TakesScreenshot) eventFiringWebDriver;
        File source = ts.getScreenshotAs(OutputType.FILE);

        String destination = System.getProperty("user.dir") + fileSeparator + "test-output" + fileSeparator + "html-report" + fileSeparator + "screenshots" +fileSeparator+ screenshotName + " - " + getTimestamp() + ".png";
        File finalDestination = new File(destination);
        FileUtils.copyFile(source, finalDestination);
        return destination;

    }


    public void takeScreenshot2(ITestResult result) throws IOException {
        if (result.isSuccess()) {
            File imageFile = ((TakesScreenshot) eventFiringWebDriver).getScreenshotAs(OutputType.FILE);
            String failureImageFileName = result.getMethod().getMethodName()
                    + new SimpleDateFormat("MM-dd-yyyy_HH-ss").format(new GregorianCalendar().getTime()) + ".png";
            File failureImageFile = new File(System.getProperty("user.dir") + "//screenshots//" + failureImageFileName);
            failureImageFile.getParentFile().mkdir();
            failureImageFile.createNewFile();
            Files.copy(imageFile, failureImageFile);
        }

    }

    public void waitForVisibility(By by) {
        AndroidDriver driver = getAndroidDriver();
        try {
            WebDriverWait wait = new WebDriverWait(driver, GlobalVariable.WAIT_TIME);
            wait.until(ExpectedConditions.visibilityOf(driver.findElement(by)));
        }catch (Exception e){
            log.debug("Element not found ::: " + by);
        }
    }

    public void waitForVisibilityByAccessibilityId(String id) {
        AndroidDriver driver = getAndroidDriver();
        try {
            WebDriverWait wait = new WebDriverWait(driver, GlobalVariable.WAIT_TIME);
            wait.until(ExpectedConditions.visibilityOf(driver.findElementByAccessibilityId(id)));
        }catch (Exception e){
            log.debug("Element not found ::: " + id);
        }
    }

    public void click(By by) {
        AndroidDriver driver = getAndroidDriver();
        waitForVisibility(by);
        driver.findElement(by).click();
    }

    public void clickByAccessibilityId(String id) {
        AndroidDriver driver = getAndroidDriver();
        waitForVisibilityByAccessibilityId(id);
        driver.findElementByAccessibilityId(id).click();
        log.debug("Element clicked on ::: "+ id);
        delay(10000);
    }

    public void setCheckBox(By by) {
        AndroidDriver driver = getAndroidDriver();
        waitForVisibility(by);
        driver.findElement(by).click();
    }

    public void sendText(By by, String text) {
        AndroidDriver driver = getAndroidDriver();
        waitForVisibility(by);
        driver.findElement(by).sendKeys(text);
    }

    public void sendTextByAccessibilityId(String id, String text) {
        AndroidDriver driver = getAndroidDriver();
        waitForVisibilityByAccessibilityId(id);
        driver.findElementByAccessibilityId(id).sendKeys(text);
    }

    public String getText(By by) {
        AndroidDriver driver = getAndroidDriver();
        waitForVisibility(by);
        return  driver.findElement(by).getText();
    }

    public boolean isDisplayed(By by){
        AndroidDriver driver = getAndroidDriver();
        //waitForVisibility(by);
        try {
            return  driver.findElement(by).isDisplayed();
        }catch (NoSuchElementException e){
            log.error("Element not found ::: " + by);
            return false;
        }
    }

    public boolean isDisplayedByAccessibilityId(String id){
        AndroidDriver driver = getAndroidDriver();
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
        AndroidDriver driver = getAndroidDriver();
        driver.hideKeyboard();
    }

    public void tearDown(){
        //((JavascriptExecutor)getIosDriver()).executeScript("sauce:job-result=" + (result.isSuccess() ? "passed" : "failed"));
        getAndroidDriver().quit();
        log.debug("Closing iOS driver");
    }

    public AndroidDriver getAndroidDriver() {
        return AndroidDriver.get();
    }

    public String decodeText(String text){
        if(text == "" || text == null){
            return " ";
        }
        byte[] actualByte = Base64.getDecoder().decode(text);
        String actualString = new String(actualByte);
        return actualString;
    }

    public String getGlobalVariable(String variable){

        if(variable.startsWith("_")){
            LoadConfigProperty();
            return config.getProperty(variable);
        }
        return  variable;
    }

    public String[] getValueAndIndex(String value) {
        String[] values = value.split(Pattern.quote("["));
        values[1] = values[1].replaceAll("[^\\d.]", "");
        return values;
    }

    public void printList(List <String> list, String name){
        System.out.println("List name : " + name);
        for(String a : list){
            System.out.println("list : " + a);
        }
    }


}
