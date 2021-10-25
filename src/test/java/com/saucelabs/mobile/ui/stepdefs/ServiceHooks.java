package com.saucelabs.mobile.ui.stepdefs;

import com.browserstack.local.Local;
import com.saucelabs.mobile.ui.base.TestBase;
import com.saucelabs.mobile.ui.utils.CreateEnvFile;
import com.saucelabs.mobile.ui.utils.GlobalVariable;
import io.cucumber.java.After;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.qameta.allure.Attachment;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriverException;

import java.util.HashMap;
import java.util.Map;

import static org.openqa.selenium.OutputType.BYTES;

public class ServiceHooks {

    private static TestBase testBase;
    public static final Logger log = LogManager.getLogger(ServiceHooks.class.getName());
    // Creates an instance of Local
    Local bsLocal = new Local();

    @Before
    public void initializeTest(){
        testBase = new TestBase();
        testBase.setup();
        testBase.implicitWait(30);
    }

    @Before
    public void beforeStartScenario(Scenario scenario){
        log.debug("✰ Started scenario : " + scenario.getName());

        Map<String, String> options = new HashMap<String, String>();
        options.put("key", GlobalVariable._browserStackKey);
        try {
            bsLocal.start(options);
            log.debug("Is browserstack local running ::: " + bsLocal.isRunning());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @AfterStep
    public void takeScreenshotAfterEachStep(Scenario scenario){
        try {
            TakesScreenshot screenshot = (TakesScreenshot) testBase.getAndroidDriver();
            byte[] data = screenshot.getScreenshotAs(OutputType.BYTES);
            scenario.attach(data, "image/png", "Attachment");
            //log.debug("Screenshot taken");
            takeScreenshotToAttachOnAllureReport();
        }catch (WebDriverException e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
    }

    @After
    public void endTest(Scenario scenario) {

        if(!scenario.isFailed()){
            log.debug("✔ Passed scenario : " + scenario.getName());
        }
        if(scenario.isFailed()){
            log.error("✘ Failed scenario : " + scenario.getName());
        }
        CreateEnvFile createEnvFile = new CreateEnvFile();
        createEnvFile.createFile();
        testBase.tearDown();

        if(bsLocal != null) {
            try {
                bsLocal.stop();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Attachment(value = "Screenshot", type = "image/png")
    public static byte[] takeScreenshotToAttachOnAllureReport() {
        return ((TakesScreenshot) testBase.getAndroidDriver()).getScreenshotAs(BYTES);
    }
}
