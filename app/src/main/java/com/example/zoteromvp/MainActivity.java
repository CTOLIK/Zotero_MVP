package com.example.zoteromvp;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.zoteromvp.databinding.ActivityMainBinding;
import com.example.zoteromvp.ui.mylib.MylibraryActivity;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashSet;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.client.Header;
import retrofit.client.Response;
import retrofit.converter.GsonConverter;
import retrofit.mime.TypedByteArray;
import rx.Observable;
import rx.functions.Func1;


public class MainActivity extends AppCompatActivity {

//    protected void onPause()
//    {
//        super.onPause();
//        allPublication_b = "";
//        trash_b = "";
//        collection_b = "";
//
//    }
    @Override
    protected void onResume()
    {
        super.onResume();
        allPublication_b = "";
        trash_b = "";
        collection_b = "";

        Button SignIn = findViewById(R.id.button_SignIn);
        //Button SignUp = findViewById(R.id.button_SignUp);



        EditText login_text = findViewById(R.id.LoginText);
        EditText pass_text = findViewById(R.id.PassText);

              login_text.setText("743083");
              pass_text.setText("vLLtTQU1tcWCPb3Grsl6GzWi");

        //   login_text.setText("17116393");
      //  pass_text.setText("vrNSxghdhbqT82b1TPLsjVn5");


        ArrayList<String> dt_Collections = new ArrayList<>();
        ArrayList<String> dt_All = new ArrayList<>();
        ArrayList<String> dt_Trash = new ArrayList<>();


        HashSet<String> ar_Items = new HashSet<>();
        HashSet<String> all_Data = new HashSet<>();

        SignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if ((pass_text.length() > 0) && (login_text.length() > 0)){
                    try {
                        setUp(pass_text.getText().toString(), Integer.parseInt(login_text.getText().toString()));

                        //dataCollection = "";
                        String tdata_col = "";
                        String postdata_col = "";

                        while (collection_b.contains("\"data\": {")){
                            tdata_col = collection_b.substring(collection_b.indexOf("\"data\": {"));
                            tdata_col=tdata_col.substring(0, tdata_col.indexOf("}"));
                            postdata_col += tdata_col + "\n";
                            //tdata = datest;
                            collection_b = collection_b.replace(tdata_col,"");
                        }


                        String[] st = postdata_col.split("\n");

                        String col_name = "";
                        for (int i=0; i<st.length; i++){
                            if (st[i].matches("\\D+:\\s[^{]+.")) {
                                st[i] = st[i].trim();
                                //Toast.makeText(MainActivity.this, st[i], Toast.LENGTH_SHORT).show();
                                if (st[i].split(":")[0].equals("\"key\"")){
                                    col_name = st[i].split(":")[1].replace("\"","").replace(",","").trim();
                                    //ar_Items.add(col_name);
                                    //itemKey = itemKey.replace("\"","");
                                }

                                // dataCollection += col_name + "|" + st[i].replace(",", "").replace("\"", "").replaceAll("\\s","").trim() + "\n";
                                dt_Collections.add(col_name + "|" + st[i].replace(",", "").replace("\"", "").replaceAll("\\s","").trim());
                            }
                        }


                        //  dataAll= "";
                        String tdata_all = "";
                        String postdata_all = "";

                        while (allPublication_b.contains("\"data\": {")){
                            tdata_all = allPublication_b.substring(allPublication_b.indexOf("\"data\": {"));
                            tdata_all=tdata_all.substring(0, tdata_all.indexOf("}"));
                            postdata_all += tdata_all + "\n";
                            //tdata = datest;
                            //Toast.makeText(MainActivity.this, tdata_all, Toast.LENGTH_SHORT).show();
                            allPublication_b = allPublication_b.replace(tdata_all,"");
                        }


                        String[] st_all = postdata_all.replace("[\n","").replace("\",","").split("\n");

                        String all_name = "";
                        for (int i=0; i<st_all.length; i++){
                            if (st_all[i].matches("\\D+:\\s[^{]+.")) {
                                st_all[i] = st_all[i].trim();
                                //Toast.makeText(MainActivity.this, st[i], Toast.LENGTH_SHORT).show();
                                if (st_all[i].split(":")[0].equals("\"key\"")){
                                    all_name = st_all[i].split(":")[1].replace("\"","").replace(",","").trim();
                                    //ar_Items.add(all_name);
                                    //itemKey = itemKey.replace("\"","");
                                }

                                //      dataAll += all_name + "|" + st_all[i].replace(",", "").replace("\"", "").replaceAll("\\s","").trim() + "\n";
                                dt_All.add(all_name + "|" + st_all[i].replace(",", "").replace("\"", "").replaceAll("\\s","").trim());
                            }
                        }


                        // dataTrash = "";
                        String tdata_tr = "";
                        String postdata_tr = "";

                        while (trash_b.contains("\"data\": {")){
                            tdata_tr = trash_b.substring(trash_b.indexOf("\"data\": {"));
                            tdata_tr=tdata_tr.substring(0, tdata_tr.indexOf("}"));
                            postdata_tr += tdata_tr + "\n";
                            //tdata = datest;
                            trash_b = trash_b.replace(tdata_tr,"");
                        }


                        String[] st_tr = postdata_tr.replace("[\n","").replace("\",","").split("\n");

                        String tr_name = "";
                        for (int i=0; i<st_tr.length; i++){
                            if (st_tr[i].matches("\\D+:\\s[^{]+.")) {
                                st_tr[i] = st_tr[i].trim();
                                //Toast.makeText(MainActivity.this, st[i], Toast.LENGTH_SHORT).show();
                                if (st_tr[i].split(":")[0].equals("\"key\"")){
                                    tr_name = st_tr[i].split(":")[1].replace("\"","").replace(",","").trim();
                                    //ar_Items.add(tr_name);
                                    //itemKey = itemKey.replace("\"","");
                                }

                                //       dataTrash += tr_name + "|" + st_tr[i].replace(",", "").replace("\"", "").replaceAll("\\s","").trim() + "\n";
                                dt_Trash.add(tr_name + "|" + st_tr[i].replace(",", "").replace("\"", "").replaceAll("\\s","").trim());
                            }

                            // Добавить коллекции


                        }


//                        System.out.print("COLLECTION: \n" + dataCollection);
//                        System.out.print("TRASH: \n" + dataTrash);
//                        System.out.print("ALL: \n" + dataAll);



                        if (!dt_All.isEmpty()){
//                            Intent intent = new Intent(MainActivity.this, LibAct.class);
//                            intent.putExtra("data", jsonFile);
//                            intent.putExtra("hs_data", all_Data);
//                            startActivity(intent);
//                            jsonFile = "";
//                            all_Data.clear();

                            Intent intent = new Intent(MainActivity.this, MylibraryActivity.class);
                            intent.putExtra("login", login_text.getText().toString());
                            intent.putExtra("pass", pass_text.getText().toString());
                            intent.putStringArrayListExtra("users_lib", dt_Collections);
                            intent.putStringArrayListExtra("all_lib", dt_All);
                            intent.putStringArrayListExtra("trash_lib", dt_Trash);
                            startActivity(intent);

                        }

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

//    protected void onStop()
//    {
//        super.onStop();
//        allPublication_b = "";
//        trash_b = "";
//        collection_b = "";
//
//    }

//    protected void onResume()
//    {
//        super.onResume();
//        allPublication_b = "";
//        trash_b = "";
//        collection_b = "";
//
//    }




    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;


    ZoteroService zoteroService;

    String allPublication_b = "";
    String trash_b = "";
    String collection_b = "";


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

            zoteroService.getCollections(LibraryType.USER, USER_ID, null)
                    .flatMap(new Func1<Response, Observable<Header>>() {
                        @Override
                        public Observable<Header> call(Response response) {
                            //if (body[0] == '[') {body.}
                            collection_b = new String(((TypedByteArray) response.getBody()).getBytes());
                            collection_b = collection_b +"\n]";
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
                    .first();

         //   System.out.print("collect " + col);

            zoteroService.getTrashItems(LibraryType.USER, USER_ID, null)
                    .flatMap(new Func1<Response, Observable<Header>>() {
                        @Override
                        public Observable<Header> call(Response response) {
                            //if (body[0] == '[') {body.}
                            trash_b = new String(((TypedByteArray) response.getBody()).getBytes());
                            trash_b = trash_b +"\n]";
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
                    .first();
//
//             //String trash = myList.toString();
//
//            //System.out.print("trashg - " + body_trash);
//
//
            zoteroService.getItemsNotAsAList(LibraryType.USER, USER_ID, null, null)
                    .flatMap(new Func1<Response, Observable<Header>>() {
                        @Override
                        public Observable<Header> call(Response response) {
                            //if (body[0] == '[') {body.}
                            allPublication_b = new String(((TypedByteArray) response.getBody()).getBytes());
                            allPublication_b = allPublication_b +"\n]";
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
                    .first();

        } catch (Exception e){
            Toast.makeText(this, "Не удалось авторизоваться.", Toast.LENGTH_SHORT).show();
        }
    }

//    String dataCollection = "";
//    String dataTrash = "";
//    String dataAll = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

//        setSupportActionBar(binding.appBarMain.toolbar);
//        binding.appBarMain.fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null)
//                        .setAnchorView(R.id.fab).show();
//            }
//        });
//        DrawerLayout drawer = binding.drawerLayout;
//        NavigationView navigationView = binding.navView;
//        // Passing each menu ID as a set of Ids because each
//        // menu should be considered as top level destinations.
//        mAppBarConfiguration = new AppBarConfiguration.Builder(
//                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
//                .setOpenableLayout(drawer)
//                .build();
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
//        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
//        NavigationUI.setupWithNavController(navigationView, navController);

        // New rows:

        // setContentView(R.layout.activity_main);

       // setContentView(R.layout.fragment_login);



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}