package com.sunit.zingo.Models;

import com.google.gson.annotations.SerializedName;

public class Vendor {

    @SerializedName("phone_number")
    private String phoneNo;

    @SerializedName("password")
    private String password;

    @SerializedName("player_id")
    private String playerId;

    public Vendor(String phoneNo, String password, String playerId) {
        this.phoneNo = phoneNo;
        this.password = password;
        this.playerId = playerId;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public String getPassword() {
        return password;
    }

    public String getPlayerId() {
        return playerId;
    }
}
