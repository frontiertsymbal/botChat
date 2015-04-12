package com.frontier.botChat.utils;

import com.frontier.botChat.utils.Parse.AnecdoteParse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GetAnecdote {

    public static String getAnecdote() {
        Gson gson = new GsonBuilder().create();
        return gson.fromJson(GetRequestToJSonString.getString(Const.ANECDOTE_URL), AnecdoteParse.class).getContent();
    }
}
