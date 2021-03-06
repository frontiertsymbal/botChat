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
        if (anecdote != null) {
            Log.i(Const.LOG_TAG, anecdote);
        } else {
            Log.e(Const.LOG_TAG, "Anecdote json null string.");
        }
        try {
            return gson.fromJson(anecdote, AnecdoteParse.class).getContent();
        } catch (JsonSyntaxException e) {
            Log.e(Const.LOG_TAG, "Anecdote server error");
        }
        return "Server error. Try again.";
    }
}
