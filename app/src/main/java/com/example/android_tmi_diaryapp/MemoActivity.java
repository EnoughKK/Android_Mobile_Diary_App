package com.example.android_tmi_diaryapp;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.EditText;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class MemoActivity extends SQLiteOpenHelper {

    EditText title;
    EditText content;

    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "Memo.db";

    public MemoActivity(@Nullable Context context)
    {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // 데이터베이스가 생성이 될 때 호출
        // 데이터베이스 -> 테이블 -> 컬럼 -> 값
        db.execSQL("CREATE TABLE IF NOT EXISTS MemoList (id INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT NOT NULL, content TEXT NOT NULL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }

    public ArrayList<MemoItem> getMemoItem(){
        ArrayList<MemoItem> memoItems = new ArrayList<>();

        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM MemoList ORDER BY id DESC", null);
        if(cursor.getCount() != 0){
            while (cursor.moveToNext()){
                int id = cursor.getInt(cursor.getColumnIndex("id"));
                String title = cursor.getString(cursor.getColumnIndex("title"));
                String content = cursor.getString(cursor.getColumnIndex("content"));

                MemoItem memoItem = new MemoItem();
                memoItem.setId(id);
                memoItem.setTitle(title);
                memoItem.setContent(content);
            }
        }

        cursor.close();
        return memoItems;
    }

    // INSERT 문
    public void InsertMemo(String _title, String _content){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("INSERT INTO MemoList (name, number) VALUES('" + _title + "', '"+ _content +"');");
    }

    // UPDATE 문
    public void UpdateMemo(String _title, String _content, int _id){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("UPDATE MemoList SET title='" + _title +"', content='"+ _content +"' WHERE id='" + _id + "'");
    }

    // DELETE 문
    public  void DeleteMemo(int _id){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM MemoList WHERE id = '"+ _id + "'");
    }
}