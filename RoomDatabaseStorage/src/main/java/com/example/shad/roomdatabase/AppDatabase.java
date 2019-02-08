package com.example.shad.roomdatabase;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Record.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract RecordDao recordDao();
}
