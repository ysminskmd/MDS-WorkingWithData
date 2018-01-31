package com.example.shad_2018_practical4.packagemanager;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PackageManager packageManager = getPackageManager();
        List<ApplicationInfo> appInfos = packageManager.getInstalledApplications(0);
        StringBuilder stringBuilder = new StringBuilder()
                .append("Installed apps:")
                .append("\n");
        for (ApplicationInfo applicationInfo : appInfos) {
            stringBuilder.append("packageName = ").append(applicationInfo.packageName).append("\n");
        }

        /**
         * https://developer.android.com/guide/components/intents-filters.html
         * https://developer.android.com/training/basics/intents/filters.html
         */
        Intent intent = new Intent(Intent.ACTION_VIEW);

        List<ResolveInfo> resolveInfos = packageManager.queryIntentActivities(intent, 0);
        stringBuilder.append("\n").append("\n").append("Resolve infos with ACTION_VIEW: ").append("\n");
        for (ResolveInfo resolveInfo : resolveInfos) {
            stringBuilder.append("packageName = ")
                    .append(resolveInfo.activityInfo.packageName)
                    .append(": ")
                    .append("activityname = ")
                    .append(resolveInfo.activityInfo.name)
                    .append("\n");
        }

        TextView tvInstalledApps = findViewById(R.id.tv_installed_apps);
        tvInstalledApps.setText(stringBuilder.toString());
        Log.i("Shad", stringBuilder.toString());
    }
}
