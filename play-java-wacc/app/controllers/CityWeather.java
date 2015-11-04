package controllers;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Antonis
 */

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Timestamp;


public class CityWeather {
    Timestamp datetime;
    String cityName;
    String time;
    String date;
    float cloudCover;
    float tempC;
    float humidity;
    float precipMM;
    float visibility;
    float windSpeedKmph;
    String weatherValue;
    String weatherIcon;
    public static CityWeather instance = null;

    CityWeather(Reader reader) {
        analyze(reader);
    }

    CityWeather(String cityName) throws IOException {

        this.cityName = cityName;

        URL url = new URL("http://api.worldweatheronline.com/premium/v1/weather.ashx?q=" + cityName + "&format=json&num_of_days=5&key=ede3d13e04df6214e3bb504c0a94e");
        URLConnection urlConnection = url.openConnection();

        analyze(new BufferedReader(new InputStreamReader(urlConnection.getInputStream())));

    }


    private void analyze(Reader reader) {
        int yyyy;
        int mm;
        int dd;
        int HH;
        int MM;

        JsonElement read = new JsonParser().parse(reader);
        JsonElement data = read.getAsJsonObject().get("data");
        JsonElement current = data.getAsJsonObject().get("current_condition");


        cloudCover = current.getAsJsonArray().get(0).getAsJsonObject().get("cloudcover").getAsFloat();
        tempC = current.getAsJsonArray().get(0).getAsJsonObject().get("temp_C").getAsFloat();
        time = current.getAsJsonArray().get(0).getAsJsonObject().get("observation_time").getAsString();
        humidity = current.getAsJsonArray().get(0).getAsJsonObject().get("humidity").getAsFloat();
        precipMM = current.getAsJsonArray().get(0).getAsJsonObject().get("precipMM").getAsFloat();
        visibility = current.getAsJsonArray().get(0).getAsJsonObject().get("visibility").getAsFloat();
        windSpeedKmph = current.getAsJsonArray().get(0).getAsJsonObject().get("windspeedKmph").getAsFloat();

        JsonElement weatherdate = data.getAsJsonObject().get("weather");
        // Manipulating time and date !!!!!
        date = weatherdate.getAsJsonArray().get(0).getAsJsonObject().get("date").getAsString();
        yyyy = Integer.parseInt(date.substring(0, 4));
        mm = Integer.parseInt(date.substring(5, 7));
        dd = Integer.parseInt(date.substring(8));
        HH = Integer.parseInt(time.substring(0, 2));
        MM = Integer.parseInt(time.substring(3, 5));
        yyyy = yyyy - 1900;
        mm = mm - 1;
        if (time.substring(6, 7).equals("P")) {
            HH = HH + 13;
            if (HH > 23) {
                HH = HH - 24;
            }
        }
        datetime = new Timestamp(yyyy, mm, dd, HH, MM, 0, 0);
        System.out.println(datetime);
        // Manipulating time and date !!!!

        JsonElement weather = current.getAsJsonArray().get(0).getAsJsonObject().get("weatherDesc");
        weatherValue = weather.getAsJsonArray().get(0).getAsJsonObject().get("value").getAsString();

        JsonElement weatherUrl = current.getAsJsonArray().get(0).getAsJsonObject().get("weatherIconUrl");
        weatherIcon = weatherUrl.getAsJsonArray().get(0).getAsJsonObject().get("value").getAsString();

    }

    public void results() {
        System.out.println("cloudCover : " + cloudCover);
        System.out.println("temperature (celsius) : " + tempC);
        System.out.println("humidity : " + humidity);
        System.out.println("precipMM : " + precipMM);
        System.out.println("visibility : " + visibility);
        System.out.println("wind speed (kmph) : " + windSpeedKmph);
        System.out.println("weather : " + weatherValue);
        System.out.println("weather icon : " + weatherIcon);
        System.out.println("time : " + time);
        System.out.println("date : " + date);

    }
}
