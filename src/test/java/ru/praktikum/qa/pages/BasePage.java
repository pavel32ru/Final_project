package ru.praktikum.qa.pages;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$$x;
import static com.codeborne.selenide.Selenide.$x;

public abstract class BasePage {
    protected SelenideElement buttonByText(String text) {
        return $x("//button[normalize-space()='" + text + "']");
    }

    protected SelenideElement inputByPlaceholder(String placeholder) {
        return $x("//input[@placeholder='" + placeholder + "']");
    }

    protected SelenideElement inputByName(String name) {
        return $x("//input[@name='" + name + "']");
    }

    protected SelenideElement fileInputByName(String name) {
        return $x("//input[@type='file' and @name='" + name + "']");
    }

    protected SelenideElement textareaByPlaceholder(String placeholder) {
        return $x("//textarea[@placeholder='" + placeholder + "']");
    }

    protected SelenideElement titleByText(String text) {
        return $x("//*[self::h1 or self::h2 or self::span][normalize-space()='" + text + "']");
    }

    protected SelenideElement visibleText(String text) {
        return $x("//*[normalize-space()='" + text + "']");
    }

    protected ElementsCollection buttonsByText(String text) {
        return $$x("//button[normalize-space()='" + text + "']");
    }

    protected SelenideElement dropdownToggleByName(String name) {
        return $x("//input[@name='" + name + "']/following-sibling::button[1]");
    }

    protected SelenideElement dropdownOptionByText(String text) {
        return $x("//button[.//span[normalize-space()='" + text + "']]");
    }

    protected SelenideElement radioByValue(String value) {
        return $x("//input[@type='radio' and @value='" + value + "']");
    }

    protected SelenideElement radioControlByValue(String value) {
        return $x("//label[normalize-space()='" + value + "']/preceding-sibling::div[1]");
    }

    protected void ensureAtLeastOneVisibleButton(String text) {
        buttonsByText(text).filter(Condition.visible).shouldHave(CollectionCondition.sizeGreaterThan(0));
    }
}