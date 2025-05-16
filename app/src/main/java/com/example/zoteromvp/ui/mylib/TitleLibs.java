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
import com.example.zoteromvp.testlibrary;

import java.util.ArrayList;
import java.util.Objects;

public class TitleLibs extends AppCompatActivity {
    @Override
    protected void onResume()
    {
        super.onResume();

        Bundle args = getIntent().getExtras();

        ArrayList<String> ar_data = args.getStringArrayList("col");

        ArrayList<String> ar_title = new ArrayList<>();
        ArrayList<String> ar_title_key = new ArrayList<>();

        for(String row_data:ar_data){
            String nameofvalue = row_data.split("\\|")[1].split(":")[0];
            String value = row_data.split("\\|")[1].split(":")[1];

         //   Log.e("TEST", row_data);

           // if (!Objects.equals(nameofvalue, "creators")){
                if ((Objects.equals(nameofvalue, "caseName")) || (Objects.equals(nameofvalue, "title"))){
                    ar_title.add(value);
                    ar_title_key.add(row_data.split("\\|")[0]);
                }
         //   }

        }







        ListView listView = findViewById(R.id.Titles);
        ArrayAdapter<String> list_adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, ar_title);

        listView.setAdapter(list_adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //String str_title = ar_title.get(position);
                String unq_key = ar_title_key.get(position);

                ArrayList<String> dataoftitle = new ArrayList<>();

                for(String row_data:ar_data){
                    String nameofvalue = row_data.split("\\|")[1].split(":")[0];
                    String value = row_data.split("\\|")[1].split(":")[1];

                    if (!Objects.equals(value, "{")){
                    if (Objects.equals(row_data.split("\\|")[0], unq_key)){
                        dataoftitle.add(nameofvalue + " - " + value);
                    }
                     }

                }

                Intent intent = new Intent(TitleLibs.this, testlibrary.class);
//                    intent.putExtra("login", login_text.getText().toString());
//                    intent.putExtra("pass", pass_text.getText().toString());
//                    intent.putStringArrayListExtra("users_lib", dt_Collections);
//                    intent.putStringArrayListExtra("all_lib", dt_All);
                intent.putStringArrayListExtra("data", dataoftitle);
            //    Toast.makeText(TitleLibs.this, dataoftitle.get(0), Toast.LENGTH_SHORT).show();

                startActivity(intent);



                //switch (ar_title.get(position)){



                //}

            }
        });

    }

//    protected void onPause()
//    {
//        super.onPause();
//        ListView lb = (ListView) findViewById(R.id.Titles);
//        lb.setAdapter(null);
//
//    }

//    protected void onResume()
//    {
//        super.onResume();
//        ListView lb = (ListView) findViewById(R.id.Titles);
//        lb.setAdapter(null);
//
//    }

//    protected void onStop()
//    {
//        super.onStop();
//        ListView lb = (ListView) findViewById(R.id.Titles);
//        lb.setAdapter(null);
//
//    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_title_libs);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });






    }
}