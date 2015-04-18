package com.frontier.botChat.utils;

import android.util.Log;
import com.frontier.botChat.utils.Parse.AnecdoteParse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

public class GetAnecdote {

    public static String getAnecdote() {
        Gson gson = new GsonBuilder().create();
        String anecdote = GetRequestToJSonString.getString(Const.ANECDOTE_URL);
        Log.i(Const.LOG_TAG, anecdote);
        try {
            return gson.fromJson(anecdote, AnecdoteParse.class).getContent();
        } catch (JsonSyntaxException e) {
            Log.e(Const.LOG_TAG, "Error parsing json string");
        }
        return "Server error. Try again.";
    }
}
