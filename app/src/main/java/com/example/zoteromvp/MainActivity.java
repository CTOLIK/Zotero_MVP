package com.example.zoteromvp;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.myapplication.R;
import com.example.myapplication.databinding.ActivityMainBinding;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import okhttp3.OkHttpClient;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.client.Header;
import retrofit.client.Response;
import retrofit.converter.GsonConverter;
import rx.Observable;
import rx.functions.Func1;


public class MainActivity extends AppCompatActivity {

    OkHttpClient client;
    TextView textView;

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;


    ZoteroService zoteroService;


    public void imtry(int USER_ID)
    {
        zoteroService.getItemsNotAsAList(LibraryType.USER, USER_ID, null, null)
                .flatMap(new Func1<Response, Observable<Header>>() {
                    @Override
                    public Observable<Header> call(Response response) {
                        System.out.print(Observable.from(response.getHeaders()).toString());
                        return Observable.from(response.getHeaders());
                    }
                })
                .filter(new Func1<Header, Boolean>() {
                    @Override
                    public Boolean call(Header header) {
                        System.out.print(HttpHeaders.LAST_MODIFIED_VERSION.equalsIgnoreCase(header.getName()));
                        return HttpHeaders.LAST_MODIFIED_VERSION.equalsIgnoreCase(header.getName());
                    }
                })
                .map(new Func1<Header, Integer>() {
                    @Override
                    public Integer call(Header header) {
                        System.out.print(Integer.valueOf(header.getValue()).toString());
                        return Integer.valueOf(header.getValue());
                    }
                })
                .toBlocking()
                .first();
    }

    public void setUp(String accesskey, int USER_ID) throws Exception {
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);
        binding.appBarMain.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null)
                        .setAnchorView(R.id.fab).show();
            }
        });
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        // New rows:

        // setContentView(R.layout.activity_main);

        setContentView(R.layout.fragment_home);
        client = new OkHttpClient();
        textView = findViewById(R.id.textView3);
        Button SignIn = findViewById(R.id.button_SignIn);
        Button SignUp = findViewById(R.id.button_SignUp);



        EditText login_text = findViewById(R.id.LoginText);
        EditText pass_text = findViewById(R.id.PassText);

        SignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if ((pass_text.length() > 0) && (login_text.length() > 0)){
                    try {
                        setUp(pass_text.getText().toString(),Integer.parseInt(login_text.getText().toString()));
                        imtry(Integer.parseInt(login_text.getText().toString()));
                    } catch (Exception e) {
                        Toast.makeText(MainActivity.this, "Авторизация провалена.", Toast.LENGTH_SHORT).show();
                        throw new RuntimeException(e);

                    }

                } else {
                    Toast.makeText(MainActivity.this, "Заполните поля для авторизации.", Toast.LENGTH_SHORT).show();
                }

            }
        });


//        SignUp.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                try {
//                    setUp(pass_text.getText().toString(), Integer.parseInt(login_text.getText().toString()));
//                } catch (Exception e) {
//                    throw new RuntimeException(e);
//                }
//            }
//        });

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