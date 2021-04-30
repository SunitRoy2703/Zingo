package com.sunit.zingo.Models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProductList {

    @SerializedName("status")
    private String status;

    @SerializedName("data")
    private List<Product> data;

    public String getStatus() {
        return status;
    }

    public List<Product> getData() {
        return data;
    }
}
