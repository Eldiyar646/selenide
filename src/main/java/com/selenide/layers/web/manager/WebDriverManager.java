package com.selenide.layers.web.manager;

import static com.codeborne.selenide.Configuration.browser;
import static com.codeborne.selenide.Configuration.headless;

public class WebDriverManager {

    public static void configureBasicWebDriver(){

        browser = "chrome";
        headless = false;

    }
}
