package com.example.zoteromvp;
// Импорт используемых библиотек

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.zoteromvp.databinding.ActivityMainBinding;
import com.example.zoteromvp.ui.mylib.MylibraryActivity;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.client.Response;
import retrofit.converter.GsonConverter;
import retrofit.mime.TypedByteArray;
import rx.observables.BlockingObservable;

public class MainActivity extends AppCompatActivity {

    // Создаём метод, который получает строку формата JSON и формирует из неё ArrayList.
    private void jsonTostring(String data_string, ArrayList<String> data_array) throws JSONException {
        JSONArray array_js_allpb = new JSONArray(data_string); // Сначала определяем строку как JSONArray, так как ответ от Zotero представляет собой коллекцию (начинается с "[")

        for(int i=0; i<array_js_allpb.length(); i++){ // Проходим массив в цикле и определяем интересующие нас JSONObject (key и data)
            JSONObject js_item = array_js_allpb.getJSONObject(i);
            String value = js_item.getString("data"); // Получаем как строку объект data
            JSONObject js_data = new JSONObject(value); // Создаём новый JSONObject на основе полученной строки для работы со вложенным JSON-содержимым
            String[] str_data = value.replace("\"","").split(","); // Форматируем данные, убирая запятые и кавычки и заносим их в строковый массив
            String key = js_data.getString("key"); // Получаем как строку объект key

            for (String strDatum : str_data) { // Форматируем данные в массиве и выводим их

                String value1 = key + "|" + strDatum; // Используем знак "|" для разделения между ключом и самими данными
                String result = value1.replaceAll("[\\[\\](){}]", ""); // Убираем скобки
                if (result.endsWith(":")) { // Определяем пустое значение параметра и ставим вместо него прочерк
                    result = result + "-";
                }

                if (result.split(":").length == 2) { // Проверяем формат данных и вслучае его соответствия заполняем передаваемый аргументом ArrayList
                    data_array.add(result);
                }
            }
        }
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        // Определяем контроллы активности: одна button и два editText
        Button SignIn = findViewById(R.id.button_SignIn);
        EditText login_text = findViewById(R.id.LoginText);
        EditText pass_text = findViewById(R.id.PassText);

        // Для удобства заранее вносим необходимые данные для авторизации (User ID и Access Key)

            // Данные для входа в библиотеку 1.
        //login_text.setText("743083");
         // pass_text.setText("vLLtTQU1tcWCPb3Grsl6GzWi");

            // Данные для входа в библиотеку 2.
            login_text.setText("17116393");
            pass_text.setText("vrNSxghdhbqT82b1TPLsjVn5");

        // Создаём ArrayList'ы для хранения данных из ответа esponse Zotero API
        ArrayList<String> dt_Collections = new ArrayList<>(); // Для коллекций
        ArrayList<String> dt_All = new ArrayList<>(); // Для всех публикаций
        ArrayList<String> dt_Trash = new ArrayList<>(); // Для содержимого мусорки


        // Обработка нажатия кнопки
        SignIn.setOnClickListener(view -> {

            // Определяем строки, куда мы будем передавать response
            String allPublication_b;
            String trash_b;
            String collection_b;

            long USER_ID = Long.parseLong(login_text.getText().toString()); // Запоминаем User ID как содержимое поля login_text

            if ((pass_text.length() > 0) && (login_text.length() > 0)){
                try {
                    setUp(pass_text.getText().toString()); // Установка соединения

                    // Получение response для всех публикаций
                    rx.Observable<Response> Resp_All = zoteroService.getItemsNotAsAList(LibraryType.USER, USER_ID, null, null);
                    Response tes_all = BlockingObservable.from(Resp_All).last();
                    allPublication_b = new String(((TypedByteArray) tes_all.getBody()).getBytes());
                    // Получение response для мусорных публикаций
                    rx.Observable<Response> Resp_Trash = zoteroService.getTrashItems(LibraryType.USER, USER_ID, null);
                    Response tes_tr = BlockingObservable.from(Resp_Trash).last();
                    trash_b = new String(((TypedByteArray) tes_tr.getBody()).getBytes());
                    // Получение response для пользовательских коллекций
                    rx.Observable<Response> Resp_lib = zoteroService.getCollections(LibraryType.USER, USER_ID, null);
                    Response tes_li = BlockingObservable.from(Resp_lib).last();
                    collection_b = new String(((TypedByteArray) tes_li.getBody()).getBytes());

                    // Вызывая метод получаем все необходимые данные в требуемом формате и в ArrayList'ах
                    jsonTostring(allPublication_b, dt_All);
                    jsonTostring(trash_b, dt_Trash);
                    jsonTostring(collection_b, dt_Collections);

                    // Если коллекция всех публикаций не равна нуля, тогда передаём данные на другую активность и запускаем её
                    if (!dt_All.isEmpty()){
                        Intent intent = new Intent(MainActivity.this, MylibraryActivity.class);
                        intent.putExtra("login", login_text.getText().toString());
                        intent.putExtra("pass", pass_text.getText().toString());
                        intent.putStringArrayListExtra("users_lib", dt_Collections);
                        intent.putStringArrayListExtra("all_lib", dt_All);
                        intent.putStringArrayListExtra("trash_lib", dt_Trash);
                        startActivity(intent);
                    }
                // Если произошла ошибка - выводим сообщение Toast
                } catch (Exception e) {
                    Toast.makeText(MainActivity.this, "Авторизация провалена.", Toast.LENGTH_SHORT).show();
                }
            // Если поля не заполнены - выводим сообщение Toast
            } else {
                Toast.makeText(MainActivity.this, "Заполните поля для авторизации.", Toast.LENGTH_SHORT).show();
            }
        });

    }

    ZoteroService zoteroService; // Определяем внутри класса интерфейс для работы с Zotero API

    // Создаём метод установки связи с Zotero, применяя инструмсенты библиотеки retrofit
    void setUp(String accesskey) {
        try {
            RequestInterceptor authorizingInterceptor = request -> { // Добавляем заголовки headers
                request.addHeader(HttpHeaders.AUTHORIZATION, HttpHeaders.AUTHORIZATION_BEARER_X + accesskey);
                request.addHeader(HttpHeaders.ZOTERO_API_VERSION, "3");
            };

            // Собираем адаптер для работы по методологии REST
            RestAdapter adapter = new RestAdapter.Builder()
                    .setEndpoint("https://api.zotero.org")
                    .setLogLevel(RestAdapter.LogLevel.FULL)
                    .setRequestInterceptor(authorizingInterceptor)
                    .setConverter(new GsonConverter(new Gson()))
                    .build();

            // Присваиваем интерфейсу класс на основе сформированного адаптера для дальнейшей работы и отправки REST-запросов
            zoteroService = adapter.create(ZoteroService.class);
        // В случае возникновения ошибки выводим Toast о неудачной попытке авторизации
        } catch (Exception e) {
            Toast.makeText(this, "Не удалось авторизоваться.", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Стандартные процедуры
        super.onCreate(savedInstanceState);
        com.example.zoteromvp.databinding.ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

    }
}