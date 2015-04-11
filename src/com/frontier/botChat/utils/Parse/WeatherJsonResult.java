package com.frontier.botChat.utils.Parse;

import java.util.List;

public class WeatherJsonResult {

    private List<Weather> weather;
    private WeatherTemp main;
    private WeatherWind wind;

    public Weather getWeather() {
        return weather.get(0);
    }

    public WeatherTemp getMain() {
        return main;
    }

    public WeatherWind getWind() {
        return wind;
    }

}
