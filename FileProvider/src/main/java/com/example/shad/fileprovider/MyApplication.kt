package com.example.shad.fileprovider

import android.app.Application
import java.io.File

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        val textsDir = File(filesDir, "texts")
        if (textsDir.exists() == false) {
            textsDir.mkdir()
        }
        val file = File(textsDir, "file.txt")
        if (file.exists() == false) {
            file.createNewFile()
        }
        file.writeText("File providers are cool!")
    }
}