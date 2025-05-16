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
import java.util.Objects;

public class MylibraryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_mylibrary);

        Bundle arguments = getIntent().getExtras();

        ArrayList<String> MyLib = new ArrayList<>();
        MyLib.add("All");
        MyLib.add("Users libraries");
        MyLib.add("Trash");

        ArrayList<String> ar_data_col = arguments.getStringArrayList("users_lib");
        ArrayList<String> ar_data_all = arguments.getStringArrayList("all_lib");
        ArrayList<String> ar_data_trash = arguments.getStringArrayList("trash_lib");


        ArrayList<String> names_of_UL = new ArrayList<>();
        ArrayList<String> keys_of_UL = new ArrayList<>();


        for(String row_data:ar_data_col){
            row_data = row_data.replace("\"","").replace(",","");
            if(Objects.equals(row_data.split("\\|")[1].split(":")[0], "name")){

                names_of_UL.add(row_data.split("\\|")[1].split(":")[1]);

            }

            if(Objects.equals(row_data.split("\\|")[1].split(":")[0], "key")){

                 keys_of_UL.add(row_data.split("\\|")[1].split(":")[1]);
            }
        }


        ListView listView = findViewById(R.id.MyLibrary);
        ArrayAdapter<String> list_adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, MyLib);

        listView.setAdapter(list_adapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                switch (MyLib.get(position)){

                    case ("All"):
                        Intent intent = new Intent(MylibraryActivity.this, TitleLibs.class);
                       //Toast.makeText(MylibraryActivity.this, "Олл", Toast.LENGTH_SHORT).show();
                        intent.putStringArrayListExtra("col", ar_data_all);
                        startActivity(intent);
                        break;

                    case ("Users libraries"):
                        Intent intent2 = new Intent(MylibraryActivity.this, Users_libraries.class);
                        //Toast.makeText(MylibraryActivity.this, "Олл", Toast.LENGTH_SHORT).show();
                        intent2.putStringArrayListExtra("lib", names_of_UL);
                        intent2.putStringArrayListExtra("lib_keys", keys_of_UL);
                        intent2.putStringArrayListExtra("all_libs", ar_data_all);
                        startActivity(intent2);
                        break;

                    case ("Trash"):
                        Intent intent3 = new Intent(MylibraryActivity.this, TitleLibs.class);
//                        Toast.makeText(MylibraryActivity.this, "Тресх", Toast.LENGTH_SHORT).show();
                        intent3.putStringArrayListExtra("col", ar_data_trash);
                        startActivity(intent3);
                        break;
                };

            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}