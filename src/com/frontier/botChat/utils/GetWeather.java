package com.frontier.botChat.utils;

import com.frontier.botChat.utils.Parse.WeatherJsonResult;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GetWeather {

    private String url = "http://api.openweathermap.org/data/2.5/weather?id=709929&units=metric";
    private WeatherJsonResult weatherJsonResult;

    public GetWeather() {
        Gson gson = new GsonBuilder().create();
        String json = GetRequestToJSonString.getString(url);
        System.out.println(json);
        weatherJsonResult = gson.fromJson(json, WeatherJsonResult.class);
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
            return "west";
        }
        return "";
    }

    public String getId() {
        return weatherJsonResult.getWeather().getIcon();
    }
}
