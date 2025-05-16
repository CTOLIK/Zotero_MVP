package com.example.zoteromvp.login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginRequestAccessPart {
    @SerializedName("user")
    @Expose
    private LoginRequestUserPart user = new LoginRequestUserPart();

    @SerializedName("groups")
    @Expose
    private LoginRequestGroupsPart groups = new LoginRequestGroupsPart();

    // Getters and Setters
    public LoginRequestUserPart getUser() {
        return user;
    }

    public void setUser(LoginRequestUserPart user) {
        this.user = user;
    }

    public LoginRequestGroupsPart getGroups() {
        return groups;
    }

    public void setGroups(LoginRequestGroupsPart groups) {
        this.groups = groups;
    }
}
