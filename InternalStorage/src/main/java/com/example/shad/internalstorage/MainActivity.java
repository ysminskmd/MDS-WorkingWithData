package com.example.shad.internalstorage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //*********************Cache dir************************************/
        File cacheFile = new File(getCacheDir(), "cache_file_1.txt");
        Log.i("Shad", "permanent cache file before write: " + toString(cacheFile));
        String cacheDataString = "Test cache string 1";
        writeDataToFile(cacheFile, cacheDataString);
        Log.i("Shad", "permanent cache file content after write: " + toString(cacheFile));

        String secondCacheDataString = "Test cache string 2";
        try {
            File secondCacheFile = File.createTempFile("cache_file_2", ".txt");
            Log.i("Shad", "temp cache file before write: " + toString(secondCacheFile));
            writeDataToFile(secondCacheFile, secondCacheDataString);
            Log.i("Shad", "temp cache file after write: " + toString(secondCacheFile));
        } catch (IOException e) {}

        //*******************Data dir***************************************/
        File dataFile = new File(getFilesDir(), "data_file.txt");
        Log.i("Shad", "file form files dir after write: " + toString(dataFile));

        String dataFileString = "Test data string";
        try {
            FileOutputStream fos = openFileOutput("data_file.txt", Context.MODE_PRIVATE);
            writeDataToOutputStream(fos, dataFileString);
        } catch (Exception e) {
            Log.e("Shad", "Excepion:", e);
        }

        dataFile = new File(getFilesDir(), "data_file.txt");
//        writeDataToFile(dataFile, dataFileString);

        Log.i("Shad", "file form files dir after write: " + toString(dataFile));
    }

    private void writeDataToFile(File file, String data) {
        try {
            FileOutputStream fos = new FileOutputStream(file);
            writeDataToOutputStream(fos, data);
        } catch (Exception e) {
            Log.e("Shad", "Excepion:", e);
        }
    }

    private void writeDataToOutputStream(OutputStream os, String data) {
        try {
            os.write(data.getBytes());
            os.close();
        } catch (IOException e) {}
    }

    private String toString(File file) {
        StringBuilder sb = new StringBuilder();
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader((file)));
            for(String line = bufferedReader.readLine(); line != null; line = bufferedReader.readLine()) {
                sb.append(line);
            }
        } catch (IOException e) {

        }
        return sb.toString();
    }

}
