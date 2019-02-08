package com.example.shad.externalstorage;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
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
            if (writeExternalStoragePermissionGranted()) {
                writeFileToPublicDirectory();
            } else {
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 0 && writeExternalStoragePermissionGranted()) {
            writeFileToPublicDirectory();
        }
    }

    private boolean writeExternalStoragePermissionGranted() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
    }

    private void writeFileToPublicDirectory() {
        File documentsDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
        if (documentsDirectory.exists() || documentsDirectory.mkdir()) {
            File ownDir = new File(documentsDirectory, "yandex");
            boolean result = ownDir.mkdir();
            if (result) {
                File publicFile = new File(ownDir, "public_file_1.txt");
                writeDataToFile(publicFile, "Public content");
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void writeDataToOutputStream(OutputStream os, String data) {
        try {
            os.write(data.getBytes());
            os.close();
        } catch (IOException e) {}
    }
}
