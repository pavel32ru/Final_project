package ru.praktikum.qa.data;

public class TestListing {
    private Integer id;
    private final String title;
    private final String description;
    private final int price;
    private final String updatedTitle;
    private final String updatedDescription;
    private final int updatedPrice;

    public TestListing(String title, String description, int price) {
        this.title = title;
        this.description = description;
        this.price = price;
        this.updatedTitle = title + " edited";
        this.updatedDescription = description + " edited";
        this.updatedPrice = price + 111;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getPrice() {
        return price;
    }

    public String getUpdatedTitle() {
        return updatedTitle;
    }

    public String getUpdatedDescription() {
        return updatedDescription;
    }

    public int getUpdatedPrice() {
        return updatedPrice;
    }
}
