package com.example.shad2018_practical4.databasestorage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    interface MyDatabase {

        static final String TABLE_NAME = "records";

        static interface Columns extends BaseColumns {
            String FIELD_NUMBER = "number";
            String FIELD_TITLE = "description";
            String FIELD_PROGRESS = "progress";
            String FIELD_META = "meta";
        }

        static final String CREATE_TABLE_SCRIPT =
                "CREATE TABLE IF NOT EXISTS " + TABLE_NAME +
                        "(" +
                        Columns.FIELD_NUMBER + " NUMBER, " +
                        Columns.FIELD_TITLE + " TEXT, " +
                        Columns.FIELD_PROGRESS + " REAL, " +
                        Columns.FIELD_META + " BLOB" +
                        ")";

        static final String DROP_TABLE_SCRIPT =
                "DROP TABLE IF EXISTS " + TABLE_NAME;
    }

    class MyDbHelper extends SQLiteOpenHelper {
        static final int VERSION = 1;
        static final String DB_NAME = "sample.db";


        public MyDbHelper(Context context) {
            super(context, DB_NAME, null, VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(MyDatabase.CREATE_TABLE_SCRIPT);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL(MyDatabase.DROP_TABLE_SCRIPT);
            onCreate(db);
        }

        @Override
        public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            onUpgrade(db, oldVersion, newVersion);
        }
    }

    private MyDbHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDbHelper = new MyDbHelper(this);
        clearData();
        generateData();
        logData();
    }

    private void generateData() {
        for (int i = 0; i < 20; i ++) {
            byte[] meta = new byte[1024];
            new Random().nextBytes(meta);
            insertRecord(new Random().nextInt(), "Record#" + i, new Random().nextDouble(), meta);
        }
    }

    private void insertRecord(int number, String title, double progress, byte[] meta) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(MyDatabase.Columns.FIELD_NUMBER, number);
        contentValues.put(MyDatabase.Columns.FIELD_TITLE, title);
        contentValues.put(MyDatabase.Columns.FIELD_PROGRESS, progress);
        contentValues.put(MyDatabase.Columns.FIELD_META, meta);
        try {
            SQLiteDatabase db = mDbHelper.getWritableDatabase();
            db.insert(MyDatabase.TABLE_NAME, null, contentValues);
        } catch (SQLiteException e) {}
    }

    private void logData() {
        try {
            SQLiteDatabase db = mDbHelper.getReadableDatabase();
            Cursor cursor = db.query(
                    //DISTINCT?
                    true,
                    MyDatabase.TABLE_NAME,
                    new String[]{MyDatabase.Columns.FIELD_NUMBER, MyDatabase.Columns.FIELD_TITLE,
                            MyDatabase.Columns.FIELD_PROGRESS, MyDatabase.Columns.FIELD_META},
                    String.format("%s > ?", MyDatabase.Columns.FIELD_NUMBER),
                    new String[]{String.valueOf(Integer.MIN_VALUE)},
                    //GROUP BY
                    null,
                    //having
                    null,
                    //ORDER BY
                    null,
                    "20"
            );

            while (cursor.moveToNext()) {
                Log.i("Shad", String.format("DbRecord: number = %s; title = %s; progress = %s; meta size = %s",
                        cursor.getInt(cursor.getColumnIndex(MyDatabase.Columns.FIELD_NUMBER)),
                        cursor.getString(cursor.getColumnIndex(MyDatabase.Columns.FIELD_TITLE)),
                        cursor.getDouble(cursor.getColumnIndex(MyDatabase.Columns.FIELD_PROGRESS)),
                        cursor.getBlob(cursor.getColumnIndex(MyDatabase.Columns.FIELD_META)).length));
            }
        } catch (SQLiteException e) {}
    }

    private void clearData() {
        try {
            SQLiteDatabase db = mDbHelper.getWritableDatabase();
            db.delete(MyDatabase.TABLE_NAME, null, null);
            db.close();
        } catch (SQLiteException e) {}
    }
}
