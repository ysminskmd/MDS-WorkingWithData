package com.example.shad2018_practical4.preferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;

import java.util.Arrays;
import java.util.HashSet;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences applicationPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = applicationPreferences.edit();
        editor.putString("string_key", "application preferences")
                .putBoolean("boolean_key", true)
                .putFloat("float_key", 23423432.342f)
                .putInt("int_value", 23434)
                .putLong("long_value", 2342311224L)
                .putStringSet("string_set_value", new HashSet(Arrays.asList("first_value", "second_value")))
                .commit();

        SharedPreferences defaultPreferences = getPreferences(Context.MODE_PRIVATE);
        defaultPreferences.edit().putString("key", "activity preferences").commit();

        SharedPreferences customPreferences = getSharedPreferences("com.example.shad2018_practical4.preferences",
                Context.MODE_PRIVATE);
        customPreferences.edit().putString("key", "Custom preferences").commit();
    }
}
