package ru.praktikum.qa.pages;

import com.codeborne.selenide.Condition;

public class HeaderComponent extends BasePage {
    public void clickLogin() {
        buttonByText("Вход и регистрация").shouldBe(Condition.visible).click();
    }

    public void clickCreateListing() {
        buttonByText("Разместить объявление").shouldBe(Condition.visible).click();
    }
}
