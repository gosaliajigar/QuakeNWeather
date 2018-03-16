package edu.itu.csc.quakenweather.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
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
import edu.itu.csc.quakenweather.settings.SettingsActivity;
import edu.itu.csc.quakenweather.utilities.Utility;

/**
 * Created by Andrii Stasenko on 2/9/2018.
 */

public class WeatherActivity extends AppCompatActivity implements ActivityCompat.OnRequestPermissionsResultCallback {

    private ArrayAdapter<Weather> weatherAdapter;
    private double latitude = 0;
    private double longitude = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        setTitle(R.string.title_activity_weather);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#228B22")));

        PreferenceManager.setDefaultValues(getApplicationContext(), R.xml.preference_general, false);

        Intent intent = getIntent();
        longitude = intent.getDoubleExtra("longitude", 0.0);
        latitude = intent.getDoubleExtra("latitude", 0.0);

        ListView listView = (ListView) findViewById(R.id.weather_list);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (weatherAdapter != null
                        && weatherAdapter.getItem(i) != null) {
                    Weather weather = weatherAdapter.getItem(i);
                    Log.d(MainActivity.APP_TAG, "Weather Selected : " + weather);
                    Intent intent = new Intent(WeatherActivity.this, WeatherDetailsActivity.class);
                    intent.putExtra("city", weather.getCity());
                    intent.putExtra("date", Integer.toString(weather.getDate()));
                    intent.putExtra("morningTemperature", Double.toString(weather.getMorningTemperature()));
                    intent.putExtra("dayTemperature", Double.toString(weather.getDayTemperature()));
                    intent.putExtra("eveningTemperature", Double.toString(weather.getEveningTemperature()));
                    intent.putExtra("nightTemperature", Double.toString(weather.getNightTemperature()));
                    intent.putExtra("pressure", Double.toString(weather.getPressure()));
                    intent.putExtra("humidity", Integer.toString(weather.getHumidity()));
                    intent.putExtra("windSpeed", Double.toString(weather.getWindSpeed()));
                    intent.putExtra("weather", weather.getWeather());
                    intent.putExtra("icon", Utility.BitMapToString(weather.getIcon()));
                    startActivity(intent);
                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_macro, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id) {
            case R.id.action_settings:
                startActivity(new Intent(WeatherActivity.this, SettingsActivity.class));
                break;
            default:
                Toast.makeText(this, "No Such Action Supported!", Toast.LENGTH_LONG).show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume() {
        super.onResume();
        new LoadWeeklyWeatherForecast(this).execute();
    }

    /**
     * AsyncTask to fetch latest quake data from USGS.
     */
    public class LoadWeeklyWeatherForecast extends AsyncTask<Void, Void, ArrayList<Weather>> {

        private ProgressDialog dialog;
        private Context context;

        public LoadWeeklyWeatherForecast(Context context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = ProgressDialog.show(context, "Latest Weather Forecast", "Collecting latest weather forecast data from Open Weather ... ", false);
        }

        @Override
        protected ArrayList<Weather> doInBackground(Void... data) {

            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
            String temperatureFormat = prefs.getString(context.getString(R.string.preference_temperature_key), null);

            ApplicationInfo applicationInfo = null;
            try {
                applicationInfo = getPackageManager().getApplicationInfo(getPackageName(), PackageManager.GET_META_DATA);
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
            Bundle bundle = applicationInfo.metaData;
            String appID = bundle.getString("org.openweathermap.APP_ID");

            StringBuilder futureURL = new StringBuilder();
            futureURL.append("http://api.openweathermap.org/data/2.5/forecast/daily?lat=");
            futureURL.append(latitude);
            futureURL.append("&lon=");
            futureURL.append(longitude);
            futureURL.append("&cnt=7&appid=");
            futureURL.append(appID);
            futureURL.append("&units=");
            if (temperatureFormat.equals("Celsius")) {
                futureURL.append("metric");
            } else {
                futureURL.append("imperial");
            }
            String urlPath = futureURL.toString();
            String weatherResponse = null;
            ArrayList<Weather> weekWeather = new ArrayList<Weather>();

            try {
                URL url = new URL(urlPath);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.setConnectTimeout(5000);
                urlConnection.setReadTimeout(5000);
                Log.d(MainActivity.APP_TAG, "WeatherActivity: Connecting " + url);
                urlConnection.connect();
                Log.d(MainActivity.APP_TAG, "WeatherActivity: Received data from " + url);

                // Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                StringBuilder stringBuilder = new StringBuilder();
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line);
                }
                Log.d(MainActivity.APP_TAG, "WeatherActivity: JSON String: " + stringBuilder.toString());
                if (stringBuilder.length() > 0) {
                    weatherResponse = stringBuilder.toString();
                    JSONObject allWeather = new JSONObject(weatherResponse);
                    JSONArray sevenDaysWeather = allWeather.getJSONArray("list");
                    for (int i = 0; i < sevenDaysWeather.length(); i++) {
                        JSONObject oneDayWeather = sevenDaysWeather.getJSONObject(i);
                        int date = oneDayWeather.getInt("dt");
                        JSONObject temperature = oneDayWeather.getJSONObject("temp");
                        Double morningTemperature = temperature.getDouble("morn");
                        Double dayTemperature = temperature.getDouble("day");
                        Double eveningTemperature = temperature.getDouble("eve");
                        Double nightTemperature = temperature.getDouble("night");
                        Double pressure = oneDayWeather.getDouble("pressure");
                        Integer humidity = oneDayWeather.getInt("humidity");
                        Double windSpeed = oneDayWeather.getDouble("speed");
                        JSONArray weatherWords = oneDayWeather.getJSONArray("weather");
                        JSONObject weatherFirstObject = weatherWords.getJSONObject(0);
                        String weatherDescription = weatherFirstObject.getString("description");
                        String iconId = weatherFirstObject.getString("icon");
                        JSONObject city = allWeather.getJSONObject("city");
                        String cityName = city.getString("name");

                        StringBuilder iconUrl = new StringBuilder();
                        iconUrl.append("http://openweathermap.org/img/w/");
                        iconUrl.append(iconId);
                        iconUrl.append(".png");
                        Bitmap icon = Utility.getBitmapFromURL(iconUrl.toString());

                        Weather weather = new Weather(cityName, date, morningTemperature, dayTemperature, eveningTemperature, nightTemperature, pressure, humidity, windSpeed, weatherDescription, icon);
                        weekWeather.add(weather);
                    }
                }
            } catch (Exception exception) {
                weekWeather = null;
                Log.e(MainActivity.APP_TAG, "WeatherActivity: getQuakeData Exception: " + exception.toString());
                Utility.addErrorEntry(context, exception);
                exception.printStackTrace();
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
                weatherAdapter = new WeatherAdapter(context, R.layout.list_item_weather, weekWeather);
                ListView listView = (ListView) findViewById(R.id.weather_list);
                listView.setAdapter(weatherAdapter);
            } else {
                Toast.makeText(context, "Couldn't load weather data from Open Weather, check your internet connection and try again!", Toast.LENGTH_LONG).show();
            }
        }
    }
}
