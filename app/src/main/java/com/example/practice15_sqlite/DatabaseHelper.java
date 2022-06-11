package com.example.practice15_sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    public DatabaseHelper (Context context) {
        // Наименование БД Version >= 1
        super(context, "Userdata.bd", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // Запрос на создание таблицы в БД
        sqLiteDatabase.execSQL("create table UserInfo(" +
                "id INTEGER NOT NULL primary key AUTOINCREMENT UNIQUE, " +
                "name TEXT, phone TEXT, date_of_birth TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // Условие на удаление таблицы UserInfo
        sqLiteDatabase.execSQL("drop Table if exists UserInfo");
    }

    /**
      Метод добавления данных пользовтеля в таблицу UserInfo
      @param name - Имя
     * @param phone - Номер телефона
     * @param date_of_birth - Дата рождения
     * @return - Возвращаем true/false (зависит от результата)
     */

    public Boolean insert(String name, String phone, String date_of_birth) {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("phone", phone);
        contentValues.put("date_of_birth", date_of_birth);
        long result = DB.insert("UserInfo", null, contentValues);
        return result != -1;
    }

    //Обновление данных таблицы
    public Boolean edit(String name, String changed_name, String changed_phone,
                        String changed_date_of_birth) {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", changed_name);
        contentValues.put("phone", changed_phone);
        contentValues.put("date_of_birth", changed_date_of_birth);
        int result = DB.update("UserInfo", contentValues,
                "name = " + "'" + name + "'", null);
        DB.close();
        return result > 0;
    }

    //Удаление данныз из таблицы
    public Boolean delete(String name) {
        SQLiteDatabase DB = this.getWritableDatabase();
        int result = DB.delete("UserInfo",
                "name = " + "'" + name + "'", null);
        DB.close();
        return result > 0;
    }

    /**
     * Выполняем запрос Select для выборки всех данных из БД
     * @return
     */

    public Cursor getData() {
        SQLiteDatabase DB = this.getWritableDatabase();
        return DB.rawQuery("Select * from UserInfo", null);
    }

}
