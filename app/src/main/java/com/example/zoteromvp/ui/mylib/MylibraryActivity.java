package com.example.zoteromvp.ui.mylib;
// Импорт используемых библиотек

import android.content.Intent;
import android.os.Bundle;
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

        // Создаём ArrayList статичной структуры, состоящий из трёх элементов для отображения дерева MyLibrary: All, Users libraries, Trash
        ArrayList<String> MyLib = new ArrayList<>();
        MyLib.add("All");
        MyLib.add("Users libraries");
        MyLib.add("Trash");

        // Создаём ArrayList'ы и через Bundle получаем данные, отправленные с MainActivity
        Bundle arguments = getIntent().getExtras();
        assert arguments != null; // Проверка на ноль
        ArrayList<String> ar_data_col = arguments.getStringArrayList("users_lib");
        ArrayList<String> ar_data_all = arguments.getStringArrayList("all_lib");
        ArrayList<String> ar_data_trash = arguments.getStringArrayList("trash_lib");

        // Дополнительные ArrayList'ы для отделения ключей от данных о пользовательских коллекциях
        ArrayList<String> names_of_UL = new ArrayList<>();
        ArrayList<String> keys_of_UL = new ArrayList<>();

        assert ar_data_col != null; // Проверка на ноль
        for(String row_data:ar_data_col){ // В цикле обрабатываем данные о коллекциях и вычленяем параметры "name" и "key", добавляя их в соответствующие листы
            row_data = row_data.replace("\"","").replace(",","");
            if(Objects.equals(row_data.split("\\|")[1].split(":")[0], "name")){
                names_of_UL.add(row_data.split("\\|")[1].split(":")[1]);
            } else if(Objects.equals(row_data.split("\\|")[1].split(":")[0], "key")){
                 keys_of_UL.add(row_data.split("\\|")[1].split(":")[1]);
            }
        }

        // Формируем адаптер для списка
        ListView listView = findViewById(R.id.MyLibrary);
        ArrayAdapter<String> list_adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, MyLib);

        // Присваиваем адаптер listview, тем самым отображая его для пользователя
        listView.setAdapter(list_adapter);

        // Обработка по нажатию на элемент списка. В зависимости от нажатого элемента осуществляется переход на другие activity и отсылаются соответствующие данные в виде ArrayList
        listView.setOnItemClickListener((parent, view, position, id) -> {
            // Так как коллекция MyLib одинакова для всех, был применён оператор switch для организации ветвления
            switch (MyLib.get(position)){
                case ("All"):
                    Intent intent = new Intent(MylibraryActivity.this, TitleLibs.class);
                    intent.putStringArrayListExtra("col", ar_data_all);
                    startActivity(intent);
                    break;
                case ("Users libraries"):
                    Intent intent2 = new Intent(MylibraryActivity.this, Users_libraries.class);
                    intent2.putStringArrayListExtra("lib", names_of_UL);
                    intent2.putStringArrayListExtra("lib_keys", keys_of_UL);
                    intent2.putStringArrayListExtra("all_libs", ar_data_all);
                    startActivity(intent2);
                    break;
                case ("Trash"):
                    Intent intent3 = new Intent(MylibraryActivity.this, TitleLibs.class);
                    intent3.putStringArrayListExtra("col", ar_data_trash);
                    startActivity(intent3);
                    break;
            }
        });
        // Стандартные процедуры
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}