package controllers;

import com.datastax.driver.core.*;

import java.lang.String;

import java.util.Date;
import java.sql.Timestamp;


/**
 * Created by antonis on 17-10-15.
 */
// Is the class that stores the specific elements of our cassandra table
public class weather {
    private String city;
    private Date time;
    private float temperature;
    private float humidity;
    private float rain;
    private float wind;
    private float cloud;
    private float visibility;
    private String weather;
    private String icon;



    public weather(String city,Date time, float cloud, float temperature, float wind, float humidity, float visibility, float rain, String weather) {
        this.city = city;
        this.time = time;
        this.cloud = cloud;
        this.temperature = temperature;
        this.rain = rain;
        this.humidity = humidity;
        this.wind = wind;
        this.visibility = visibility;
        this.weather = weather;
        this.icon = icon;


    }
    public weather(String city,float temperature, String icon) {
        this.city = city;
        this.icon = icon;
        this.temperature = temperature;
    }
    public weather(String city,float temperature) {
        this.city = city;
        //this.icon = icon;
        this.temperature = temperature;
    }

    public String getCity() {
        return city;
    }

    public Date getTime() {
        return time;
    }

    public float getCloud() {
        return cloud;
    }

    public float getHumidity() {
        return humidity;
    }

    public float getTemperature() {
        return temperature;
    }

    public float getRain() {
        return rain;
    }

    public float getWind() {
        return wind;
    }

    public float getVisibility() {
        return visibility;
    }
    public String getIcon(){
        return icon;
    }

    public String getWeather() {
        return weather;
    }
}


