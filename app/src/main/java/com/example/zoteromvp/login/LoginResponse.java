package com.example.zoteromvp.login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginResponse {
    @SerializedName("userID")
    @Expose
    private long userId;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("displayName")
    @Expose
    private String displayName;

    @SerializedName("key")
    @Expose
    private String key;

    public long getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getKey() {
        return key;
    }
}
