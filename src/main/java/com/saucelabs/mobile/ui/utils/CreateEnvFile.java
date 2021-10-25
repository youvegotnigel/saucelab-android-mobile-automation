package com.saucelabs.mobile.ui.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Properties;

public class CreateEnvFile {

    Properties properties = new Properties();
    Properties config = new Properties();
    //Properties config;
    public static final Logger log = LogManager.getLogger(CreateEnvFile.class.getName());

    public void createFile() {

        LoadConfigProperty();
        //config = new Properties();

        properties.setProperty("Platform", "Android");
        properties.setProperty("OS Version", "10.0");
        properties.setProperty("AUT", "Sauce Labs Mobile App");
        FileWriter writer = null;
        try {
            writer = new FileWriter("allure-results\\environment.properties");
            properties.store(writer, "youvegotnigel");
        } catch (IOException ex) {
            ex.printStackTrace();
            log.error(ex.getMessage());
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public void LoadConfigProperty(){
        try {
            //config = new Properties();
            FileInputStream ip = new FileInputStream(
                    System.getProperty("user.dir") + "/src/test/java/resources/config/config.properties");
            config.load(ip);
            log.info("Properties file loaded successfully");
        }catch (Exception e){
            log.error("Configuration Properties file not found." + Arrays.toString(e.getStackTrace()));
        }

    }
}
