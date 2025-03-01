package com.doolean.dooguard.client.gui;

public class Flag {
    private final String category;
    private final String name;
    private final String type;
    private final String description;

    public Flag(String category, String name, String type, String description) {
        this.category = category;
        this.name = name;
        this.type = type;
        this.description = description;
    }

    public Flag() {
        this.category = "";
        this.name = "";
        this.type = "";
        this.description = "";
    }

    public String getCategory() {
        return category;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }
}