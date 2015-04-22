package com.frontier.botChat.utils.crashReporter;

import android.content.Context;
import android.content.Intent;
import com.frontier.botChat.utils.Const;

import java.io.*;
import java.lang.Thread.UncaughtExceptionHandler;

public class CrashReporter implements UncaughtExceptionHandler {

    private static Intent lastIntent = null;

    private final UncaughtExceptionHandler defaultUEH;
    private final Context applicationContext;
    private final String subject;

    public CrashReporter(Context context, String subject) {
        this.defaultUEH = Thread.getDefaultUncaughtExceptionHandler();
        this.applicationContext = context.getApplicationContext();
        this.subject = subject;
        maybeSendMail(context);
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        StringBuilder report = new StringBuilder();
        addStackTraceTo(report, e);
        Feedback.addSystemInfoTo(report);
        Feedback.addIntentInfoTo(report, lastIntent);

        try {
            FileOutputStream trace = applicationContext.openFileOutput(
                    Const.REPORT_FILE_NAME, Context.MODE_PRIVATE);
            trace.write(report.toString().getBytes());
            trace.close();
        } catch (IOException ioe) {
            e.printStackTrace();
        }

        defaultUEH.uncaughtException(t, e);
    }

    private void addStackTraceTo(StringBuilder report, Throwable e) {
        StackTraceElement[] arr = e.getStackTrace();
        report.append(e.toString() + "\n\n");
        report.append("--------- Stack trace ---------\n\n");
        for (StackTraceElement anArr : arr) {
            report.append("    " + anArr.toString() + "\n");
        }
        report.append("-------------------------------\n\n");

        // If the exception was thrown in a background thread inside
        // AsyncTask, then the actual exception can be found with getCause
        report.append("--------- Cause ---------\n\n");
        Throwable cause = e.getCause();
        if (cause != null) {
            report.append(cause.toString() + "\n\n");
            arr = cause.getStackTrace();
            for (StackTraceElement anArr : arr) {
                report.append("    " + anArr.toString() + "\n");
            }
        }
    }

    private void maybeSendMail(Context context) {
        StringBuilder crashReport = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(applicationContext.openFileInput(Const.REPORT_FILE_NAME)));
            String line;
            while ((line = reader.readLine()) != null) {
                crashReport.append(line).append("\n");
            }
        } catch (FileNotFoundException fnfe) {
            return;
        } catch (IOException ioe) {
            return;
        }

        String body = "Oh noes! This is unfortunate, but it seems last time you tried this app it crashed. " +
                "You would really help us fix that if you send this error report back to us, which " +
                " should be as simple as sending off this email. Thanks!\n\n" +
                crashReport + "\n\n";
        try {
            Feedback.sendEmail(context, "Send feedback", subject, body);
        } finally {
            // Doesn't matter if we successfully sent the email or failed,
            // we have to delete the crash report file.
            applicationContext.deleteFile(Const.REPORT_FILE_NAME);
        }
    }
}