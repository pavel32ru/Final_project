package ru.praktikum.qa.steps;

import io.cucumber.java.ru.Дано;
import io.cucumber.java.ru.И;
import io.cucumber.java.ru.Тогда;
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

    @Дано("через API создан и авторизован пользователь для работы с объявлениями")
    public void createAndAuthorizeUserForListings() {
        TestUser user = userApiClient.register(TestDataFactory.randomUser());
        context.setCurrentUser(user);
        context.setCurrentListing(TestDataFactory.randomListing());
        authPage.openPage().login(user.getEmail(), user.getPassword());
        homePage.shouldShowLoggedInCta();
    }

    @И("пользователь открывает форму создания объявления")
    public void openCreateListingForm() {
        listingFormPage.openCreatePage();
    }

    @И("пользователь публикует новое объявление")
    public void publishListing() {
        listingFormPage.fillCreateForm(context.getCurrentListing());
        Integer listingId = listingFormPage.createListingViaBrowserApi(context.getCurrentListing(), context.getCurrentUser().getToken());
        context.getCurrentListing().setId(listingId);
    }

    @Тогда("объявление успешно создано")
    public void listingCreated() {
        if (context.getCurrentListing().getId() == null) {
            throw new AssertionError("Listing id was not created");
        }
        listingDetailsPage.openPage(context.getCurrentListing().getId());
    }

    @Дано("у авторизованного пользователя есть собственное объявление")
    public void userHasOwnListing() {
        createAndAuthorizeUserForListings();
        openCreateListingForm();
        publishListing();
    }

    @И("пользователь открывает своё объявление в профиле")
    public void openOwnListingInProfile() {
        listingDetailsPage.openPage(context.getCurrentListing().getId());
    }

    @И("пользователь редактирует объявление")
    public void editListing() {
        listingFormPage.updateListingViaBrowserApi(context.getCurrentListing(), context.getCurrentUser().getToken());
    }

    @Тогда("объявление успешно отредактировано")
    public void listingEdited() {
        if (context.getCurrentListing().getId() == null) {
            throw new AssertionError("Listing id is missing for edited listing");
        }
        listingDetailsPage.openPage(context.getCurrentListing().getId());
    }

    @И("пользователь удаляет своё объявление")
    public void deleteOwnListing() {
        listingDetailsPage.deleteListingViaBrowserApi(context.getCurrentListing().getId(), context.getCurrentUser().getToken());
    }

    @Тогда("объявление успешно удалено")
    public void listingDeleted() {
        profilePage.openPage();
        profilePage.shouldNotContainListing(context.getCurrentListing().getUpdatedTitle());
        profilePage.shouldNotContainListing(context.getCurrentListing().getTitle());
    }
}