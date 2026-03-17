package ru.praktikum.qa.steps;

import io.cucumber.java.ru.Дано;
import io.cucumber.java.ru.И;
import io.cucumber.java.ru.Тогда;
import ru.praktikum.qa.api.UserApiClient;
import ru.praktikum.qa.context.ScenarioContext;
import ru.praktikum.qa.data.TestDataFactory;
import ru.praktikum.qa.data.TestUser;
import ru.praktikum.qa.pages.HomePage;
import ru.praktikum.qa.pages.RegistrationPage;

public class RegistrationSteps {
    private final ScenarioContext context;
    private final UserApiClient userApiClient;
    private final RegistrationPage registrationPage;
    private final HomePage homePage;

    public RegistrationSteps(ScenarioContext context) {
        this.context = context;
        this.userApiClient = new UserApiClient();
        this.registrationPage = new RegistrationPage();
        this.homePage = new HomePage();
    }

    @Дано("подготовлен новый пользователь для регистрации")
    public void prepareNewUserForRegistration() {
        context.setCurrentUser(TestDataFactory.randomUser());
    }

    @Дано("существует зарегистрированный пользователь")
    public void prepareExistingUser() {
        TestUser user = userApiClient.register(TestDataFactory.randomUser());
        context.setDuplicateUser(user);
    }

    @И("пользователь открывает страницу регистрации")
    public void openRegistrationPage() {
        registrationPage.openPage();
    }

    @И("пользователь отправляет форму регистрации с новыми данными")
    public void submitRegistrationForm() {
        TestUser user = context.getCurrentUser();
        registrationPage.register(user.getEmail(), user.getPassword());
    }

    @И("пользователь пытается зарегистрироваться повторно с тем же email")
    public void submitDuplicateRegistrationForm() {
        TestUser user = context.getDuplicateUser();
        registrationPage.register(user.getEmail(), user.getPassword());
    }

    @Тогда("регистрация проходит успешно")
    public void registrationShouldSucceed() {
        homePage.shouldShowLoggedInCta();
    }

    @Тогда("повторная регистрация не выполняется")
    public void duplicateRegistrationShouldFail() {
        registrationPage.shouldStayOnRegistrationForm();
    }
}
