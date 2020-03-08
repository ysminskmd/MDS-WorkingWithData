package com.example.shad_2018_practical4.packagemanager;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private BatteryManager mBatteryManager;
    private TextView mBatteryTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mBatteryManager = (BatteryManager) getSystemService(Context.BATTERY_SERVICE);
        }
        mBatteryTextView = findViewById(R.id.batteryTv);

        findViewById(R.id.installedAppsBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showInstalledApps();
            }
        });

        findViewById(R.id.batteryBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBatteryCapacity();
            }
        });
    }

    private void showInstalledApps() {
        PackageManager packageManager = getPackageManager();
        List<ApplicationInfo> appInfos = packageManager.getInstalledApplications(0);
        StringBuilder stringBuilder = new StringBuilder()
                .append("Installed apps:")
                .append("\n");
        Log.i("Shad", "Installed apps:");
        for (ApplicationInfo applicationInfo : appInfos) {
            Log.i("Shad", "packageName = " + applicationInfo.packageName);
            stringBuilder.append("packageName = ").append(applicationInfo.packageName).append("\n");
        }

        /**
         * https://developer.android.com/guide/components/intents-filters.html
         * https://developer.android.com/training/basics/intents/filters.html
         */
        Intent intent = new Intent(Intent.ACTION_VIEW);

        List<ResolveInfo> resolveInfos = packageManager.queryIntentActivities(intent, 0);
        stringBuilder.append("\n").append("\n").append("Resolve infos with ACTION_VIEW: ").append("\n");
        Log.i("Shad", "Resolve infos with ACTION_VIEW: ");
        for (ResolveInfo resolveInfo : resolveInfos) {
            Log.i("Shad", String.format("packageName = %s, activityName = %s",
                    resolveInfo.activityInfo.packageName, resolveInfo.activityInfo.name));
            stringBuilder.append("packageName = ")
                    .append(resolveInfo.activityInfo.packageName)
                    .append(": ")
                    .append("activityname = ")
                    .append(resolveInfo.activityInfo.name)
                    .append("\n");
        }

        TextView tvInstalledApps = findViewById(R.id.tv_installed_apps);
        tvInstalledApps.setText(stringBuilder.toString());
    }

    private void showBatteryCapacity() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            final int batteryCapacity = mBatteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY);
            mBatteryTextView.setText("Battery capacity: " + batteryCapacity);
            Log.i("Shad", "Battery capacity: " + batteryCapacity);
        }

    }
}
