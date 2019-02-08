package com.example.shad.roomdatabase;

import android.os.Bundle;
import android.util.Log;

import java.util.Random;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

public class MainActivity extends AppCompatActivity {

    private AppDatabase mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDb = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "database-name")
                .allowMainThreadQueries()
                .build();

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
        mDb.recordDao().insertAll(new Record(number, title, progress, meta));
    }

    private void logData() {
        for (Record record : mDb.recordDao().getAll()) {
            Log.i("Shad", String.format(
                    "DbRecord: id = %d; number = %s; title = %s; progress = %s; meta size = %s",
                    record.id,
                    record.number,
                    record.description,
                    record.progress,
                    record.meta.length
            ));
        }
    }

    private void clearData() {
        mDb.clearAllTables();
    }

}
