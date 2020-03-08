package com.example.shad.fileprovider

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.core.content.FileProvider
import java.io.File

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.openFileBtn).setOnClickListener { openFile() }
    }

    private fun openFile() {
        val data = FileProvider.getUriForFile(this, "com.example.shad.fileprovider.provider", File(filesDir, "texts/file.txt"))
        grantUriPermission(packageName, data, Intent.FLAG_GRANT_READ_URI_PERMISSION)
        val intent = Intent(Intent.ACTION_VIEW)
                .setDataAndType(data, "text/plain")
                .addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        startActivity(intent)
    }
}
