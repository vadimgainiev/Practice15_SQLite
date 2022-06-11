package com.example.practice15_sqlite;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.util.StringBuilderPrinter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText name, phone, dateOfBirth;
    Button insert, select, update, delete;
    String changeableName = "";
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        name = findViewById(R.id.txtName);
        phone = findViewById(R.id.txtNumber);
        dateOfBirth = findViewById(R.id.txtDate);
        insert = findViewById(R.id.btnInsert);
        update = findViewById(R.id.btnUpdate);
        delete = findViewById(R.id.btnDelete);
        select = findViewById(R.id.btnSelect);
        databaseHelper = new DatabaseHelper(this);

        insert.setOnClickListener( view -> {
            Boolean checkInsertData = databaseHelper.insert(name.getText().toString(),
                    phone.getText().toString(), dateOfBirth.getText().toString());

            if(checkInsertData) {
                Toast.makeText(getApplicationContext(),
                        "Данные успешно добавлены", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getApplicationContext(),
                        "Произошла ошибка", Toast.LENGTH_LONG).show();
            }
        });

        select.setOnClickListener( view -> {
            Cursor res = databaseHelper.getData();
            // Проверка на наличие данных
            if(res.getCount()==0){
                Toast.makeText(MainActivity.this,
                        "Нет данных", Toast.LENGTH_SHORT).show();
            }
            // Цикл для перебора и объединения данных
            StringBuilder builder = new StringBuilder();
            while(res.moveToNext()){
                builder.append("Имя: ").append(res.getString(1)).append("\n");
                builder.append("Тел. номер: ").append(res.getString(2)).append("\n");
                builder.append("Дата рождения: ").append(res.getString(3)).append("\n\n");
            }
            // Диалоговое окно
            AlertDialog.Builder builder1 = new AlertDialog.Builder(MainActivity.this);
            builder1.setCancelable(true);
            builder1.setTitle("Данные пользователей");
            builder1.setMessage(builder.toString());
            builder1.show();
        });

        update.setOnClickListener(view -> {
            if(name.getText().toString().equals("") || name.getText().toString().equals(null)) {
                Toast.makeText(getApplicationContext(),
                        "Введите имя пользователя, данные которого хотите изменить",
                        Toast.LENGTH_LONG).show();
            }
            else {
                Cursor res = databaseHelper.getData();
                if(res.getCount() == 0) {
                    Toast.makeText(getApplicationContext(),
                            "Нет данных для изменения", Toast.LENGTH_LONG).show();
                    return;
                }
                while (res.moveToNext()) {
                    if(changeableName.equals("")) {
                        changeableName = name.getText().toString();
                        Toast.makeText(getApplicationContext(),
                                "Измените данные", Toast.LENGTH_LONG).show();
                    }
                    else {
                        Boolean checkInsertData =  databaseHelper.edit(
                                changeableName,
                                name.getText().toString(),
                                phone.getText().toString(),
                                dateOfBirth.getText().toString());
                        if(checkInsertData) {
                            Toast.makeText(getApplicationContext(),
                                    "Данные успешно изменены", Toast.LENGTH_LONG).show();
                        }
                        else {
                            Toast.makeText(getApplicationContext(),
                                    "Произошла ошибка",Toast.LENGTH_LONG).show();
                        }

                        changeableName = "";
                    }
                }
            }
        });

        delete.setOnClickListener(view ->{
            Boolean checkInsertData = databaseHelper.delete(name.getText().toString());
            if(checkInsertData) {
                Toast.makeText(getApplicationContext(),
                        "Данные успешно удалены", Toast.LENGTH_LONG).show();
                name.setText("");
                phone.setText("");
                dateOfBirth.setText("");
            }
            else {
                Toast.makeText(getApplicationContext(),
                        "Произошла ошибка", Toast.LENGTH_LONG).show();
            }
        });
    }
}