/*
 * Copyright 2023-2025 wintmain
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.wintmain.wBasis.overview;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "demo_whitelist.db";
    private static final int DATABASE_VERSION = 1;
    private static final String SQL_CREATE_ENTRIES = "CREATE TABLE " + WhitelistEntry.TABLE_NAME
            + " (" + WhitelistEntry._ID + " INTEGER PRIMARY KEY,"
            + WhitelistEntry.COLUMN_PACKAGE_NAME
            + " TEXT)";
    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + WhitelistEntry.TABLE_NAME;
    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static void addToWhitelist(Context context, String packageName) {
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = null;
        try {
            db = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(WhitelistEntry.COLUMN_PACKAGE_NAME, packageName);
            db.insert(WhitelistEntry.TABLE_NAME, null, values);
        } catch (Exception e) {
            Log.e("DbHelper", "Error adding to whitelist", e);
        } finally {
            if (db != null) {
                db.close();
            }
        }
    }

    // 从白名单列表中移除包名
    public static void removeFromWhitelist(Context context, String packageName) {
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String selection = WhitelistEntry.COLUMN_PACKAGE_NAME + " = ?";
        String[] selectionArgs = {packageName};

        db.delete(WhitelistEntry.TABLE_NAME, selection, selectionArgs);
        db.close();
    }

    // 获取白名单列表
    public static List<String> getWhitelist(Context context) {
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] projection = {WhitelistEntry.COLUMN_PACKAGE_NAME};

        Cursor cursor = db.query(
                WhitelistEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null
        );

        List<String> whitelist = new ArrayList<>();
        while (cursor.moveToNext()) {
            String packageName = cursor.getString(cursor.getColumnIndexOrThrow(
                    WhitelistEntry.COLUMN_PACKAGE_NAME));
            whitelist.add(packageName);
        }

        cursor.close();
        db.close();

        return whitelist;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
        Log.d("DbHelper", "Database created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public static class WhitelistEntry implements BaseColumns {
        public static final String TABLE_NAME = "whitelist";
        public static final String COLUMN_PACKAGE_NAME = "package_name";
    }
}
