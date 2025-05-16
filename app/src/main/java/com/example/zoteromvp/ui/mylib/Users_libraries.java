package com.example.zoteromvp.ui.mylib;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.zoteromvp.R;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Objects;

public class Users_libraries extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_users_libraries);
       //

        Bundle argument = getIntent().getExtras();

      //  Toast.makeText(this, argument.get("lib").toString(), Toast.LENGTH_SHORT).show();
       // System.out.print("BL" + argument.get("lib").toString()[0]);

        ArrayList<String> namesofLibs = argument.getStringArrayList("lib");
        ArrayList<String> keysofLibs = argument.getStringArrayList("lib_keys");
        ArrayList<String> allData = argument.getStringArrayList("all_libs");

        ListView listViewv = findViewById(R.id.UsersLibraryList);
        ArrayAdapter<String> list_adaptere = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, namesofLibs);

        listViewv.setAdapter(list_adaptere);

        listViewv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String keyCol = keysofLibs.get(position);
                HashSet<String> datatopost_keys = new HashSet<>();
                ArrayList<String> datakey = new ArrayList<>();
                ArrayList<String> datapost = new ArrayList<>();

                assert allData != null;
                for(String row_data:allData){
                    String key = row_data.split("\\|")[0];
                    String nameofvalue = row_data.split("\\|")[1].split(":")[0];
                    String value = row_data.split("\\|")[1].split(":")[1];

                    if (Objects.equals(nameofvalue, "collections")){
                        if (Objects.equals(keyCol, value)){
                            datatopost_keys.add(key);
                           // Toast.makeText(Users_libraries.this, key, Toast.LENGTH_SHORT).show();
                        }
                    }

                }

                for(String key_items:datatopost_keys) {
                    for (String row_data : allData) {
                        String key = row_data.split("\\|")[0];
                        String nameofvalue = row_data.split("\\|")[1].split(":")[0];
                        String value = row_data.split("\\|")[1].split(":")[1];


                        // Toast.makeText(Users_libraries.this, key, Toast.LENGTH_SHORT).show();

//                        if (key==key_items){
//                            Toast.makeText(Users_libraries.this, "Хелов", Toast.LENGTH_SHORT).show();
//                        }

                        if (Objects.equals(key_items, key)) {
//                        if (Objects.equals(keyCol, value)){
//                            datatopost_keys.add(value);
//                        }\
                            datapost.add(row_data);
                         //   Toast.makeText(Users_libraries.this, row_data, Toast.LENGTH_SHORT).show();
                        }
                    }
                }




                Intent intent = new Intent(Users_libraries.this, TitleLibs.class);
//                    intent.putExtra("login", login_text.getText().toString());
//                    intent.putExtra("pass", pass_text.getText().toString());
//                    intent.putStringArrayListExtra("users_lib", dt_Collections);
//                    intent.putStringArrayListExtra("all_lib", dt_All);
                intent.putStringArrayListExtra("col", datapost);
          //      Toast.makeText(Users_libraries.this, datapost.get(0), Toast.LENGTH_SHORT).show();

                startActivity(intent);


            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;

        });
    }
}