package com.exmaple.shad.serializableparcelable

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {

    private val catKey = "com.example.shad.cat"
    private val dogKey = "com.example.shad.dog"
    private lateinit var leia: Cat
    private lateinit var ghost: Dog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.newActivityBtn).setOnClickListener {
            startNewActivity()
        }

        leia = Cat("Leia", 7, "grey")
//        ghost = Dog("Ghost", 5, "white")
        ghost = Dog("Ghost", 5, "white", leia)
    }

    private fun startNewActivity() {
        val intent = Intent(this, AnotherActivity::class.java)
        intent.putExtra(catKey, leia)
        intent.putExtra(dogKey, ghost)
        startActivity(intent)
    }
}
