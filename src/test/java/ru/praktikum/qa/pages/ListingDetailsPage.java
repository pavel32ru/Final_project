package ru.praktikum.qa.pages;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Condition;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.codeborne.selenide.Selenide.executeAsyncJavaScript;
import static com.codeborne.selenide.Selenide.open;

public class ListingDetailsPage extends BasePage {
    private static final String BUTTON_EDIT = "\u0420\u0435\u0434\u0430\u043A\u0442\u0438\u0440\u043E\u0432\u0430\u0442\u044C \u043E\u0431\u044A\u044F\u0432\u043B\u0435\u043D\u0438\u0435";
    private static final String BUTTON_DELETE = "\u0423\u0434\u0430\u043B\u0438\u0442\u044C";
    private static final String DELETE_CONFIRM_TITLE = "\u0412\u044B \u0443\u0432\u0435\u0440\u0435\u043D\u044B, \u0447\u0442\u043E \u0445\u043E\u0442\u0438\u0442\u0435 \u0443\u0434\u0430\u043B\u0438\u0442\u044C \u043E\u0431\u044A\u044F\u0432\u043B\u0435\u043D\u0438\u0435?";

    public ListingDetailsPage openPage(Integer listingId) {
        open("/listing/" + listingId);
        return this;
    }

    public void shouldShowTitle(String title) {
        visibleText(title).shouldBe(Condition.visible);
    }

    public void clickEdit() {
        buttonByText(BUTTON_EDIT).shouldBe(Condition.visible).click();
    }

    public void deleteListing() {
        buttonByText(BUTTON_DELETE).shouldBe(Condition.visible).click();
        titleByText(DELETE_CONFIRM_TITLE).shouldBe(Condition.visible);
        buttonsByText(BUTTON_DELETE).filter(Condition.visible)
                .shouldHave(CollectionCondition.sizeGreaterThanOrEqual(1))
                .last()
                .click();
    }

    public void deleteListingViaBrowserApi(Integer listingId, String token) {
        Object resultObject = executeAsyncJavaScript(
                "const listingId = arguments[0];" +
                        "const token = arguments[1];" +
                        "const callback = arguments[arguments.length - 1];" +
                        "fetch(window.location.origin + '/api/listings/' + listingId, {" +
                        "  method: 'DELETE'," +
                        "  headers: { Authorization: 'Bearer ' + token, 'Content-Type': 'application/json' }" +
                        "}).then(async response => {" +
                        "  callback(JSON.stringify({ status: response.status, body: await response.text() }));" +
                        "}).catch(error => callback(JSON.stringify({ status: 0, body: String(error) })));",
                listingId, token);
        String result = resultObject == null ? "" : resultObject.toString();
        int status = extractStatus(result);
        if (status != 200 && status != 201 && status != 204) {
            throw new AssertionError("Delete listing failed: " + result);
        }
    }

    private int extractStatus(String result) {
        Matcher matcher = Pattern.compile("\\\"status\\\"\\s*:\\s*(\\d+)").matcher(result);
        if (!matcher.find()) {
            throw new AssertionError("Response has no status: " + result);
        }
        return Integer.parseInt(matcher.group(1));
    }
}