package com.example.zoteromvp;

import com.google.gson.Gson;

import org.junit.Test;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.client.Response;
import retrofit.converter.GsonConverter;
import rx.observables.BlockingObservable;

public class GetAccess {
    // Подготовка к работе с Zotero
    ZoteroService zoteroService;
    static final int USER_ID = 743083;
    static final String ACCESS_KEY = "vLLtTQU1tcWCPb3Grsl6GzWi";

    //Тест 1: Установление связи с Zotero и получение ответа. В случае неверных данных будет выдана ошибка retrofit "403 Forbidden", что означает отказ в доступе.
    @Test
    public void exceptionTesting() throws Exception {
        RequestInterceptor authorizingInterceptor = new RequestInterceptor() {
            @Override
            public void intercept(RequestFacade request) {
                request.addHeader(HttpHeaders.AUTHORIZATION, HttpHeaders.AUTHORIZATION_BEARER_X + ACCESS_KEY);
                request.addHeader(HttpHeaders.ZOTERO_API_VERSION, "3");
            }
        };

        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint("https://api.zotero.org")
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setRequestInterceptor(authorizingInterceptor)
                .setConverter(new GsonConverter(new Gson()))
                .build();

        zoteroService = adapter.create(ZoteroService.class);
        rx.Observable<Response> Resp_All = zoteroService.getItemsNotAsAList(LibraryType.USER, USER_ID, null, null);
        Response tes_all = BlockingObservable.from(Resp_All).last();
    }
}






