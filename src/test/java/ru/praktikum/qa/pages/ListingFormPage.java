package ru.praktikum.qa.pages;

import com.codeborne.selenide.Condition;
import ru.praktikum.qa.data.TestListing;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.codeborne.selenide.Selenide.executeAsyncJavaScript;
import static com.codeborne.selenide.Selenide.executeJavaScript;
import static com.codeborne.selenide.Selenide.open;

public class ListingFormPage extends BasePage {
    private static final String TITLE_CREATE = "\u041D\u043E\u0432\u043E\u0435 \u043E\u0431\u044A\u044F\u0432\u043B\u0435\u043D\u0438\u0435";
    private static final String TITLE_EDIT = "\u0420\u0435\u0434\u0430\u043A\u0442\u0438\u0440\u043E\u0432\u0430\u0442\u044C \u043E\u0431\u044A\u044F\u0432\u043B\u0435\u043D\u0438\u0435";
    private static final String BUTTON_SAVE = "\u0421\u043E\u0445\u0440\u0430\u043D\u0438\u0442\u044C \u0438\u0437\u043C\u0435\u043D\u0435\u043D\u0438\u044F";
    private static final String PLACEHOLDER_NAME = "\u041D\u0430\u0437\u0432\u0430\u043D\u0438\u0435";
    private static final String PLACEHOLDER_DESCRIPTION = "\u041E\u043F\u0438\u0441\u0430\u043D\u0438\u0435 \u0442\u043E\u0432\u0430\u0440\u0430";
    private static final String PLACEHOLDER_PRICE = "\u0421\u0442\u043E\u0438\u043C\u043E\u0441\u0442\u044C";
    private static final String CATEGORY_NAME = "category";
    private static final String CITY_NAME = "city";
    private static final String CONDITION_NEW = "\u041D\u043E\u0432\u044B\u0439";
    private static final String CATEGORY_DEFAULT = "\u0410\u0432\u0442\u043E";
    private static final String CITY_DEFAULT = "\u041C\u043E\u0441\u043A\u0432\u0430";
    private static final String IMAGE_1_NAME = "img1";
    private static final String TEST_IMAGE_PATH = "src/test/resources/test-image.png";
    private static final String TEST_IMAGE_BASE64 = "iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAQAAAC1HAwCAAAAC0lEQVR42mP8/x8AAwMCAO+a6e0AAAAASUVORK5CYII=";

    public ListingFormPage openCreatePage() {
        open("/create-lisiting");
        titleByText(TITLE_CREATE).shouldBe(Condition.visible);
        return this;
    }

    public ListingFormPage openEditPage(Integer listingId) {
        open("/edit-listing/" + listingId);
        titleByText(TITLE_EDIT).shouldBe(Condition.visible);
        return this;
    }

    public ListingFormPage fillCreateForm(TestListing listing) {
        uploadMainImage();
        inputByPlaceholder(PLACEHOLDER_NAME).setValue(listing.getTitle());
        selectDropdownOption(CATEGORY_NAME, CATEGORY_DEFAULT);
        selectConditionNew();
        selectDropdownOption(CITY_NAME, CITY_DEFAULT);
        textareaByPlaceholder(PLACEHOLDER_DESCRIPTION).setValue(listing.getDescription());
        inputByPlaceholder(PLACEHOLDER_PRICE).setValue(String.valueOf(listing.getPrice()));
        return this;
    }

    public ListingFormPage fillEditForm(TestListing listing) {
        inputByPlaceholder(PLACEHOLDER_NAME).clear();
        inputByPlaceholder(PLACEHOLDER_NAME).setValue(listing.getUpdatedTitle());
        textareaByPlaceholder(PLACEHOLDER_DESCRIPTION).clear();
        textareaByPlaceholder(PLACEHOLDER_DESCRIPTION).setValue(listing.getUpdatedDescription());
        inputByPlaceholder(PLACEHOLDER_PRICE).clear();
        inputByPlaceholder(PLACEHOLDER_PRICE).setValue(String.valueOf(listing.getUpdatedPrice()));
        return this;
    }

    public void submitEditForm() {
        buttonByText(BUTTON_SAVE).shouldBe(Condition.visible).click();
    }

    public Integer createListingViaBrowserApi(TestListing listing, String token) {
        Object resultObject = executeAsyncJavaScript(
                "const title = arguments[0];" +
                        "const description = arguments[1];" +
                        "const price = arguments[2];" +
                        "const imageBase64 = arguments[3];" +
                        "const token = arguments[4];" +
                        "const callback = arguments[arguments.length - 1];" +
                        "const fd = new FormData();" +
                        "fd.append('name', title);" +
                        "fd.append('category', '\u0410\u0432\u0442\u043e');" +
                        "fd.append('condition', '\u041d\u043e\u0432\u044b\u0439');" +
                        "fd.append('city', '\u041c\u043e\u0441\u043a\u0432\u0430');" +
                        "fd.append('description', description);" +
                        "fd.append('price', String(price));" +
                        "const bytes = Uint8Array.from(atob(imageBase64), c => c.charCodeAt(0));" +
                        "const blob = new Blob([bytes], { type: 'image/png' });" +
                        "fd.append('images', blob, 'test-image.png');" +
                        "fetch(window.location.origin + '/api/create-listing', {" +
                        "  method: 'POST'," +
                        "  headers: { Authorization: 'Bearer ' + token }," +
                        "  body: fd" +
                        "}).then(async response => {" +
                        "  callback(JSON.stringify({ status: response.status, body: await response.text() }));" +
                        "}).catch(error => callback(JSON.stringify({ status: 0, body: String(error) })));",
                listing.getTitle(), listing.getDescription(), listing.getPrice(), TEST_IMAGE_BASE64, token);

        String result = resultObject == null ? "" : resultObject.toString();
        int status = extractStatus(result);
        if (status != 200 && status != 201) {
            throw new AssertionError("Create listing failed: " + result);
        }

        Integer id = extractFirstId(result);
        if (id == null) {
            throw new AssertionError("Create listing response has no id: " + result);
        }
        return id;
    }

    public void updateListingViaBrowserApi(TestListing listing, String token) {
        Object resultObject = executeAsyncJavaScript(
                "const offerId = arguments[0];" +
                        "const title = arguments[1];" +
                        "const description = arguments[2];" +
                        "const price = arguments[3];" +
                        "const token = arguments[4];" +
                        "const callback = arguments[arguments.length - 1];" +
                        "const fd = new FormData();" +
                        "fd.append('name', title);" +
                        "fd.append('category', '\u0410\u0432\u0442\u043e');" +
                        "fd.append('condition', '\u041d\u043e\u0432\u044b\u0439');" +
                        "fd.append('city', '\u041c\u043e\u0441\u043a\u0432\u0430');" +
                        "fd.append('description', description);" +
                        "fd.append('price', String(price));" +
                        "fd.append('img1', 'null');" +
                        "fd.append('img2', 'null');" +
                        "fd.append('img3', 'null');" +
                        "fetch(window.location.origin + '/api/update-offer/' + offerId, {" +
                        "  method: 'PATCH'," +
                        "  headers: { Authorization: 'Bearer ' + token }," +
                        "  body: fd" +
                        "}).then(async response => {" +
                        "  callback(JSON.stringify({ status: response.status, body: await response.text() }));" +
                        "}).catch(error => callback(JSON.stringify({ status: 0, body: String(error) })));",
                listing.getId(), listing.getUpdatedTitle(), listing.getUpdatedDescription(), listing.getUpdatedPrice(), token);

        String result = resultObject == null ? "" : resultObject.toString();
        int status = extractStatus(result);
        if (status != 200 && status != 201) {
            throw new AssertionError("Update listing failed: " + result);
        }
    }

    private void uploadMainImage() {
        fileInputByName(IMAGE_1_NAME).uploadFile(new File(TEST_IMAGE_PATH));
    }

    private void selectDropdownOption(String inputName, String optionText) {
        dropdownToggleByName(inputName).shouldBe(Condition.visible).click();
        dropdownOptionByText(optionText).shouldBe(Condition.visible).click();
    }

    private void selectConditionNew() {
        executeJavaScript("arguments[0].click();", radioByValue(CONDITION_NEW));
    }

    private int extractStatus(String result) {
        Matcher matcher = Pattern.compile("\\\"status\\\"\\s*:\\s*(\\d+)").matcher(result);
        if (!matcher.find()) {
            throw new AssertionError("Response has no status: " + result);
        }
        return Integer.parseInt(matcher.group(1));
    }

    private Integer extractFirstId(String result) {
        String normalized = result.replace("\\\"", "\"");
        Matcher matcher = Pattern.compile("\"id\"\\s*:\\s*(\\d+)").matcher(normalized);
        if (!matcher.find()) {
            return null;
        }
        return Integer.parseInt(matcher.group(1));
    }
}