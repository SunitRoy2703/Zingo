package com.sunit.zingo.Models;

import com.google.gson.annotations.SerializedName;

public class Category {

    @SerializedName("category_id")
    private int category_id;

    @SerializedName("name")
    private String name;

    @SerializedName("description")
    private String description;

    @SerializedName("category_image")
    private String category_image;

    @SerializedName("created")
    private String created;

    @SerializedName("status")
    private String status;

    @SerializedName("parent_id")
    private int parent_id;

    public Category(int category_id, String name, String description, String category_image, String created, String status, int parent_id) {
        this.category_id = category_id;
        this.name = name;
        this.description = description;
        this.category_image = category_image;
        this.created = created;
        this.status = status;
        this.parent_id = parent_id;
    }

    public int getCategory_id() {
        return category_id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getCategory_image() {
        return category_image;
    }

    public String getCreated() {
        return created;
    }

    public String getStatus() {
        return status;
    }

    public int getParent_id() {
        return parent_id;
    }
}
