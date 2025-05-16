package com.example.zoteromvp.ui.gallery;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.zoteromvp.R;
import com.example.zoteromvp.ui.pub.Publication;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

public class LibAct extends AppCompatActivity {

//    @Override
//    protected void onPause(){
//        super.onPause();
//        ListView listView = findViewById(R.id.MyLib);
//        listView.setAdapter(null);
//
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.fragment_gallery);

        Bundle arguments = getIntent().getExtras();
        String data = arguments.get("data").toString();
        HashSet<String> hs_data = (HashSet<String>) arguments.get("hs_data");
        ArrayList<String> ar_data = new ArrayList<>(hs_data);
        Collections.sort(ar_data);

        ArrayList<String> ar_data2 = new ArrayList<>();
        for(String row_data:ar_data){
            String value = row_data.split("\\|")[1].split(":")[1].replace("\"","").replace(",","").trim();
            if (value != null){
                ar_data2.add(row_data);
            }
        }

        //System.out.print(data);


        ArrayList<String> types_lib = new ArrayList<>();
        ArrayList<String> pr_types_lib = new ArrayList<>();
        ArrayList<String> id_keys_lib = new ArrayList<>();
        ArrayList<String> val_keys_lib = new ArrayList<>();


        ArrayList<String> val_keys_title = new ArrayList<>();
        ArrayList<String> pr_val_keys_title = new ArrayList<>();

        String myStr = "";

        for (String row_data : ar_data) {
            String id_key = row_data.split("\\|")[0].replace("\"", "").replace(",", "").trim();

            String nm_key = row_data.split("\\|")[1].split(":")[0].replace("\"", "").replace(",", "").trim();


            //String val_key = row_data.split("|")[1].split(":")[1].trim();

            // Toast.makeText(this, val_key, Toast.LENGTH_SHORT).show();

            if (!id_keys_lib.contains(id_key)) {
                id_keys_lib.add(id_key);

                try {
                    if (row_data.split("\\|")[1].split(":")[1] != null) {
                        String val_key = row_data.split("\\|")[1].split(":")[1].replace("\"", "").replace(",", "").trim();
                        val_keys_lib.add(val_key);
                        myStr += val_key + "\n";
                        //System.out.print(row_data);
                        //Toast.makeText(this, row_data.toString(), Toast.LENGTH_SHORT).show();

                    }
                } catch (Exception e) {

                    //Toast.makeText(this, row_data.toString(), Toast.LENGTH_SHORT).show();
                }
//


                //Toast.makeText(this, id_key, Toast.LENGTH_SHORT).show();
                //
            }
        }

        //System.out.print(myStr);

        String out = "";
        for (String row_idkey : id_keys_lib) {
            for (String row_data : ar_data) {
                String id_key = row_data.split("\\|")[0].replace("\"", "").replace(",", "").trim();
                String nm_key = row_data.split("\\|")[1].split(":")[0].replace("\"", "").replace(",", "").trim();

                if ((nm_key.equals("itemType")) && (id_key.equals(row_idkey))) {

                    try {
                        if (row_data.split("\\|")[1].split(":")[1] != null) {
                            String val_key = row_data.split("\\|")[1].split(":")[1].replace("\"", "").replace(",", "").trim();
                            String name_type = val_key.toUpperCase().charAt(0) + val_key.substring(1);

                            if ((!name_type.equals("Note")) && (!pr_types_lib.contains(name_type))) {
                                pr_types_lib.add(name_type);
                                types_lib.add(val_key);
                                myStr += row_data + "\n";
                            }


                            //System.out.print(row_data);
                            //Toast.makeText(this, row_data.toString(), Toast.LENGTH_SHORT).show();

                        }
                    } catch (Exception e) {

                        //Toast.makeText(this, row_data.toString(), Toast.LENGTH_SHORT).show();
                    }
                }

                }

            }


        for (String row_idkey : id_keys_lib) {
            for (String row_data : ar_data) {
                String id_key = row_data.split("\\|")[0].replace("\"", "").replace(",", "").trim();
                String nm_key = row_data.split("\\|")[1].split(":")[0].replace("\"", "").replace(",", "").trim();

                if ((nm_key.equals("title")) && (id_key.equals(row_idkey))) {

                    try {
                        if (row_data.split("\\|")[1].split(":")[1] != null) {
                            String val_key = row_data.split("\\|")[1].split(":")[1].replace("\"", "").replace(",", "").trim();
                            String name_type = val_key.toUpperCase().charAt(0) + val_key.substring(1);

                            //if (!name_type.equals("Note")) {
                                pr_val_keys_title.add(name_type);
                                val_keys_title.add(val_key);
                                //myStr += row_data + "\n";
                           // }


                            //System.out.print(row_data);
                            //Toast.makeText(this, name_type, Toast.LENGTH_SHORT).show();

                        }
                    } catch (Exception e) {

                        //Toast.makeText(this, row_data.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            }





            //System.out.print(out);

            //Toast.makeText(this, val_keys_title.size(), Toast.LENGTH_SHORT).show();

            ListView listView = findViewById(R.id.MyLib);
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                    android.R.layout.simple_list_item_1, pr_types_lib);

            listView.setAdapter(adapter);

            String ss = "";

            for(String row:val_keys_title){ ss+=row+"\n";}

            //System.out.print(ss);


            listView.setClickable(true);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    //startActivity(new Intent(LibAct.this, Publication.class));

                    Intent intent = new Intent(LibAct.this, Publication.class);
                    intent.putExtra("row_name", pr_types_lib.get(position));
                     intent.putExtra("titles", val_keys_title);
                    //intent.putExtra("hs_data", all_Data);
                    startActivity(intent);
                    //val_keys_title.clear();
//                jsonFile = "";
//                all_Data.clear();


                    //setContentView(R.layout.fragment_pub);

                }
            });

        }


//        for(String row_data1:id_keys_lib){
//            String f = row_data1;
//
////            String nm_key = row_data.split("|")[1].split(":")[0].replace("\"","").trim();
////            String val_key = row_data.split("|")[1].split(":")[1].replace("\"","").trim();
//                Toast.makeText(this, f, Toast.LENGTH_SHORT).show();
//
//        }


        //   Toast.makeText(this, id_keys_lib.size(), Toast.LENGTH_SHORT).show();

        // String[] ar_test = data.split("\n");
        //  String[] s_data = new String[hs_data.size()];
        // ar_data = hs_data.toArray(ar_data);


    }
}