package com.mixailsednev.githubrepo.mvptabletphone.model;

import android.net.Uri;

public class CaseBookContentProviderContract {
    // // Uri
    // authority
    public static final String AUTHORITY = "com.mixailsednev.githubrepo.mvptabletphone.CaseBook";

    // path
    public static final String CASE_PATH = "case";

    // Таблица
    public static final String CASES_TABLE = "cases";
    // Поля
    public static final String CASE_ID = "_id";
    public static final String CASE_TITLE = "title";
    public static final String CASE_TYPE = "type";

    // Скрипт создания таблицы
    static final String DB_CREATE = "create table " + CASES_TABLE + "("
            + CASE_ID + " integer primary key autoincrement, "
            + CASE_TITLE + " text, " + CASE_TYPE + " text" + ");";

    // Общий Uri
    public static final Uri CASE_CONTENT_URI = Uri.parse("content://"
            + AUTHORITY + "/" + CASE_PATH);

    // Типы данных
    // набор строк
    static final String CASE_CONTENT_TYPE = "vnd.android.cursor.dir/vnd."
            + AUTHORITY + "." + CASE_PATH;

    // одна строка
    static final String CASE_CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd."
            + AUTHORITY + "." + CASE_PATH;
}