package com.frontier.botChat.utils;

import android.util.Log;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class GetRequestToJSonString {

    private static HttpURLConnection urlConnection;
    private static String jSonString;

    public static String getString(String urlString) {
        try {
            URL url = new URL(urlString);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setDoInput(true);
            urlConnection.setDoOutput(true);
            if (urlConnection.getResponseCode() == 200) {
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                jSonString = readStream(in);
                in.close();
            } else {
                Log.e(Const.LOG_TAG, "The server responded with " + urlConnection.getResponseCode());
                return null;
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            urlConnection.disconnect();
        }
        return jSonString;
    }

    private static String readStream(InputStream in) {
        BufferedReader reader = null;
        String request = "";
        try {
            reader = new BufferedReader(new InputStreamReader(in, "windows-1251"));
            String line = "";
            while ((line = reader.readLine()) != null) {
                request += line;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return request;
    }
}
