package com.frontier.botChat.utils;

import android.util.Log;
import com.frontier.botChat.utils.Parse.WeatherJsonResult;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GetWeather {

    private WeatherJsonResult weatherJsonResult;

    public GetWeather() {
        Gson gson = new GsonBuilder().create();
        String weather = GetRequestToJSonString.getString(Const.WEATHER_URL);
        Log.i(Const.LOG_TAG, weather);
        weatherJsonResult = gson.fromJson(weather, WeatherJsonResult.class);
    }

    public String getMessage() {
        String description = weatherJsonResult.getWeather().getDescription();
        int temp = (int) weatherJsonResult.getMain().getTemp();
        double humidity = weatherJsonResult.getMain().getHumidity();
        double windSpeed = weatherJsonResult.getWind().getSpeed();
        double direction = weatherJsonResult.getWind().getDeg();

        return "Now " + description + ", the temperature " + temp
                + " degrees Celsius, humidity " + humidity + "%, "
                + getDirection(direction) + " wind of " + windSpeed + " meters per second.";
    }

    private String getDirection(double direction) {
        if (direction < 339 || direction > 22) {
            return "north";
        }
        if (direction < 23 || direction > 67) {
            return "northeast";
        }
        if (direction < 68 || direction > 112) {
            return "east";
        }
        if (direction < 113 || direction > 157) {
            return "southeast";
        }
        if (direction < 158 || direction > 202) {
            return "south";
        }
        if (direction < 203 || direction > 247) {
            return "southwest";
        }
        if (direction < 248 || direction > 292) {
            return "west";
        }
        if (direction < 293 || direction > 338) {
            return "northwest";
        }
        return "";
    }

    public String getId() {
        return weatherJsonResult.getWeather().getIcon();
    }
}
