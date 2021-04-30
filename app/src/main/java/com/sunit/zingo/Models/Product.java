package com.sunit.zingo.Models;

import com.google.gson.annotations.SerializedName;

public class Product {

    @SerializedName("product_id")
    private int product_id;

    @SerializedName("model")
    private String model;

    @SerializedName("product_code")
    private String product_code;

    @SerializedName("price")
    private String price;

    @SerializedName("measurement")
    private String measurement;

    @SerializedName("category_id")
    private int category_id;

    @SerializedName("image1")
    private String image1;

    @SerializedName("image2")
    private String image2;

    @SerializedName("assign_product")
    private String assign_product;

    public Product(int product_id, String model, String product_code, String price, String measurement, int category_id, String image1, String image2, String assign_product) {
        this.product_id = product_id;
        this.model = model;
        this.product_code = product_code;
        this.price = price;
        this.measurement = measurement;
        this.category_id = category_id;
        this.image1 = image1;
        this.image2 = image2;
        this.assign_product = assign_product;
    }

    public int getProduct_id() {
        return product_id;
    }

    public String getModel() {
        return model;
    }

    public String getProduct_code() {
        return product_code;
    }

    public String getPrice() {
        return price;
    }

    public String getMeasurement() {
        return measurement;
    }

    public int getCategory_id() {
        return category_id;
    }

    public String getImage1() {
        return image1;
    }

    public String getImage2() {
        return image2;
    }

    public String getAssign_product() {
        return assign_product;
    }
}
