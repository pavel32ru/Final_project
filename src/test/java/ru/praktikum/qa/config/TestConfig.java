package ru.praktikum.qa.config;
import com.codeborne.selenide.Configuration;
public final class TestConfig {
    public static final String BASE_URL = propertyOrDefault("base.url", "https://qa-desk.stand.praktikum-services.ru");
    public static final String API_URL = propertyOrDefault("api.url", BASE_URL + "/api");
    private TestConfig() {
    }
    public static void configureUi() {
        Configuration.baseUrl = BASE_URL;
        Configuration.browser = propertyOrDefault("browser", "chrome");
        Configuration.browserSize = "1920x1080";
        Configuration.timeout = 10000;
        Configuration.pageLoadTimeout = 20000;
        Configuration.headless = Boolean.parseBoolean(propertyOrDefault("headless", "false"));
        Configuration.savePageSource = false;
        Configuration.reportsFolder = "target/selenide-reports";
    }
    private static String propertyOrDefault(String key, String defaultValue) {
        String value = System.getProperty(key);
        return value == null || value.isBlank() ? defaultValue : value;
    }
}