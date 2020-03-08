package com.example.shad.roomdatabase;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.List;
import java.util.Random;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.room.Room;

public class MainActivity extends AppCompatActivity {

    private Executor mExecutor;
    private AppDatabase mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.fillDbBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fillDatabase();
            }
        });
        findViewById(R.id.getDbDataBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDataFromDb();
            }
        });
        mExecutor = Executors.newSingleThreadExecutor();
        mDb = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "database-name")
                .build();
        registerObserver();
    }

    private void fillDatabase() {
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                clearData();
                generateData();
            }
        });
    }

    private void getDataFromDb() {
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
//                List<Record> records = mDb.recordDao().getAll();
//                logRecords(records);
            }
        });
    }

    private void generateData() {
//        for (int i = 0; i < 20; i ++) {
        for (int i = 0; i < 1; i ++) {
            byte[] meta = new byte[1024];
            new Random().nextBytes(meta);
            insertRecord(new Random().nextInt(), "Record#" + i, new Random().nextDouble(), meta);
        }
    }

    private void insertRecord(int number, String title, double progress, byte[] meta) {
        mDb.recordDao().insertAll(new Record(number, title, progress, meta));
    }

    private void registerObserver() {
        LiveData<List<Record>> records = mDb.recordDao().getAll();
        records.observe(this, new Observer<List<Record>>() {
            @Override
            public void onChanged(List<Record> records) {
                logRecords(records);
            }
        });
    }

    private void logRecords(@Nullable List<Record> records) {
        if (records == null) {
            Log.i("Shad", "Records are null");
            return;
        }
        for (Record record : records) {
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
