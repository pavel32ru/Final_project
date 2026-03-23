package ru.praktikum.qa.pages;

import com.codeborne.selenide.Condition;

import static com.codeborne.selenide.Selenide.open;

public class AuthPage extends BasePage {
    public AuthPage openPage() {
        open("/login");
        titleByText("Войти").shouldBe(Condition.visible);
        return this;
    }

    public AuthPage login(String email, String password) {
        inputByPlaceholder("Введите Email").setValue(email);
        inputByPlaceholder("Пароль").setValue(password);
        buttonByText("Войти").click();
        return this;
    }

    public void shouldStayOnLoginForm() {
        titleByText("Войти").shouldBe(Condition.visible);
    }
}
