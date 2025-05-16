package com.example.zoteromvp;

import static com.example.zoteromvp.GetAccess.ACCESS_KEY;
import static com.example.zoteromvp.GetAccess.USER_ID;
import static org.junit.Assert.assertTrue;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import java.util.ArrayList;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.client.Response;
import retrofit.converter.GsonConverter;
import retrofit.mime.TypedByteArray;
import rx.observables.BlockingObservable;

public class CheckFormatTitles {
    // Подготовка к работе с Zotero
    ZoteroService zoteroService;

    void setUp(String accesskey, int USER_ID) throws Exception {
        RequestInterceptor authorizingInterceptor = new RequestInterceptor() {
            @Override
            public void intercept(RequestFacade request) {
                request.addHeader(HttpHeaders.AUTHORIZATION, HttpHeaders.AUTHORIZATION_BEARER_X + accesskey);
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

    private ArrayList<String> jsonTostring(String data_string, ArrayList<String> data_array) throws JSONException {
        JSONArray array_js_allpb = new JSONArray(data_string);

        for(int i=0; i<array_js_allpb.length(); i++){
            JSONObject js_item = array_js_allpb.getJSONObject(i);
            String value = js_item.getString("data");
            JSONObject js_data = new JSONObject(value);
            String[] str_data = value.replace("\"","").split(",");
            String key = js_data.getString("key");

            for(int k=0;k<str_data.length;k++){

                String value1 = key + "|" + str_data[k];
                String result = value1.replaceAll("[\\[\\](){}]","");
                if (result.substring(result.length() - 1).equals(":")){
                    result = result + "-";
                }

                if (result.split(":").length == 2){
                    data_array.add(result);
                }
            }
        }
        return data_array;

    }


    public String JsonStr() throws Exception {

        setUp(ACCESS_KEY, USER_ID);

        String allPublication_b = "";

        ArrayList<String> dt_All = new ArrayList<>();

        rx.Observable<Response> Resp_All = zoteroService.getItemsNotAsAList(LibraryType.USER, USER_ID, null, null);
        Response tes_all = BlockingObservable.from(Resp_All).last();
        allPublication_b = new String(((TypedByteArray) tes_all.getBody()).getBytes());

        jsonTostring(allPublication_b, dt_All);

        return allPublication_b;
    }

// Тест 1: Проверка соответствия данных формату <параметр>:<значение> посредством регулярных выражений.
    @Test
    public void TestCheckFormat() throws Exception {
        String dataString=JsonStr();
        ArrayList<String> ar_data = new ArrayList<>();
        ArrayList<String> ar_data_2 = new ArrayList<>();
        ar_data_2=jsonTostring(dataString,ar_data);


        for(String row_data:ar_data_2){
            assertTrue(row_data.matches(".+:.+"));
        }


    }



}
