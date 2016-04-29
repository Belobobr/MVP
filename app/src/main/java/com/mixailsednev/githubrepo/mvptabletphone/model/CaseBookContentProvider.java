package com.mixailsednev.githubrepo.mvptabletphone.model;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
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

    //// UriMatcher
    // общий Uri
    static final int URI_CASE = 1;

    // Uri с указанным ID
    static final int URI_CASE_ID = 2;

    // описание и создание UriMatcher
    private static final UriMatcher uriMatcher;
    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(CaseBookContentProviderContract.AUTHORITY, CaseBookContentProviderContract.CASE_PATH, URI_CASE);
        uriMatcher.addURI(CaseBookContentProviderContract.AUTHORITY, CaseBookContentProviderContract.CASE_PATH + "/#", URI_CASE_ID);
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
                    sortOrder = CaseBookContentProviderContract.CASE_TITLE + " ASC";
                }
                break;
            case URI_CASE_ID: // Uri с ID
                String id = uri.getLastPathSegment();
                Log.d(TAG, "URI_CASE_ID, " + id);
                // добавляем ID к условию выборки
                if (TextUtils.isEmpty(selection)) {
                    selection = CaseBookContentProviderContract.CASE_ID + " = " + id;
                } else {
                    selection = selection + " AND " + CaseBookContentProviderContract.CASE_ID + " = " + id;
                }
                break;
            default:
                throw new IllegalArgumentException("Wrong URI: " + uri);
        }
        db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query(CaseBookContentProviderContract.CASES_TABLE, projection, selection,
                selectionArgs, null, null, sortOrder);
        // просим ContentResolver уведомлять этот курсор
        // об изменениях данных в CASE_CONTENT_URI
        cursor.setNotificationUri(getContext().getContentResolver(),
                CaseBookContentProviderContract. CASE_CONTENT_URI);
        return cursor;
    }

    public Uri insert(Uri uri, ContentValues values) {
        Log.d(TAG, "insert, " + uri.toString());
        if (uriMatcher.match(uri) != URI_CASE)
            throw new IllegalArgumentException("Wrong URI: " + uri);

        db = dbHelper.getWritableDatabase();
        long rowID = db.insert(CaseBookContentProviderContract.CASES_TABLE, null, values);
        Uri resultUri = ContentUris.withAppendedId(CaseBookContentProviderContract.CASE_CONTENT_URI, rowID);
        // уведомляем ContentResolver, что данные по адресу resultUri изменились
        getContext().getContentResolver().notifyChange(resultUri, null);
        return resultUri;
    }

    public int bulkInsert(Uri uri, ContentValues[] values){
        int numInserted = 0;
        String table;

        int uriType = uriMatcher.match(uri);

        switch (uriType) {
            case URI_CASE:
                table = CaseBookContentProviderContract.CASES_TABLE;
                break;
            default:
                return 0;
        }
        SQLiteDatabase sqlDB = dbHelper.getWritableDatabase();
        sqlDB.beginTransaction();
        try {
            for (ContentValues cv : values) {
                long newID = sqlDB.insertOrThrow(table, null, cv);
                if (newID <= 0) {
                    throw new SQLException("Failed to insert row into " + uri);
                }
            }
            sqlDB.setTransactionSuccessful();
            getContext().getContentResolver().notifyChange(uri, null);
            numInserted = values.length;
        } catch(Exception exception) {
            numInserted = 0;
        } finally {
            sqlDB.endTransaction();
        }
        return numInserted;
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
                    selection = CaseBookContentProviderContract.CASE_ID + " = " + id;
                } else {
                    selection = selection + " AND " + CaseBookContentProviderContract.CASE_ID + " = " + id;
                }
                break;
            default:
                throw new IllegalArgumentException("Wrong URI: " + uri);
        }
        db = dbHelper.getWritableDatabase();
        int cnt = db.delete(CaseBookContentProviderContract.CASES_TABLE, selection, selectionArgs);
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
                    selection = CaseBookContentProviderContract.CASE_ID + " = " + id;
                } else {
                    selection = selection + " AND " + CaseBookContentProviderContract.CASE_ID + " = " + id;
                }
                break;
            default:
                throw new IllegalArgumentException("Wrong URI: " + uri);
        }
        db = dbHelper.getWritableDatabase();
        int cnt = db.update(CaseBookContentProviderContract.CASES_TABLE, values, selection, selectionArgs);
        getContext().getContentResolver().notifyChange(uri, null);
        return cnt;
    }

    public String getType(Uri uri) {
        Log.d(TAG, "getType, " + uri.toString());
        switch (uriMatcher.match(uri)) {
            case URI_CASE:
                return CaseBookContentProviderContract.CASE_CONTENT_TYPE;
            case URI_CASE_ID:
                return CaseBookContentProviderContract.CASE_CONTENT_ITEM_TYPE;
        }
        return null;
    }

    private class DBHelper extends SQLiteOpenHelper {

        public DBHelper(Context context) {
            super(context, DB_NAME, null, DB_VERSION);
        }

        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CaseBookContentProviderContract.DB_CREATE);
            ContentValues cv = new ContentValues();
            for (int i = 1; i <= 3; i++) {
                cv.put(CaseBookContentProviderContract.CASE_TITLE, "title " + i);
                cv.put(CaseBookContentProviderContract.CASE_TYPE, "type " + i);
                db.insert(CaseBookContentProviderContract.CASES_TABLE, null, cv);
            }
        }

        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        }
    }
}