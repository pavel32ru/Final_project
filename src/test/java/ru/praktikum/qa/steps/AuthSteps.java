package ru.praktikum.qa.steps;

import io.cucumber.java.ru.Дано;
import io.cucumber.java.ru.И;
import io.cucumber.java.ru.Тогда;
import ru.praktikum.qa.api.UserApiClient;
import ru.praktikum.qa.context.ScenarioContext;
import ru.praktikum.qa.data.TestDataFactory;
import ru.praktikum.qa.data.TestUser;
import ru.praktikum.qa.pages.AuthPage;
import ru.praktikum.qa.pages.HomePage;

public class AuthSteps {
    private final ScenarioContext context;
    private final UserApiClient userApiClient;
    private final AuthPage authPage;
    private final HomePage homePage;

    public AuthSteps(ScenarioContext context) {
        this.context = context;
        this.userApiClient = new UserApiClient();
        this.authPage = new AuthPage();
        this.homePage = new HomePage();
    }

    @Дано("через API создан пользователь для авторизации")
    public void createUserViaApiForAuth() {
        TestUser user = userApiClient.register(TestDataFactory.randomUser());
        context.setCurrentUser(user);
    }

    @И("пользователь открывает страницу логина")
    public void openLoginPage() {
        authPage.openPage();
    }

    @И("пользователь авторизуется валидными данными")
    public void loginWithValidCredentials() {
        TestUser user = context.getCurrentUser();
        authPage.login(user.getEmail(), user.getPassword());
    }

    @Тогда("авторизация проходит успешно")
    public void authShouldSucceed() {
        homePage.shouldShowLoggedInCta();
    }
}
