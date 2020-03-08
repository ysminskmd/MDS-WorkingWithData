package com.example.shad.documentprovider

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import java.io.BufferedInputStream
import java.io.OutputStream


class MainActivity : AppCompatActivity() {

    private val CODE_READ = 42
    private val CODE_WRITE = 43

    private lateinit var imageView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        imageView = findViewById(R.id.iv)
        findViewById<Button>(R.id.chooseFileBtn).setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                chooseFile()
            }
        }
        findViewById<Button>(R.id.writeToFileBtn).setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                writeToFile()
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    private fun chooseFile() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        intent.type = "image/*"
        startActivityForResult(intent, CODE_READ)
    }

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    private fun writeToFile() {
        val intent = Intent(Intent.ACTION_CREATE_DOCUMENT)
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_TITLE, "shad.txt")
        startActivityForResult(intent, CODE_WRITE)
    }

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val cr = contentResolver
        val uri = data?.data
        if (uri == null) {
            return
        }
        if (requestCode == CODE_READ) {
            try {
                cr.takePersistableUriPermission(uri, Intent.FLAG_GRANT_READ_URI_PERMISSION)
            } catch (e: SecurityException) {
                Toast.makeText(this, "No permission", Toast.LENGTH_LONG).show()
                return
            }
            var image: Bitmap? = null
            val stream = cr.openInputStream(uri)
            stream?.let {
                image = BitmapFactory.decodeStream(BufferedInputStream(it))
                it.close()
            }
            image?.let {
                imageView.setImageBitmap(it)
            }
        } else if (requestCode == CODE_WRITE) {
            try {
                cr.takePersistableUriPermission(uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
            } catch (e: SecurityException) {
                Toast.makeText(this, "FAILED TO TAKE PERMISSION", Toast.LENGTH_LONG).show()
                return
            }

            var os: OutputStream? = null
            try {
                os = cr.openOutputStream(uri)
                os?.write("Android is interesting".toByteArray())
            } catch (e: Exception) {
            } finally {
                os?.close()
            }
        }
    }
}
