package com.frontier.botChat.utils.crashReporter;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Environment;
import com.frontier.botChat.R;
import com.frontier.botChat.utils.Const;

import java.util.Iterator;

public class Feedback {

    public static void addSystemInfoTo(StringBuilder report) {
        report.append("-------------------------------\n\n");
        report.append("System info:\n");
        report.append("Model:" + Build.MODEL + "\n");
        report.append("Display:" + Build.DISPLAY + "\n");
        report.append("Release:" + Build.VERSION.RELEASE + "\n");
        report.append("Sdk:" + Build.VERSION.SDK_INT + "\n");
        int memory = (int) (Runtime.getRuntime().totalMemory() / 1024);
        report.append("Total memory:" + memory + "K\n");
        report.append("Storage state:" + Environment.getExternalStorageState() + "\n");
    }

    public static void addIntentInfoTo(StringBuilder report, Intent intent) {
        if (intent == null) {
            // Nothing to do.
            return;
        }
        report.append("\n");
        report.append("Intent:\n");
        report.append("Package:").append(intent.getComponent().getPackageName()).append("\n");
        report.append("Class:").append(intent.getComponent().getClassName()).append("\n");
        report.append("\n");
        if (intent.getExtras() != null) {
            Iterator<String> iterator = intent.getExtras().keySet().iterator();
            while (iterator.hasNext()) {
                String key = iterator.next();
                report.append(key).append(":");
                // Skip some extras.
                if ("html".equals(key) ||
                        key.contains("payment")) {
                    report.append("X\n");
                    continue;
                }
                report.append(String.valueOf(intent.getExtras().get(key)))
                        .append("\n");
            }
        }
    }

    public static String getPackageVersion(Context context) {
        try {
            // This should be the same as the package from AndroidManifest.xml
            String applicationPackage = R.class.getPackage().getName();
            return context.getPackageManager().getPackageInfo(applicationPackage, 0).versionName;
        } catch (Exception e) {
            return "UNKNOWN";
        }
    }

    public static void sendEmail(Context context, String title, String subject, String body) {
        Intent sendIntent = new Intent(Intent.ACTION_SEND);
        sendIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        sendIntent.putExtra(Intent.EXTRA_EMAIL, Const.CRASH_REPORTS_EMAILS);
        sendIntent.putExtra(Intent.EXTRA_TEXT, body);
        sendIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
        sendIntent.setType("message/rfc822");
        Intent chooseIntent = Intent.createChooser(sendIntent, title);
        context.startActivity(chooseIntent);
    }

}
