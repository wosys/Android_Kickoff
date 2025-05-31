/*
 * Copyright 2023-2024 wintmain
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

package com.wintmain.wPlayer;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;

/**
 * @Description 有关数据处理的相关java代码，主要内容是对数据库进行创建和增删改查等。
 * 需要歌曲列表和恢复列表，它们是独立于android的生命周期的，所以需要使用到数据库
 * @Author wintmain
 * @mailto wosintmain@gmail.com
 * @Date
 */
public class DataBaseHelper extends SQLiteOpenHelper {


    public final static String DATABASE_NAME = "MyMusic.db"; //数据库名
    public final static String TABLE_NAME = "mymusic_table";//表名，用于保存歌曲信息

    public final static String COL_1 = "ID";
    public final static String COL_2 = "NAME";
    public final static String COL_3 = "SINGER_NAME";

    public DataBaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // 保存歌曲信息到数据库
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_NAME
                + " (ID INTEGER,NAME TEXT,SINGER_NAME TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // 更新表的内容
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }


    public boolean insertData(int ID, String name, String singerName) {
        // 插入数据
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COL_1, ID);
        contentValues.put(COL_2, name);
        contentValues.put(COL_3, singerName);


        long result = db.insert(TABLE_NAME, null, contentValues);
        return result != -1;
    }

    // 查询数据库
    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        // rawquery运行提供的SQL并在结果集上返回 Cursor，query提供的参数是表名
        return db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
    }

    public Integer deleteData(String id) {
        SQLiteDatabase db = this.getWritableDatabase();

        return db.delete(TABLE_NAME, "ID=?", new String[]{id});
    }


}
