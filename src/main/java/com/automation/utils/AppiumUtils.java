package com.automation.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class AppiumUtils {

    private static Properties properties;

    // Load config.properties file
    public static Properties getProperties() {
        if (properties == null) {
            properties = new Properties();
            try {
                FileInputStream fis = new FileInputStream("config/config.properties");
                properties.load(fis);
            } catch (IOException e) {
                throw new RuntimeException("Cannot load config.properties: " + e.getMessage());
            }
        }
        return properties;
    }

    // Get a specific property value
    public static String getProperty(String key) {
        return getProperties().getProperty(key);
    }
}
