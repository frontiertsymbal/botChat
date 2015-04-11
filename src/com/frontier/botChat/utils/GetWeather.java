package com.frontier.botChat.utils;

import com.frontier.botChat.utils.Parse.WeatherJsonResult;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GetWeather {

    private static String url = "http://api.openweathermap.org/data/2.5/weather?id=709929&lang=ru&units=metric";

    private String imageUrl = "http://openweathermap.org/img/w/10d.png";

    public static String getWeather() {
        Gson gson = new GsonBuilder().create();
        String ret = GetRequestToJSonString.getString(url);
        System.out.println(ret);
        WeatherJsonResult weatherJsonResult = gson.fromJson(ret, WeatherJsonResult.class);

        String description = weatherJsonResult.getWeather().getDescription();
        double temp = weatherJsonResult.getMain().getTemp();
        int humidity = weatherJsonResult.getMain().getHumidity();
        double windSpeed = weatherJsonResult.getWind().getSpeed();
        double direction = weatherJsonResult.getWind().getDeg();


        //TODO
        return null;
    }

}
