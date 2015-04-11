package com.frontier.botChat.utils;

import com.frontier.botChat.utils.Parse.AnecdoteParse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GetAnecdote {

    private static String url = "http://rzhunemogu.ru/RandJSON.aspx?CType=1";

    public static String getAnecdote() {
        Gson gson = new GsonBuilder().create();
        return gson.fromJson(GetRequestToJSonString.getString(url), AnecdoteParse.class).getContent();
    }
}
