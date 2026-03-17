package ru.praktikum.qa.pages;

import com.codeborne.selenide.Condition;

import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.open;

public class ProfilePage extends BasePage {
    private static final String TITLE_PROFILE = "\u041C\u043E\u0439 \u043F\u0440\u043E\u0444\u0438\u043B\u044C";
    private static final String BLOCK_MY_LISTINGS = "\u041C\u043E\u0438 \u043E\u0431\u044A\u044F\u0432\u043B\u0435\u043D\u0438\u044F";

    public ProfilePage openPage() {
        open("/profile");
        titleByText(TITLE_PROFILE).shouldBe(Condition.visible);
        return this;
    }

    public void openOwnListing(String title) {
        $x("//*[normalize-space()='" + BLOCK_MY_LISTINGS + "']/following::*[normalize-space()='" + title + "'][1]")
                .shouldBe(Condition.visible)
                .click();
    }

    public void shouldContainListing(String title) {
        visibleText(title).shouldBe(Condition.visible);
    }

    public void shouldNotContainListing(String title) {
        $x("//*[normalize-space()='" + title + "']").shouldNotBe(Condition.exist);
    }
}