package edu.itu.csc.quakenweather.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import edu.itu.csc.quakenweather.R;
import edu.itu.csc.quakenweather.adapters.WeatherAdapter;
import edu.itu.csc.quakenweather.models.Weather;

/**
 * Created by Andrii on 2/9/2018.
 */

public class WeatherActivity extends AppCompatActivity implements ActivityCompat.OnRequestPermissionsResultCallback {

    private double latitude = 0;
    private double longitude = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        setTitle(getString(R.string.title_activity_weather));

        Intent intent = getIntent();
        longitude = intent.getDoubleExtra("longitude", 0.0);
        latitude = intent.getDoubleExtra("latitude", 0.0);
        new loadLatestWeatherForecast(this).execute();

    }

    /**
     * AsyncTask to fetch latest quake data from USGS.
     */
    public class loadLatestWeatherForecast extends AsyncTask<Void, Void, ArrayList<Weather>> {

        private ProgressDialog dialog;
        private Context context;

        public loadLatestWeatherForecast(Context context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = ProgressDialog.show(context, "Latest Weather Forecast", "Collecting latest weather forecast data from openweathermap.org ... ", false);
        }

        @Override
        protected ArrayList<Weather> doInBackground(Void... data) {

            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            String urlPath = "http://api.openweathermap.org/data/2.5/forecast/daily?lat=37.387647699999995&lon=-122.03019289999997&cnt=7&appid=ecbeaa84b6d265f4f7e442cb44543c42";
            String weatherResponse = null;
            ArrayList<Weather> weekWeather = new ArrayList<Weather>();

            try {
                URL url = new URL(urlPath);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.setConnectTimeout(5000);
                urlConnection.setReadTimeout(5000);
                urlConnection.connect();

                // Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                StringBuilder stringBuilder = new StringBuilder();
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line);
                }
                if (stringBuilder.length() > 0) {
                    weatherResponse = stringBuilder.toString();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                JSONObject allWeather = new JSONObject(weatherResponse);
                JSONArray sevenDaysWeather = allWeather.getJSONArray("list");
                for (int i = 0; i < sevenDaysWeather.length(); i++) {
                    JSONObject oneDayWeather = sevenDaysWeather.getJSONObject(i);
                    int date = oneDayWeather.getInt("dt");
                    JSONObject temperature = oneDayWeather.getJSONObject("temp");
                    Double dayTemperature = temperature.getDouble("day");
                    Double nightTemperature = temperature.getDouble("night");
                    JSONArray weatherWords = oneDayWeather.getJSONArray("weather");
                    JSONObject weatherFirstObject = weatherWords.getJSONObject(0);
                    String weatherDescription = weatherFirstObject.getString("description");
                    String iconId = weatherFirstObject.getString("icon");
                    JSONObject city = allWeather.getJSONObject("city");
                    String cityName = city.getString("name");

                    Weather weather = new Weather(cityName, date, dayTemperature, nightTemperature, weatherDescription, iconId);
                    weekWeather.add(weather);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return weekWeather;
        }

        @Override
        protected void onPostExecute(ArrayList<Weather> weekWeather) {
            super.onPostExecute(weekWeather);
            if (dialog != null
                    && dialog.isShowing()) {
                dialog.dismiss();
            }

            if (weekWeather != null) {
                ArrayAdapter<Weather> weatherAdapter = new WeatherAdapter(context, R.layout.list_item_weather, weekWeather);
                ListView listView = (ListView) findViewById(R.id.weather_list);
                listView.setAdapter(weatherAdapter);
            } else {
                Toast.makeText(context, "Couldn't load quakes data from openweathermap.org, check your internet connection and try again!", Toast.LENGTH_LONG).show();
            }
        }
    }
}
