package com.example.shad.contentprovider.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.database.Cursor
import android.net.Uri
import android.content.UriMatcher
import android.database.sqlite.SQLiteQueryBuilder
import android.content.ContentUris
import android.database.SQLException
import android.text.TextUtils

class MyContentProvider : ContentProvider() {

    companion object {

        private const val BOOKS_LIST = 1
        private const val BOOKS_ID = 2
        private const val STUDENTS_LIST = 3
        private const val STUDENTS_ID = 4

        private val URI_MATCHER: UriMatcher = UriMatcher(UriMatcher.NO_MATCH)

        init {
            URI_MATCHER.addURI(MyContract.AUTHORITY, "books", BOOKS_LIST)
            URI_MATCHER.addURI(MyContract.AUTHORITY, "books/#", BOOKS_ID)
            URI_MATCHER.addURI(MyContract.AUTHORITY, "students", STUDENTS_LIST)
            URI_MATCHER.addURI(MyContract.AUTHORITY, "students/#", STUDENTS_ID)
        }
    }

    private lateinit var helper: MySQLiteOpenHelper

    override fun onCreate(): Boolean {
        helper = MySQLiteOpenHelper(context)
        return true
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        val matchedUri = URI_MATCHER.match(uri)
        if (matchedUri != BOOKS_LIST && matchedUri != STUDENTS_LIST) {
            throw IllegalArgumentException("Unsupported URI for insertion: $uri")
        }
        val db = helper.writableDatabase
        when(matchedUri) {
            BOOKS_LIST -> {
                val id = db.insert(DbConstants.TABLE_BOOKS, null, values)
                return getUriForId(id, uri)
            }
            else -> {
                val id = db.insert(DbConstants.TABLE_STUDENTS, null, values)
                return getUriForId(id, uri)
            }
        }
    }

    private fun getUriForId(id: Long, uri: Uri): Uri {
        if (id > 0) {
            return ContentUris.withAppendedId(uri, id)
        }
        throw SQLException("Problem while inserting into uri: $uri")
    }

    override fun query(uri: Uri, projection: Array<out String>?, selection: String?, selectionArgs: Array<out String>?, sortOrder: String?): Cursor? {
        val db = helper.readableDatabase
        val builder = SQLiteQueryBuilder()
        when(URI_MATCHER.match(uri)) {
            BOOKS_LIST -> builder.tables = DbConstants.TABLE_BOOKS
            BOOKS_ID -> {
                builder.tables = DbConstants.TABLE_BOOKS
                builder.appendWhere("${DbConstants.COL_ID} = ${uri.lastPathSegment}")
            }
            STUDENTS_LIST -> builder.tables = DbConstants.TABLE_STUDENTS
            STUDENTS_ID -> {
                builder.tables = DbConstants.TABLE_STUDENTS
                builder.appendWhere("${DbConstants.COL_ID} = ${uri.lastPathSegment}")
            }
            else -> throw IllegalArgumentException("Unsupported uri: $uri")
        }
        return builder.query(db, projection, selection, selectionArgs, null, null, sortOrder)
    }


    override fun update(uri: Uri, values: ContentValues?, selection: String?, selectionArgs: Array<out String>?): Int {
        val db = helper.writableDatabase
        var updateCount = 0
        when (URI_MATCHER.match(uri)) {
            BOOKS_LIST -> updateCount = db.update(DbConstants.TABLE_BOOKS, values, selection, selectionArgs)
            BOOKS_ID -> {
                var where = "${DbConstants.COL_ID} = ${uri.lastPathSegment}"
                if (!TextUtils.isEmpty(selection)) {
                    where += " AND $selection"
                }
                updateCount = db.update(DbConstants.TABLE_BOOKS, values, where, selectionArgs)
            }
            else -> throw IllegalArgumentException("Unsupported URI: $uri")
        }
        return updateCount
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        val db = helper.writableDatabase
        var delCount = 0
        when (URI_MATCHER.match(uri)) {
            BOOKS_LIST -> delCount = db.delete(DbConstants.TABLE_BOOKS, selection, selectionArgs)
            BOOKS_ID -> {
                var where = "${DbConstants.COL_ID} = ${uri.lastPathSegment}"
                if (!TextUtils.isEmpty(selection)) {
                    where += " AND $selection"
                }
                delCount = db.delete(DbConstants.TABLE_BOOKS, where, selectionArgs)
            }
            else -> throw IllegalArgumentException("Unsupported URI: $uri")
        }
        return delCount
    }

    override fun getType(uri: Uri): String? {
        return when (URI_MATCHER.match(uri)) {
            BOOKS_LIST -> MyContract.Books.CONTENT_TYPE
            BOOKS_ID -> MyContract.Books.CONTENT_ITEM_TYPE
            STUDENTS_LIST -> MyContract.Students.CONTENT_TYPE
            STUDENTS_ID -> MyContract.Students.CONTENT_ITEM_TYPE
            else -> throw IllegalArgumentException("Unsupported URI: $uri")
        }
    }

}