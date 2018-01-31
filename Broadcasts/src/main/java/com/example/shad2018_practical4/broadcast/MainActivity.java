package com.example.shad2018_practical4.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private BroadcastReceiver mMonitor = new BroadcastReceiver() {
        @Override
        public void onReceive(final Context context, final Intent intent) {
            String action = intent.getAction();
            String data = intent.getDataString();
            StringBuilder logMessage = new StringBuilder()
                    .append(">").append(action)
                    .append(" for package = ").append(data)
                    .append("\n\n");
            mPackageLog.append(logMessage);
            mTextView.setText(mPackageLog.toString());
            Log.i("Shad", logMessage.toString());
        }
    };

    private TextView mTextView;
    private StringBuilder mPackageLog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTextView = findViewById(R.id.tv_main);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mPackageLog = new StringBuilder().append("Packages log: ").append("\n\n");
        mTextView.setText(mPackageLog.toString());

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_PACKAGE_ADDED);
        intentFilter.addAction(Intent.ACTION_PACKAGE_REPLACED);
        intentFilter.addAction(Intent.ACTION_PACKAGE_REMOVED);
        intentFilter.addDataScheme("package");
        registerReceiver(mMonitor, intentFilter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(mMonitor);
    }
}
