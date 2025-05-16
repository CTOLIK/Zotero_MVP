package com.example.zoteromvp;

import static org.junit.Assert.assertNotNull;

import com.google.gson.Gson;

import org.junit.Before;
import org.junit.Test;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.client.Header;
import retrofit.client.Response;
import retrofit.converter.GsonConverter;
import rx.Observable;
import rx.functions.Func1;
import rx.observables.BlockingObservable;

public class GetResponse {
    ZoteroService zoteroService;
    Long USER_ID = Long.parseLong("17116393");
    String ACCESS_KEY = "vrNSxghdhbqT82b1TPLsjVn5";

    // Подготовка к работе с Zotero

    @Before
    public void setConnection() throws Exception {
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
    }

    // Тест 1: Проверка получения ответа API Zotero
    @Test
    public void testGetResponse() throws Exception {
        assert(0 < zoteroService.getItemsNotAsAList(LibraryType.USER, USER_ID, null, null)
                .flatMap(new Func1<Response, Observable<Header>>() {
                    @Override
                    public Observable<Header> call(Response response) {
                        return Observable.from(response.getHeaders());
                    }
                })
                .filter(new Func1<Header, Boolean>() {
                    @Override
                    public Boolean call(Header header) {
                        return HttpHeaders.LAST_MODIFIED_VERSION.equalsIgnoreCase(header.getName());
                    }
                })
                .map(new Func1<Header, Integer>() {
                    @Override
                    public Integer call(Header header) {
                        return Integer.valueOf(header.getValue());
                    }
                })
                .toBlocking()
                .first());
    }

    // Тест 2: Проверка не нулевого response для пользовательских коллекций
    @Test
    public void testCollectionLoad() throws Exception {
        Observable<Response> Resp_lib = zoteroService.getCollections(LibraryType.USER, USER_ID,null);
        Response tes_li = BlockingObservable.from(Resp_lib).last();
        assertNotNull(tes_li);
    }

    // Тест 3: Проверка не нулевого response для всех публикаций
    @Test
    public void testAllItemsLoad() throws Exception {
        Observable<Response> Resp_lib = zoteroService.getItemsNotAsAList(LibraryType.USER, USER_ID,null, null);
        Response tes_all = BlockingObservable.from(Resp_lib).last();
        assertNotNull(tes_all);
    }

    // Тест 4: Проверка не нулевого response для публикаций в мусорке
    @Test
    public void testTrashLoad() throws Exception {
        Observable<Response> Resp_lib = zoteroService.getTrashItems(LibraryType.USER, USER_ID,null);
        Response tes_tr = BlockingObservable.from(Resp_lib).last();
        assertNotNull(tes_tr);
    }

}
