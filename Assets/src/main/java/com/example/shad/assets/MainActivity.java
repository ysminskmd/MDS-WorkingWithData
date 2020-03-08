package com.example.shad.assets;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
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

        StringBuilder sb = new StringBuilder();
        sb.append("Data from assets/test_asset.html").append("\n");
        try {
            InputStream is = getAssets().open("shad/test_asset.html");
            sb.append(toString(is));
        } catch (IOException e) {}

        tvMain.setText(sb.toString());

        WebView webView = findViewById(R.id.web_view);
        webView.loadUrl("file:///android_asset/shad/test_asset.html");
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
