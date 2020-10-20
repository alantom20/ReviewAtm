package com.home.reviewatm;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class ExpenseHelper extends SQLiteOpenHelper {
    public ExpenseHelper(Context context){
        this(context,"atm",null,1);
    }

    private ExpenseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE expense(_id INTEGER PRIMARY KEY NOT NULL," +
                "cdate VACHAR NOT NULL," +
                "info VACHAR,amount INTEGER)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
