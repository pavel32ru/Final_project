package ru.praktikum.qa.hooks;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.qameta.allure.selenide.AllureSelenide;
import ru.praktikum.qa.config.TestConfig;
import static com.codeborne.selenide.WebDriverRunner.hasWebDriverStarted;
public class Hooks {
    private static boolean listenerRegistered;
    @Before
    public void setUp() {
        TestConfig.configureUi();
        if (!listenerRegistered) {
            SelenideLogger.addListener("allure", new AllureSelenide().screenshots(true).savePageSource(false));
            listenerRegistered = true;
        }
        if (hasWebDriverStarted()) {
            Selenide.clearBrowserCookies();
            Selenide.clearBrowserLocalStorage();
        }
    }
    @After
    public void tearDown() {
        Selenide.closeWebDriver();
    }
}