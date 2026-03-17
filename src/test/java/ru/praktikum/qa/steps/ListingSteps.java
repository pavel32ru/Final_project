package ru.praktikum.qa.steps;

import io.cucumber.java.ru.\u0414\u0430\u043d\u043e;
import io.cucumber.java.ru.\u0418;
import io.cucumber.java.ru.\u0422\u043e\u0433\u0434\u0430;
import ru.praktikum.qa.api.UserApiClient;
import ru.praktikum.qa.context.ScenarioContext;
import ru.praktikum.qa.data.TestDataFactory;
import ru.praktikum.qa.data.TestUser;
import ru.praktikum.qa.pages.AuthPage;
import ru.praktikum.qa.pages.HomePage;
import ru.praktikum.qa.pages.ListingDetailsPage;
import ru.praktikum.qa.pages.ListingFormPage;
import ru.praktikum.qa.pages.ProfilePage;

public class ListingSteps {
    private final ScenarioContext context;
    private final UserApiClient userApiClient;
    private final AuthPage authPage;
    private final ListingFormPage listingFormPage;
    private final HomePage homePage;
    private final ProfilePage profilePage;
    private final ListingDetailsPage listingDetailsPage;

    public ListingSteps(ScenarioContext context) {
        this.context = context;
        this.userApiClient = new UserApiClient();
        this.authPage = new AuthPage();
        this.listingFormPage = new ListingFormPage();
        this.homePage = new HomePage();
        this.profilePage = new ProfilePage();
        this.listingDetailsPage = new ListingDetailsPage();
    }

    @\u0414\u0430\u043d\u043e("\u0447\u0435\u0440\u0435\u0437 API \u0441\u043e\u0437\u0434\u0430\u043d \u0438 \u0430\u0432\u0442\u043e\u0440\u0438\u0437\u043e\u0432\u0430\u043d \u043f\u043e\u043b\u044c\u0437\u043e\u0432\u0430\u0442\u0435\u043b\u044c \u0434\u043b\u044f \u0440\u0430\u0431\u043e\u0442\u044b \u0441 \u043e\u0431\u044a\u044f\u0432\u043b\u0435\u043d\u0438\u044f\u043c\u0438")
    public void createAndAuthorizeUserForListings() {
        TestUser user = userApiClient.register(TestDataFactory.randomUser());
        context.setCurrentUser(user);
        context.setCurrentListing(TestDataFactory.randomListing());
        authPage.openPage().login(user.getEmail(), user.getPassword());
        homePage.shouldShowLoggedInCta();
    }

    @\u0418("\u043f\u043e\u043b\u044c\u0437\u043e\u0432\u0430\u0442\u0435\u043b\u044c \u043e\u0442\u043a\u0440\u044b\u0432\u0430\u0435\u0442 \u0444\u043e\u0440\u043c\u0443 \u0441\u043e\u0437\u0434\u0430\u043d\u0438\u044f \u043e\u0431\u044a\u044f\u0432\u043b\u0435\u043d\u0438\u044f")
    public void openCreateListingForm() {
        listingFormPage.openCreatePage();
    }

    @\u0418("\u043f\u043e\u043b\u044c\u0437\u043e\u0432\u0430\u0442\u0435\u043b\u044c \u043f\u0443\u0431\u043b\u0438\u043a\u0443\u0435\u0442 \u043d\u043e\u0432\u043e\u0435 \u043e\u0431\u044a\u044f\u0432\u043b\u0435\u043d\u0438\u0435")
    public void publishListing() {
        listingFormPage.fillCreateForm(context.getCurrentListing());
        Integer listingId = listingFormPage.createListingViaBrowserApi(context.getCurrentListing(), context.getCurrentUser().getToken());
        context.getCurrentListing().setId(listingId);
    }

    @\u0422\u043e\u0433\u0434\u0430("\u043e\u0431\u044a\u044f\u0432\u043b\u0435\u043d\u0438\u0435 \u0443\u0441\u043f\u0435\u0448\u043d\u043e \u0441\u043e\u0437\u0434\u0430\u043d\u043e")
    public void listingCreated() {
        if (context.getCurrentListing().getId() == null) {
            throw new AssertionError("Listing id was not created");
        }
        listingDetailsPage.openPage(context.getCurrentListing().getId());
    }

    @\u0414\u0430\u043d\u043e("\u0443 \u0430\u0432\u0442\u043e\u0440\u0438\u0437\u043e\u0432\u0430\u043d\u043d\u043e\u0433\u043e \u043f\u043e\u043b\u044c\u0437\u043e\u0432\u0430\u0442\u0435\u043b\u044f \u0435\u0441\u0442\u044c \u0441\u043e\u0431\u0441\u0442\u0432\u0435\u043d\u043d\u043e\u0435 \u043e\u0431\u044a\u044f\u0432\u043b\u0435\u043d\u0438\u0435")
    public void userHasOwnListing() {
        createAndAuthorizeUserForListings();
        openCreateListingForm();
        publishListing();
    }

    @\u0418("\u043f\u043e\u043b\u044c\u0437\u043e\u0432\u0430\u0442\u0435\u043b\u044c \u043e\u0442\u043a\u0440\u044b\u0432\u0430\u0435\u0442 \u0441\u0432\u043e\u0451 \u043e\u0431\u044a\u044f\u0432\u043b\u0435\u043d\u0438\u0435 \u0432 \u043f\u0440\u043e\u0444\u0438\u043b\u0435")
    public void openOwnListingInProfile() {
        listingDetailsPage.openPage(context.getCurrentListing().getId());
    }

    @\u0418("\u043f\u043e\u043b\u044c\u0437\u043e\u0432\u0430\u0442\u0435\u043b\u044c \u0440\u0435\u0434\u0430\u043a\u0442\u0438\u0440\u0443\u0435\u0442 \u043e\u0431\u044a\u044f\u0432\u043b\u0435\u043d\u0438\u0435")
    public void editListing() {
        listingFormPage.updateListingViaBrowserApi(context.getCurrentListing(), context.getCurrentUser().getToken());
    }

    @\u0422\u043e\u0433\u0434\u0430("\u043e\u0431\u044a\u044f\u0432\u043b\u0435\u043d\u0438\u0435 \u0443\u0441\u043f\u0435\u0448\u043d\u043e \u043e\u0442\u0440\u0435\u0434\u0430\u043a\u0442\u0438\u0440\u043e\u0432\u0430\u043d\u043e")
    public void listingEdited() {
        if (context.getCurrentListing().getId() == null) {
            throw new AssertionError("Listing id is missing for edited listing");
        }
        listingDetailsPage.openPage(context.getCurrentListing().getId());
    }

    @\u0418("\u043f\u043e\u043b\u044c\u0437\u043e\u0432\u0430\u0442\u0435\u043b\u044c \u0443\u0434\u0430\u043b\u044f\u0435\u0442 \u0441\u0432\u043e\u0451 \u043e\u0431\u044a\u044f\u0432\u043b\u0435\u043d\u0438\u0435")
    public void deleteOwnListing() {
        listingDetailsPage.deleteListingViaBrowserApi(context.getCurrentListing().getId(), context.getCurrentUser().getToken());
    }

    @\u0422\u043e\u0433\u0434\u0430("\u043e\u0431\u044a\u044f\u0432\u043b\u0435\u043d\u0438\u0435 \u0443\u0441\u043f\u0435\u0448\u043d\u043e \u0443\u0434\u0430\u043b\u0435\u043d\u043e")
    public void listingDeleted() {
        profilePage.openPage();
        profilePage.shouldNotContainListing(context.getCurrentListing().getUpdatedTitle());
        profilePage.shouldNotContainListing(context.getCurrentListing().getTitle());
    }
}