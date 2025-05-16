package com.example.zoteromvp.login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
import java.util.Map;

public class LoginRequestGroupsPart {
    @SerializedName("all")
    @Expose
    private Map<String, Boolean> all;

    public LoginRequestGroupsPart() {
        this.all = new HashMap<>();
        this.all.put("library", true);
        this.all.put("write", true);
    }

    public Map<String, Boolean> getAll() {
        return all;
    }

    public void setAll(Map<String, Boolean> all) {
        this.all = all;
    }
}
