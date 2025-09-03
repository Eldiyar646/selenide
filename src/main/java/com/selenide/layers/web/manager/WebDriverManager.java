package com.selenide.layers.web.manager;

import com.codeborne.selenide.Configuration;


public class WebDriverManager {

    public static void configureBasicWebDriver() {
        Configuration.browser = "chrome";
        Configuration.headless = Boolean.parseBoolean(System.getProperty("HEADLESS", "true"));
    }
}
