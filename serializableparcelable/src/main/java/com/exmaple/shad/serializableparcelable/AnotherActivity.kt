package com.exmaple.shad.serializableparcelable

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class AnotherActivity : AppCompatActivity() {

    private val catKey = "com.example.shad.cat"
    private val dogKey = "com.example.shad.dog"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_another)

        val leia = intent.extras?.getParcelable<Cat>(catKey)
        val ghost = intent.extras?.getSerializable(dogKey) as Dog
        findViewById<TextView>(R.id.tv).text = "$leia\n$ghost"
    }
}
