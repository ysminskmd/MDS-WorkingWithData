package com.example.shad.roomdatabase;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface RecordDao {
    @Query("SELECT * FROM record order by id")
    List<Record> getAll();

    @Insert
    void insertAll(Record... users);

    @Delete
    void delete(Record user);
}
