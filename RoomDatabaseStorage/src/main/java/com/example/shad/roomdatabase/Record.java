package com.example.shad.roomdatabase;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Record {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    public int id;

    @ColumnInfo(name = "number")
    public int number;

    @ColumnInfo(name = "description")
    public String description;

    @ColumnInfo(name = "progress")
    public double progress;

    @ColumnInfo(name = "meta")
    public byte[] meta;

    public Record(int number, String description, double progress, byte[] meta) {
        this.number = number;
        this.description = description;
        this.progress = progress;
        this.meta = meta;
    }
}
