package com.example.zoteromvp;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class testlibrary extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_testlibrary);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Bundle args = getIntent().getExtras();

        ArrayList<String> ar_data = args.getStringArrayList("data");

        ArrayList<String> list_name = new ArrayList<>();
        ArrayList<String> list_value = new ArrayList<>();

        for(String data_row:ar_data){
            if (data_row.contains(" - []")){
                data_row = data_row.replace(" - []", " - empty");
            }

          //  if (!Objects.equals(data_row.split(" - ")[0], "creators")){
                list_value.add(data_row.split(" - ")[1]);
                list_name.add(data_row.split(" - ")[0]);
         //   }


        }


        ArrayAdapter<String> list_adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, ar_data);

       // ListView listView1 = findViewById(R.id.TESTList);

     //   listView1.setAdapter(list_adapter);

        TableLayout nt = (TableLayout) findViewById(R.id.tableLayout2_2);

        int count = list_adapter.getCount();
        for (int i = 0; i < count; i++) {
            //tableLayout.addView(createTableRow(adapter.getItem(i));
            TableRow tableRow1 = new TableRow(this);
            TextView textView1 = new TextView(this);

            textView1.setText(list_name.get(i));
            tableRow1.addView(textView1, new TableRow.LayoutParams(
                    TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT, 0.5f));

            EditText editText1 = new EditText(this);
            editText1.setText(list_value.get(i), TextView.BufferType.EDITABLE);
            tableRow1.addView(editText1, new TableRow.LayoutParams(
                    TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT, 1.0f));

            nt.addView(tableRow1);

        }

        Button post_button = (Button) findViewById(R.id.buttonpost);

        post_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(testlibrary.this, "Данные изменены.", Toast.LENGTH_SHORT).show();
            }
        });


    }
}