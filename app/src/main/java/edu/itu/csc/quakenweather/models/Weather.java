package edu.itu.csc.quakenweather.models;

import android.graphics.Bitmap;

/**
 * Created by Andrii Stasenko on 3/4/2018.
 */

public class Weather {

    private String city;
    private int date;
    private double morningTemperature;
    private double dayTemperature;
    private double eveningTemperature;
    private double nightTemperature;
    private double pressure;
    private int humidity;
    private double windSpeed;
    private String weather;
    private Bitmap icon;

    public Weather(String city, int date, double morningTemperature, double dayTemperature, double eveningTemperature, double nightTemperature, double pressure, int humidity, double windSpeed, String weather, Bitmap icon) {
        this.city = city;
        this.date = date;
        this.morningTemperature = morningTemperature;
        this.dayTemperature = dayTemperature;
        this.eveningTemperature = eveningTemperature;
        this.nightTemperature = nightTemperature;
        this.pressure = pressure;
        this.humidity = humidity;
        this.windSpeed = windSpeed;
        this.weather = weather;
        this.icon = icon;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public double getMorningTemperature() {
        return morningTemperature;
    }

    public void setMorningTemperature(double morningTemperature) {
        this.morningTemperature = morningTemperature;
    }

    public double getDayTemperature() {
        return dayTemperature;
    }

    public void setDayTemperature(double dayTemperature) {
        this.dayTemperature = dayTemperature;
    }

    public double getEveningTemperature() {
        return eveningTemperature;
    }

    public void setEveningTemperature(double eveningTemperature) {
        this.eveningTemperature = eveningTemperature;
    }

    public double getNightTemperature() {
        return nightTemperature;
    }

    public void setNightTemperature(double nightTemperature) {
        this.nightTemperature = nightTemperature;
    }

    public double getPressure() {
        return pressure;
    }

    public void setPressure(double pressure) {
        this.pressure = pressure;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(double windSpeed) {
        this.windSpeed = windSpeed;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public Bitmap getIcon() {
        return icon;
    }

    public void setIcon(Bitmap icon) {
        this.icon = icon;
    }

    @Override
    public String toString() {
        return "Weather{" +
                "city='" + city + '\'' +
                ", date=" + date +
                ", morningTemperature=" + morningTemperature +
                ", dayTemperature=" + dayTemperature +
                ", eveningTemperature=" + eveningTemperature +
                ", nightTemperature=" + nightTemperature +
                ", pressure=" + pressure +
                ", humidity=" + humidity +
                ", windSpeed=" + windSpeed +
                ", weather='" + weather + '\'' +
                ", icon=" + icon +
                '}';
    }
}
