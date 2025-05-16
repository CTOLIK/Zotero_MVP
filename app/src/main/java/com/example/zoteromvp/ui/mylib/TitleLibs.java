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
import com.example.zoteromvp.testlibrary;

import java.util.ArrayList;
import java.util.Objects;

public class TitleLibs extends AppCompatActivity {
    @Override
    protected void onResume()
    {
        super.onResume();
        // Создаём ArrayList'ы и через Bundle заполняем их данными, отправленными с предыдущей activity
        Bundle args = getIntent().getExtras();
        assert args != null; // Проверка на заполненность
        ArrayList<String> ar_data = args.getStringArrayList("col");
        ArrayList<String> ar_title = new ArrayList<>();
        ArrayList<String> ar_title_key = new ArrayList<>();

        assert ar_data != null; // Проверка на заполненность
        for(String row_data:ar_data){ // Перебираем параметры объектов коллекций, определяя имена (поля "title" и "caseName")
            String nameofvalue = row_data.split("\\|")[1].split(":")[0];
            String value = row_data.split("\\|")[1].split(":")[1];
            if ((Objects.equals(nameofvalue, "caseName")) || (Objects.equals(nameofvalue, "title"))){
                // Заполняем ArrayList'ы данными о ключе и имени
                ar_title.add(value);
                ar_title_key.add(row_data.split("\\|")[0]);
            }
        }

        // Отображаем в списке совокупность имён публикаций
        ListView listView = findViewById(R.id.Titles);
        ArrayAdapter<String> list_adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, ar_title);
        listView.setAdapter(list_adapter);

        // Обрабатываем нажатие элемента списка
        listView.setOnItemClickListener((parent, view, position, id) -> {
            String unq_key = ar_title_key.get(position); // Получаем ключ публикации
            ArrayList<String> dataoftitle = new ArrayList<>(); // Инициализируем ArrayList для хранения параметров выбранной публикации

            for(String row_data:ar_data){ // Перебираем все параметры
                String nameofvalue = row_data.split("\\|")[1].split(":")[0]; // Вычленяем из строки имя параметра
                String value = row_data.split("\\|")[1].split(":")[1]; // Вычленяем из строки значение параметра
                if (!Objects.equals(value, "{")){ // Отбрасывание элементов, где значение параметра - только "{"
                    if (Objects.equals(row_data.split("\\|")[0], unq_key)){ // Проверка на соответствие ключу
                        dataoftitle.add(nameofvalue + " - " + value); // Заполнение ArrayList параметрами выбранной публикации
                    }
                }
            }

            // Отправка данных выбранной коллекции в другую activity и её запуск
            Intent intent = new Intent(TitleLibs.this, testlibrary.class);
            intent.putStringArrayListExtra("data", dataoftitle);
            startActivity(intent);
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Стандартные процедуры
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