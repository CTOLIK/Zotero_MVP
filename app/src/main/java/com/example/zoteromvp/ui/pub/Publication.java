package com.example.zoteromvp.ui.pub;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.zoteromvp.R;

import java.util.ArrayList;

public class Publication extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_pub);

        Bundle arguments = getIntent().getExtras();
        String r = arguments.get("row_name").toString();
        ArrayList<String> ar_data = (ArrayList<String>)arguments.get("titles");

        String s = "";

        for(String row:ar_data){ s+=row+"\n";}




        ListView listView = findViewById(R.id.Titles_nm);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, ar_data);

        listView.setAdapter(adapter);



        System.out.print(s);
        //Toast.makeText(this,r, Toast.LENGTH_SHORT).show();



    }

}