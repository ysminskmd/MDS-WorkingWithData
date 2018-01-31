package com.example.shad2018_practical4.externalstorage;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (isExternalStorageWritable()) {

            //Own cache files
            File ownCacheFileOnExternalStorage = new File(getExternalCacheDir(), "cache_file_1.txt");
            String cacheString = "Cache string";
            writeDataToFile(ownCacheFileOnExternalStorage, cacheString);

            //Own data file
            File dataFileOnExternalStorage = new File(getExternalFilesDir(null), "data_file_1.txt");
            String dataString = "Data string";
            writeDataToFile(dataFileOnExternalStorage, dataString);

            //Public file in documents directory
            File documentsDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
            if (documentsDirectory.exists() || documentsDirectory.mkdir()) {
                File ownDir = new File(documentsDirectory, "yandex");
                boolean result = ownDir.mkdir();
                if (result) {
                    File publicFile = new File(ownDir, "public_file_1.txt");
                    String data = "Public content";
                    writeDataToFile(publicFile, data);
                }
            }
        }
    }

    private boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
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
