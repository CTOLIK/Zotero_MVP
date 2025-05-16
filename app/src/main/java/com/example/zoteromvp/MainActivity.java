package com.example.zoteromvp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.zoteromvp.databinding.ActivityMainBinding;
import com.example.zoteromvp.ui.mylib.MylibraryActivity;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.client.Response;
import retrofit.converter.GsonConverter;
import retrofit.mime.TypedByteArray;
import rx.observables.BlockingObservable;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onResume()
    {
        super.onResume();

        Button SignIn = findViewById(R.id.button_SignIn);
        EditText login_text = findViewById(R.id.LoginText);
        EditText pass_text = findViewById(R.id.PassText);

                login_text.setText("743083");
              pass_text.setText("vLLtTQU1tcWCPb3Grsl6GzWi");

     //      login_text.setText("17116393");
    //    pass_text.setText("vrNSxghdhbqT82b1TPLsjVn5");


        ArrayList<String> dt_Collections = new ArrayList<>();
        ArrayList<String> dt_All = new ArrayList<>();
        ArrayList<String> dt_Trash = new ArrayList<>();


        SignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String allPublication_b = "";
                String trash_b = "";
                String collection_b = "";

                Long USER_ID = Long.parseLong(login_text.getText().toString());

                if ((pass_text.length() > 0) && (login_text.length() > 0)){
                    try {
                        setUp(pass_text.getText().toString(), Integer.parseInt(login_text.getText().toString()));

                        rx.Observable<Response> Resp_All = zoteroService.getItemsNotAsAList(LibraryType.USER, USER_ID, null, null);
                        Response tes_all = BlockingObservable.from(Resp_All).last();
                        allPublication_b = new String(((TypedByteArray) tes_all.getBody()).getBytes());

                        rx.Observable<Response> Resp_Trash = zoteroService.getTrashItems(LibraryType.USER, USER_ID, null);
                        Response tes_tr = BlockingObservable.from(Resp_Trash).last();
                        trash_b = new String(((TypedByteArray) tes_tr.getBody()).getBytes());


                        rx.Observable<Response> Resp_lib = zoteroService.getCollections(LibraryType.USER, USER_ID, null);
                        Response tes_li = BlockingObservable.from(Resp_lib).last();
                        collection_b = new String(((TypedByteArray) tes_li.getBody()).getBytes());
                        //Log.e("blyad", allPublication_b);


                        JSONArray array_js_allpb = new JSONArray(allPublication_b);

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
                                    dt_All.add(result);
                                }
                            }
                        }

                        JSONArray array_js_trash = new JSONArray(trash_b);

                        for(int i=0; i<array_js_trash.length(); i++){
                            JSONObject js_item = array_js_trash.getJSONObject(i);
                            String value = js_item.getString("data");
                            JSONObject js_data = new JSONObject(value);
                            String[] str_data = value.replace("\"","").split(",");
                            String key = js_data.getString("key");

                            for(int k=0;k<str_data.length;k++){

                                String value1 = key + "|" + str_data[k];
                                String result = value1.replaceAll("[-+^,]","").replaceAll("[\\[\\](){}]","");
                                if (result.substring(result.length() - 1).equals(":")){
                                    result = result + "-";
                                }
                                if (result.split(":").length == 2) {

                                    dt_Trash.add(result);

                                }

                            }
                        }

                        JSONArray array_js_coll = new JSONArray(collection_b);

                        for(int i=0; i<array_js_coll.length(); i++){
                            JSONObject js_item = array_js_coll.getJSONObject(i);
                            String value = js_item.getString("data");
                            String[] str_data = value.replace("\"","").split(",");
                            String key = js_item.getString("key");

                            for(int k=0;k<str_data.length;k++){

                                String value1 = key + "|" + str_data[k];
                                String result = value1.replaceAll("[-+^,]","").replaceAll("[\\[\\](){}]","");
                                if (result.substring(result.length() - 1).equals(":")){
                                    result = result + "-";
                                }
                                dt_Collections.add(result);
                            }
                        }

                        if (!dt_All.isEmpty()){

                            Intent intent = new Intent(MainActivity.this, MylibraryActivity.class);
                            intent.putExtra("login", login_text.getText().toString());
                            intent.putExtra("pass", pass_text.getText().toString());
                            intent.putStringArrayListExtra("users_lib", dt_Collections);
                            intent.putStringArrayListExtra("all_lib", dt_All);
                            intent.putStringArrayListExtra("trash_lib", dt_Trash);
                            startActivity(intent);

                        }
//
                    } catch (Exception e) {
                        Toast.makeText(MainActivity.this, "Авторизация провалена.", Toast.LENGTH_SHORT).show();
                        throw new RuntimeException(e);

                    }

                } else {
                    Toast.makeText(MainActivity.this, "Заполните поля для авторизации.", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }


    private ActivityMainBinding binding;

    ZoteroService zoteroService;


    public void setUp(String accesskey, int USER_ID) throws Exception {
        try {
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

        } catch (Exception e) {
            Toast.makeText(this, "Не удалось авторизоваться.", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

    }
}