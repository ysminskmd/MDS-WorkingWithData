package com.example.shad.contentprovider.provider

import android.provider.BaseColumns

object DbConstants {

    const val NAME = "shadprovider.db"

    const val TABLE_BOOKS = "books"
    const val TABLE_STUDENTS = "students"

    const val COL_ID = BaseColumns._ID
    const val COL_NAME = "name"
    const val COL_SURNAME = "surname"
    const val COL_AUTHOR = "author"
    const val COL_YEAR = "year"

    const val CREATE_BOOKS = "CREATE TABLE $TABLE_BOOKS (" +
         "$COL_ID           INTEGER  PRIMARY KEY AUTOINCREMENT, \n" +
         "$COL_NAME     TEXT, \n" +
         "$COL_AUTHOR      TEXT, \n" +
         "$COL_YEAR      INTEGER \n" +
         ")"

    const val CREATE_STUDENTS = "CREATE TABLE $TABLE_STUDENTS (" +
         "$COL_ID           INTEGER  PRIMARY KEY AUTOINCREMENT, \n" +
         "$COL_NAME      TEXT, \n" +
         "$COL_SURNAME      TEXT, \n" +
         "$COL_YEAR      INTEGER \n" +
         ")"

    const val DROP_BOOKS = "DROP TABLE IF EXISTS $TABLE_BOOKS"

    const val DROP_STUDENTS = "DROP TABLE IF EXISTS $TABLE_STUDENTS"
}