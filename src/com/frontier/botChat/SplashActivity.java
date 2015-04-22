package com.frontier.botChat;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import com.frontier.botChat.utils.crashReporter.CrashReporter;
import com.frontier.botChat.utils.crashReporter.Feedback;

import java.util.concurrent.TimeUnit;

public class SplashActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        CrashReporter crashReporter = new CrashReporter(this, "Error: " + getPackageName()
                + " (" + Feedback.getPackageVersion(getApplicationContext()) + ")");
        Thread.setDefaultUncaughtExceptionHandler(crashReporter);

        new LoadTask().execute();
    }

    private class LoadTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
