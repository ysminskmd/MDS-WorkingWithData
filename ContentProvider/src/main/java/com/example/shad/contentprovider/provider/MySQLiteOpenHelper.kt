package com.example.shad.contentprovider.provider

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class MySQLiteOpenHelper(context: Context?) : SQLiteOpenHelper(context, DbConstants.NAME, null, 1) {

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(DbConstants.CREATE_BOOKS)
        db?.execSQL(DbConstants.CREATE_STUDENTS)
        val studentSheldon = ContentValues()
        studentSheldon.put(DbConstants.COL_NAME, "Sheldon")
        studentSheldon.put(DbConstants.COL_SURNAME, "Cooper")
        studentSheldon.put(DbConstants.COL_YEAR, 4)

        val studentLeonard = ContentValues()
        studentLeonard.put(DbConstants.COL_NAME, "Leonard")
        studentLeonard.put(DbConstants.COL_SURNAME, "Hofstadter")
        studentLeonard.put(DbConstants.COL_YEAR, 3)
        db?.insert(DbConstants.TABLE_STUDENTS, null, studentSheldon)
        db?.insert(DbConstants.TABLE_STUDENTS, null, studentLeonard)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL(DbConstants.DROP_BOOKS)
        db?.execSQL(DbConstants.DROP_STUDENTS)
        onCreate(db);
    }



}