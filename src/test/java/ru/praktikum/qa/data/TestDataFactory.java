package ru.praktikum.qa.data;

import java.util.Locale;
import java.util.UUID;

public final class TestDataFactory {
    private TestDataFactory() {
    }

    public static TestUser randomUser() {
        String suffix = UUID.randomUUID().toString().substring(0, 8).toLowerCase(Locale.ROOT);
        return new TestUser(
                "autotest-" + suffix + "@example.com",
                "Password123!",
                "Codex " + suffix
        );
    }

    public static TestListing randomListing() {
        String suffix = UUID.randomUUID().toString().substring(0, 6).toLowerCase(Locale.ROOT);
        int price = Math.abs(suffix.hashCode() % 5000) + 5000;
        return new TestListing(
                "autotest-listing-" + suffix,
                "autotest-description-" + suffix,
                price
        );
    }
}
