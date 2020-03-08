package com.example.shad.raw_resources;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView tvMain = findViewById(R.id.tv_main);
        InputStream is = getResources().openRawResource(R.raw.shad);

        StringBuilder sb = new StringBuilder();
        sb.append("Data from shad.json").append("\n");
        sb.append(toString(is));
        sb.append("\n").append("\n");

        tvMain.setText(sb.toString());
    }

    private String toString(InputStream is) {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        try {
            for(String line = bufferedReader.readLine(); line != null; line = bufferedReader.readLine()) {
                sb.append(line);
            }
        } catch (IOException e) {

        }
        return sb.toString();
    }
}
