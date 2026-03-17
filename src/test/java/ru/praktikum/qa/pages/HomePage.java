package ru.praktikum.qa.pages;

import com.codeborne.selenide.Condition;
import ru.praktikum.qa.data.TestListing;

import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.open;

public class HomePage extends BasePage {
    private static final String SEARCH_PLACEHOLDER = "\u042F \u0445\u043E\u0447\u0443 \u043A\u0443\u043F\u0438\u0442\u044C...";

    public HomePage openPage() {
        open("/");
        return this;
    }

    public void shouldShowLoggedInCta() {
        buttonByText("\u0420\u0430\u0437\u043C\u0435\u0441\u0442\u0438\u0442\u044C \u043E\u0431\u044A\u044F\u0432\u043B\u0435\u043D\u0438\u0435").shouldBe(Condition.visible);
    }

    public void searchByTitle(String title) {
        inputByPlaceholder(SEARCH_PLACEHOLDER).setValue(title);
        buttonByText("\u041F\u0440\u0438\u043C\u0435\u043D\u0438\u0442\u044C").shouldBe(Condition.visible).click();
    }

    public void openListingByTitle(String title) {
        $x("//*[contains(@class,\u0027card\u0027)]//*[normalize-space()=\"" + title + "\"]").shouldBe(Condition.visible).click();
    }

    public void shouldContainListing(TestListing listing) {
        visibleText(listing.getTitle()).shouldBe(Condition.visible);
    }

    public void shouldContainListing(String title) {
        visibleText(title).shouldBe(Condition.visible);
    }

    public void shouldNotContainListing(String title) {
        visibleText(title).shouldNotBe(Condition.exist);
    }
}
