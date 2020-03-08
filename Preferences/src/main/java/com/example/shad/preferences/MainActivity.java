package com.example.shad.preferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private final int n = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.commitBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveDataToPreferencesCommit();
            }
        });
        findViewById(R.id.applyBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveDataToPreferencesApply();
            }
        });
    }

    private void saveDataToPreferencesCommit() {
        long totalTime = 0;
        SharedPreferences applicationPreferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        SharedPreferences.Editor editor = applicationPreferences.edit();
        for (int i = 0; i < 100; i++) {
            editor = putSeveralStrings(editor, 100)
                    .putBoolean("boolean_key", true)
                    .putFloat("float_key", 23423432.342f)
                    .putInt("int_value", 23434)
                    .putLong("long_value", 2342311224L)
                    .putStringSet("string_set_value", new HashSet(Arrays.asList("first_value", "second_value")));
            long start = System.currentTimeMillis();
            editor.commit();
            totalTime += System.currentTimeMillis() - start;
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        Log.i("Shad", "Commit time: " + totalTime + " millis");

        SharedPreferences defaultPreferences = getPreferences(Context.MODE_PRIVATE);
        defaultPreferences.edit().putString("key", "activity preferences").commit();

        SharedPreferences customPreferences = getSharedPreferences("com.example.shad2018_practical4.preferences",
                Context.MODE_PRIVATE);
        customPreferences.edit().putString("key", "Custom preferences").commit();
    }

    private void saveDataToPreferencesApply() {
        long totalTime = 0;
        SharedPreferences applicationPreferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        SharedPreferences.Editor editor = applicationPreferences.edit();
        for (int i = 0; i < 100; i++) {
            editor = putSeveralStrings(editor, 100)
                    .putBoolean("boolean_key", true)
                    .putFloat("float_key", 23423432.342f)
                    .putInt("int_value", 23434)
                    .putLong("long_value", 2342311224L)
                    .putStringSet("string_set_value", new HashSet(Arrays.asList("first_value", "second_value")));
            long start = System.currentTimeMillis();
            editor.apply();
            totalTime += System.currentTimeMillis() - start;
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        Log.i("Shad", "Apply time: " + totalTime + " millis");
    }

    private SharedPreferences.Editor putSeveralStrings(@NonNull SharedPreferences.Editor editor, final int n) {
        Random random = new Random();
        for (int i = 0; i < n; i++) {
            editor.putString("string_key_" + random.nextInt(), "string_value_" + random.nextInt());
        }
        return editor;
    }
}
