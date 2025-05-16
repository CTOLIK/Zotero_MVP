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
import java.util.HashSet;
import java.util.Objects;

public class Users_libraries extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_users_libraries);

        // Создаём ArrayList'ы и через Bundle заполняем их данными, отправленными с MyLibraryActivity
        Bundle argument = getIntent().getExtras();
        assert argument != null;
        ArrayList<String> namesofLibs = argument.getStringArrayList("lib");
        ArrayList<String> keysofLibs = argument.getStringArrayList("lib_keys");
        ArrayList<String> allData = argument.getStringArrayList("all_libs");

        // Отображаем в списке совокупность имён пользовательских коллекций
        ListView listViewv = findViewById(R.id.UsersLibraryList);
        assert namesofLibs != null;
        ArrayAdapter<String> list_adaptere = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, namesofLibs);
        listViewv.setAdapter(list_adaptere);

        // Обрабатываем нажатие на элементы списка
        listViewv.setOnItemClickListener((parent, view, position, id) -> {
            // Проверка коллекции на заполненность
            assert keysofLibs != null;
            String keyCol = keysofLibs.get(position); // Запоминаем ключ коллекции
            HashSet<String> datatopost_keys = new HashSet<>(); // Инициализируем HashSet для избежания дублирования данных (они игнорируются при попытке добавления)
            ArrayList<String> datapost = new ArrayList<>(); // Инициализируем ArrayList

            // Проверка коллекции на заполненность
            assert allData != null;
            for(String row_data:allData){ // Перебираем allData, разделяя на ключ, имя и значение параметра. Тем самым мы формируем совокупность ключей
                String key = row_data.split("\\|")[0];
                String nameofvalue = row_data.split("\\|")[1].split(":")[0];
                String value = row_data.split("\\|")[1].split(":")[1];

                if (Objects.equals(nameofvalue, "collections")){ // Если имя параметра = "collection"
                    if (Objects.equals(keyCol, value)){ // Если значение параметра равняется ключу, то это свидетельствует о его принадлежности к данной коллекции
                        datatopost_keys.add(key); // Добавление ключа в HashSet
                    }
                }
            }

            for(String key_items:datatopost_keys) { // Обход ключей и формирование всех данных публикаций по элементам коллекций
                for (String row_data : allData) {
                    String key = row_data.split("\\|")[0];
                    if (Objects.equals(key_items, key)) {
                        datapost.add(row_data);
                    }
                }
            }

            // Отправка данных выбранной коллекции в другую activity и её запуск
            Intent intent = new Intent(Users_libraries.this, TitleLibs.class);
            intent.putStringArrayListExtra("col", datapost);
            startActivity(intent);
        });

        // Стандартные процедуры
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}