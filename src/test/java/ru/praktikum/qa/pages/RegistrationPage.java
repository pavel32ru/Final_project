package ru.praktikum.qa.pages;

import com.codeborne.selenide.Condition;

import static com.codeborne.selenide.Selenide.open;

public class RegistrationPage extends BasePage {
    public RegistrationPage openPage() {
        open("/registration");
        titleByText("Зарегистрироваться").shouldBe(Condition.visible);
        return this;
    }

    public RegistrationPage register(String email, String password) {
        inputByPlaceholder("Введите Email").setValue(email);
        inputByPlaceholder("Пароль").setValue(password);
        inputByPlaceholder("Повторите пароль").setValue(password);
        buttonByText("Создать аккаунт").click();
        return this;
    }

    public void shouldStayOnRegistrationForm() {
        titleByText("Зарегистрироваться").shouldBe(Condition.visible);
        buttonByText("Создать аккаунт").shouldBe(Condition.visible);
    }
}
