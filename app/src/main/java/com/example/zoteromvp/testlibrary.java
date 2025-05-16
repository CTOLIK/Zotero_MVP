package com.example.zoteromvp;
// Импорт используемых библиотек

import android.os.Bundle;
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
        // Стандартные процедуры
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_testlibrary);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Создаём ArrayList'ы и через Bundle заполняем их данными, отправленными с предыдущей activity
        Bundle args = getIntent().getExtras();
        assert args != null; // Проверка на заполненность
        ArrayList<String> ar_data = args.getStringArrayList("data");
        ArrayList<String> list_name = new ArrayList<>();
        ArrayList<String> list_value = new ArrayList<>();

        assert ar_data != null; // Проверка на заполненность
        for(String data_row:ar_data){ // Перебор параметров и их значений и их форматирование
            if (data_row.contains(" - []")){
                data_row = data_row.replace(" - []", " - empty"); // Замена пустых квадратных скобок на "empty"
            }
                // Заполнение ArrayList
                list_value.add(data_row.split(" - ")[1]);
                list_name.add(data_row.split(" - ")[0]);
        }

        // Создаём адаптер
        ArrayAdapter<String> list_adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, ar_data);

        // Инициализируем TableLayout для отображения в первом столбце имени парметра типа textView и значения во втором с типом editText
        TableLayout nt = findViewById(R.id.tableLayout2_2);

        int count = list_adapter.getCount(); // Подсчёт колличества параметров
        for (int i = 0; i < count; i++) { // Создание и добавление в TableLayout строк из двух значений
            TableRow tableRow1 = new TableRow(this);
            TextView textView1 = new TextView(this);

            // Создание textView и присвоение ему i-параметра
            textView1.setText(list_name.get(i));
            tableRow1.addView(textView1, new TableRow.LayoutParams(
                    TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT, 0.5f));

            // Создание textView и присвоение ему i-параметра
            EditText editText1 = new EditText(this);
            editText1.setText(list_value.get(i), TextView.BufferType.EDITABLE);
            tableRow1.addView(editText1, new TableRow.LayoutParams(
                    TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT, 1.0f));

            // Заполнение TableLayout
            nt.addView(tableRow1);

        }

        Button post_button = findViewById(R.id.buttonpost); // Инициализируем кнопку, по нажатию которой данные будут изменены, сохранены и отправлены в Zotero (в будущем)

        // На текущий момент отображается Toast с сообщением "Данные изменены".
        post_button.setOnClickListener(v -> Toast.makeText(testlibrary.this, "Данные изменены.", Toast.LENGTH_SHORT).show());

    }
}