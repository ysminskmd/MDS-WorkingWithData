package com.example.shad.contentprovideruser

import android.content.ContentValues
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {

    private val BOOKS_URI = Uri.parse("content://com.example.shad.contentprovider.provider/books")
    private val STUDENTS_URI = Uri.parse("content://com.example.shad.contentprovider.provider/students")
    private val COLUMN_NAME = "name"
    private val COLUMN_SURNAME = "surname"
    private val COLUMN_AUTHOR = "author"
    private val COLUMN_YEAR = "year"

    private lateinit var textView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        textView = findViewById(R.id.tv)
        findViewById<Button>(R.id.showBooksBtn).setOnClickListener { requestBooks() }
        findViewById<Button>(R.id.showStudentsBtn).setOnClickListener { requestStudents() }
        findViewById<Button>(R.id.modifyBooksBtn).setOnClickListener { modifyBooks() }
        findViewById<Button>(R.id.modifyStudentsBtn).setOnClickListener { modifyStudents() }
    }

    private fun requestBooks() {
        val cursor = contentResolver.query(BOOKS_URI, null, null, null, null)
        val stringBuilder = StringBuilder().append("Books: ").append("\n")
        cursor?.use { cursor ->
            while (cursor.moveToNext()) {
                val name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME))
                val author = cursor.getString(cursor.getColumnIndex(COLUMN_AUTHOR))
                val year = cursor.getInt(cursor.getColumnIndex(COLUMN_YEAR))

                stringBuilder.append("book: name = ").append(name).append("; ")
                        .append("author = ").append(author).append(";")
                        .append("year = ").append(year).append("\n").append("\n")
            }
        }
        textView.text = stringBuilder.toString()
    }

    private fun requestStudents() {
        val cursor = contentResolver.query(STUDENTS_URI, null, null, null, null)
        val stringBuilder = StringBuilder().append("Students: ").append("\n")
        cursor?.use { cursor ->
            while (cursor.moveToNext()) {
                val name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME))
                val surname = cursor.getString(cursor.getColumnIndex(COLUMN_SURNAME))
                val year = cursor.getInt(cursor.getColumnIndex(COLUMN_YEAR))

                stringBuilder.append("student: name = ").append(name).append("; ")
                        .append("surname = ").append(surname).append(";")
                        .append("year = ").append(year).append("\n").append("\n")
            }
        }
        textView.text = stringBuilder.toString()
    }

    private fun modifyBooks() {
        val book = ContentValues()
        book.put(COLUMN_NAME, "The Hitchhiker's Guide to the Galaxy")
        book.put(COLUMN_AUTHOR, "Douglas Adams")
        book.put(COLUMN_YEAR, 1979)
        contentResolver.insert(BOOKS_URI, book)
    }

    private fun modifyStudents() {
        val student = ContentValues()
        student.put(COLUMN_NAME, "Howard")
        student.put(COLUMN_SURNAME, "Wolowitz")
        student.put(COLUMN_YEAR, 1)
        contentResolver.insert(STUDENTS_URI, student)
    }
}
