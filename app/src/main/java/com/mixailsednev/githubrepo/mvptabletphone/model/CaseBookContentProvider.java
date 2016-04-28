package com.mixailsednev.githubrepo.mvptabletphone.model;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

public class CaseBookContentProvider extends ContentProvider {
    final String TAG = "CaseBookContentProvider";

    // // Константы для БД
    // БД
    static final String DB_NAME = "caseBookDb";
    static final int DB_VERSION = 1;

    // Таблица
    static final String CASES_TABLE = "cases";

    // Поля
    static final String CASE_ID = "_id";
    static final String CASE_TITLE = "title";
    static final String CASE_TYPE = "type";

    // Скрипт создания таблицы
    static final String DB_CREATE = "create table " + CASES_TABLE + "("
            + CASE_ID + " integer primary key autoincrement, "
            + CASE_TITLE + " text, " + CASE_TYPE + " text" + ");";

    // // Uri
    // authority
    static final String AUTHORITY = "com.mixailsednev.githubrepo.mvptabletphone.CaseBook";

    // path
    static final String CASE_PATH = "case";

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

    //// UriMatcher
    // общий Uri
    static final int URI_CASE = 1;

    // Uri с указанным ID
    static final int URI_CASE_ID = 2;

    // описание и создание UriMatcher
    private static final UriMatcher uriMatcher;
    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY, CASE_PATH, URI_CASE);
        uriMatcher.addURI(AUTHORITY, CASE_PATH + "/#", URI_CASE_ID);
    }

    DBHelper dbHelper;
    SQLiteDatabase db;

    public boolean onCreate() {
        Log.d(TAG, "onCreate");
        dbHelper = new DBHelper(getContext());
        return true;
    }

    // чтение
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        Log.d(TAG, "query, " + uri.toString());
        // проверяем Uri
        switch (uriMatcher.match(uri)) {
            case URI_CASE: // общий Uri
                Log.d(TAG, "URI_CASE");
                // если сортировка не указана, ставим свою - по имени
                if (TextUtils.isEmpty(sortOrder)) {
                    sortOrder = CASE_TITLE + " ASC";
                }
                break;
            case URI_CASE_ID: // Uri с ID
                String id = uri.getLastPathSegment();
                Log.d(TAG, "URI_CASE_ID, " + id);
                // добавляем ID к условию выборки
                if (TextUtils.isEmpty(selection)) {
                    selection = CASE_ID + " = " + id;
                } else {
                    selection = selection + " AND " + CASE_ID + " = " + id;
                }
                break;
            default:
                throw new IllegalArgumentException("Wrong URI: " + uri);
        }
        db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query(CASES_TABLE, projection, selection,
                selectionArgs, null, null, sortOrder);
        // просим ContentResolver уведомлять этот курсор
        // об изменениях данных в CASE_CONTENT_URI
        cursor.setNotificationUri(getContext().getContentResolver(),
                CASE_CONTENT_URI);
        return cursor;
    }

    public Uri insert(Uri uri, ContentValues values) {
        Log.d(TAG, "insert, " + uri.toString());
        if (uriMatcher.match(uri) != URI_CASE)
            throw new IllegalArgumentException("Wrong URI: " + uri);

        db = dbHelper.getWritableDatabase();
        long rowID = db.insert(CASES_TABLE, null, values);
        Uri resultUri = ContentUris.withAppendedId(CASE_CONTENT_URI, rowID);
        // уведомляем ContentResolver, что данные по адресу resultUri изменились
        getContext().getContentResolver().notifyChange(resultUri, null);
        return resultUri;
    }

    public int delete(Uri uri, String selection, String[] selectionArgs) {
        Log.d(TAG, "delete, " + uri.toString());
        switch (uriMatcher.match(uri)) {
            case URI_CASE:
                Log.d(TAG, "URI_CASE");
                break;
            case URI_CASE_ID:
                String id = uri.getLastPathSegment();
                Log.d(TAG, "URI_CASE_ID, " + id);
                if (TextUtils.isEmpty(selection)) {
                    selection = CASE_ID + " = " + id;
                } else {
                    selection = selection + " AND " + CASE_ID + " = " + id;
                }
                break;
            default:
                throw new IllegalArgumentException("Wrong URI: " + uri);
        }
        db = dbHelper.getWritableDatabase();
        int cnt = db.delete(CASES_TABLE, selection, selectionArgs);
        getContext().getContentResolver().notifyChange(uri, null);
        return cnt;
    }

    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        Log.d(TAG, "update, " + uri.toString());
        switch (uriMatcher.match(uri)) {
            case URI_CASE:
                Log.d(TAG, "URI_CASE");

                break;
            case URI_CASE_ID:
                String id = uri.getLastPathSegment();
                Log.d(TAG, "URI_CASE_ID, " + id);
                if (TextUtils.isEmpty(selection)) {
                    selection = CASE_ID + " = " + id;
                } else {
                    selection = selection + " AND " + CASE_ID + " = " + id;
                }
                break;
            default:
                throw new IllegalArgumentException("Wrong URI: " + uri);
        }
        db = dbHelper.getWritableDatabase();
        int cnt = db.update(CASES_TABLE, values, selection, selectionArgs);
        getContext().getContentResolver().notifyChange(uri, null);
        return cnt;
    }

    public String getType(Uri uri) {
        Log.d(TAG, "getType, " + uri.toString());
        switch (uriMatcher.match(uri)) {
            case URI_CASE:
                return CASE_CONTENT_TYPE;
            case URI_CASE_ID:
                return CASE_CONTENT_ITEM_TYPE;
        }
        return null;
    }

    private class DBHelper extends SQLiteOpenHelper {

        public DBHelper(Context context) {
            super(context, DB_NAME, null, DB_VERSION);
        }

        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DB_CREATE);
            ContentValues cv = new ContentValues();
            for (int i = 1; i <= 3; i++) {
                cv.put(CASE_TITLE, "title " + i);
                cv.put(CASE_TYPE, "type " + i);
                db.insert(CASES_TABLE, null, cv);
            }
        }

        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        }
    }
}