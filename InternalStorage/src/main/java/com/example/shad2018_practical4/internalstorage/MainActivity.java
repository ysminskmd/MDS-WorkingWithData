package com.example.shad2018_practical4.internalstorage;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //*********************Cache dir************************************/
        File cacheFile = new File(getCacheDir(), "cache_file_1.txt");
        String cacheDataString = "Test cache string 1";
        writeDataToFile(cacheFile, cacheDataString);

        String secondCacheDataString = "Test cache string 2";
        try {
            File secondCacheFile = File.createTempFile("cache_file_2", ".txt");
            writeDataToFile(secondCacheFile, secondCacheDataString);
        } catch (IOException e) {}

        //*******************Data dir***************************************/
        File dataFile = new File(getFilesDir(), "data_file.txt");
        String dataFileString = "Test data string";
        writeDataToFile(dataFile, dataFileString);
    }

    private void writeDataToFile(File file, String data) {
        try {
            FileOutputStream fos = new FileOutputStream(file);
            writeDataToOutputStream(fos, data);
        } catch (Exception e) {}
    }

    private void writeDataToOutputStream(OutputStream os, String data) {
        try {
            os.write(data.getBytes());
            os.close();
        } catch (IOException e) {}
    }
}
