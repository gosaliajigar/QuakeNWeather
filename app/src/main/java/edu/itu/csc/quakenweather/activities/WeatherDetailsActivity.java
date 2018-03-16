package edu.itu.csc.quakenweather.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

import edu.itu.csc.quakenweather.R;
import edu.itu.csc.quakenweather.utilities.Utility;

/**
 * Created by Andrii Stasenko  on 3/12/2018.
 */

public class WeatherDetailsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_details);

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#228B22")));

        Intent intent = getIntent();
        setTitle(R.string.weather_details);

        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, MMM d");
        TextView date = (TextView) findViewById(R.id.weather_date_text);
        date.setText(dateFormat.format(new Date(Long.parseLong(intent.getStringExtra("date")) * 1000)).toString());


        TextView weatherDescription = (TextView) findViewById(R.id.weather_details_description_text);
        weatherDescription.setText(intent.getStringExtra("weather").substring(0, 1).toUpperCase() + intent.getStringExtra("weather").substring(1));

        ImageView weatherImage = (ImageView) findViewById(R.id.weather_imageView);
        weatherImage.setImageBitmap(Utility.StringToBitMap(intent.getStringExtra("icon")));

        String temperatureUnits;
        String windSpeedUnits;
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String temperature_format = prefs.getString(this.getString(R.string.preference_temperature_key), null);
        if (temperature_format.equals("Celsius")) {
            temperatureUnits = "\u00b0C";
            windSpeedUnits = " meter/sec";
        } else {
            temperatureUnits = "\u00b0F";
            windSpeedUnits = " miles/hour";
        }

        TextView morningTemperature = (TextView) findViewById(R.id.morningTemperature_data);
        morningTemperature.setText(intent.getStringExtra("morningTemperature") + temperatureUnits);
        TextView dayTemperature = (TextView) findViewById(R.id.dayTemperature_data);
        dayTemperature.setText(intent.getStringExtra("dayTemperature") + temperatureUnits);
        TextView eveningTemperature = (TextView) findViewById(R.id.eveningTemperature_data);
        eveningTemperature.setText(intent.getStringExtra("eveningTemperature") + temperatureUnits);
        TextView nightTemperature = (TextView) findViewById(R.id.nightTemperature_data);
        nightTemperature.setText(intent.getStringExtra("nightTemperature") + temperatureUnits);
        TextView pressure = (TextView) findViewById(R.id.pressure_data);
        pressure.setText(intent.getStringExtra("pressure") + " hPa");
        TextView humidity = (TextView) findViewById(R.id.humidity_data);
        humidity.setText(intent.getStringExtra("humidity") + "%");
        TextView windSpeed = (TextView) findViewById(R.id.windSpeed_data);
        windSpeed.setText(intent.getStringExtra("windSpeed") + windSpeedUnits);

    }
}
