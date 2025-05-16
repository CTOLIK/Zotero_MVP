package com.example.zoteromvp;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class JavaClass {
    OkHttpClient client = new OkHttpClient();
    RequestBody requestBody = new MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart("username", "kekich")
            .addFormDataPart("password", "4755354561Aa!")
            .build();

    Request request = new Request.Builder()
            .url("https://www.zotero.org/user/login/")
            .post(requestBody)
            .build();


}
