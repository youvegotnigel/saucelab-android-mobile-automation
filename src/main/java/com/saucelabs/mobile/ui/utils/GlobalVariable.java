package com.saucelabs.mobile.ui.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.util.Arrays;
import java.util.Properties;

/**
 * This class is used to handle static and non-static global variables
 */
public class GlobalVariable {

    public static Properties config;
    public static final Logger log = LogManager.getLogger(GlobalVariable.class.getName());

    public static final int WAIT_TIME = 10;
    public static final String _browserStackKey = "wEzkdnqVrQ58yhAsFsz7";


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
}
