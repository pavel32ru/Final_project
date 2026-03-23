package ru.praktikum.qa.context;

import ru.praktikum.qa.data.TestListing;
import ru.praktikum.qa.data.TestUser;

public class ScenarioContext {
    private TestUser currentUser;
    private TestUser duplicateUser;
    private TestListing currentListing;

    public TestUser getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(TestUser currentUser) {
        this.currentUser = currentUser;
    }

    public TestUser getDuplicateUser() {
        return duplicateUser;
    }

    public void setDuplicateUser(TestUser duplicateUser) {
        this.duplicateUser = duplicateUser;
    }

    public TestListing getCurrentListing() {
        return currentListing;
    }

    public void setCurrentListing(TestListing currentListing) {
        this.currentListing = currentListing;
    }
}
