package edu.itu.csc.quakenweather.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import edu.itu.csc.quakenweather.R;
import edu.itu.csc.quakenweather.models.Weather;

/**
 * Created by Andrii Stasenko on 3/7/2018.
 */

public class WeatherAdapter extends ArrayAdapter<Weather> {
    private Context context;

    private List<Weather> data;

    public WeatherAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<Weather> objects) {
        super(context, resource, objects);
        this.context = context;
        this.data = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View row = convertView;

        LayoutInflater inflater = ((Activity) getContext()).getLayoutInflater();
        row = inflater.inflate(R.layout.list_item_weather, parent, false);

        TextView date = (TextView) row.findViewById(R.id.weather_date);
        TextView weatherDescription = (TextView) row.findViewById(R.id.weather_description);
        TextView dayTemperature = (TextView) row.findViewById(R.id.weather_day_temperature);
        TextView nightTemperature = (TextView) row.findViewById(R.id.weather_night_temperature);
        ImageView weatherImage = (ImageView) row.findViewById(R.id.weather_image);
        row.setTag("weather");

        Weather weather = data.get(position);
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, MMM d");
        date.setText(dateFormat.format(new Date((long) weather.getDate() * 1000)).toString());
        weatherDescription.setText(weather.getWeather().substring(0, 1).toUpperCase() + weather.getWeather().substring(1));
        dayTemperature.setText(Double.toString(weather.getDayTemperature()) + "\u00b0");
        nightTemperature.setText(Double.toString(weather.getNightTemperature()) + "\u00b0");

        return row;
    }
}
