package edu.itu.csc.quakenweather.models;

/**
 * Created by Andrii Stasenko on 3/4/2018.
 */

public class Weather {

    private String city;
    private int date;
    private double dayTemperature;
    private double nightTemperature;
    private String weather;
    private String iconId;

    public Weather(String city, int date, double dayTemperature, double nightTemperature, String weather, String iconId) {
        this.city = city;
        this.date = date;
        this.dayTemperature = dayTemperature;
        this.nightTemperature = nightTemperature;
        this.weather = weather;
        this.iconId = iconId;
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

    public double getDayTemperature() {
        return dayTemperature;
    }

    public void setDayTemperature(double dayTemperature) {
        this.dayTemperature = dayTemperature;
    }

    public double getNightTemperature() {
        return nightTemperature;
    }

    public void setNightTemperature(double nightTemperature) {
        this.nightTemperature = nightTemperature;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public String getIconId() {
        return iconId;
    }

    public void setIconId(String iconId) {
        this.iconId = iconId;
    }

    @Override
    public String toString() {
        return "Weather{" +
                "city='" + city + '\'' +
                ", date=" + date +
                ", dayTemperature=" + dayTemperature +
                ", nightTemperature=" + nightTemperature +
                ", weather='" + weather + '\'' +
                ", iconId='" + iconId + '\'' +
                '}';
    }
}
