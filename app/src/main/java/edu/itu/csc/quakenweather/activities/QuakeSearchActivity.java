package edu.itu.csc.quakenweather.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import edu.itu.csc.quakenweather.R;
import edu.itu.csc.quakenweather.adapters.QuakeAdapter;
import edu.itu.csc.quakenweather.models.Quake;
import edu.itu.csc.quakenweather.settings.SettingsActivity;
import edu.itu.csc.quakenweather.search.RecommendTrie;
import edu.itu.csc.quakenweather.utilities.Utility;

/**
 * Display city name recommendations where there was a quake given filters using Trie data structure.
 *
 * @author "Jigar Gosalia"
 *
 */
public class QuakeSearchActivity extends AppCompatActivity {

    private static ArrayAdapter<Quake> quakeAdapter = null;

    private static List<Quake> quakeDetails = new ArrayList<Quake>();

    private static RecommendTrie recommendTrie = new RecommendTrie();

    private TextView updateTime;

    private TextView updateFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quake_search);

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#228B22")));

        updateTime = (TextView) findViewById(R.id.update_time);
        updateFilter = (TextView) findViewById(R.id.update_filter);

        PreferenceManager.setDefaultValues(getApplicationContext(), R.xml.preference_general, false);

        quakeAdapter = new QuakeAdapter(this, R.layout.list_item_quake, new ArrayList<Quake>());

        // Get a reference to the ListView and attach this adapter to it.
        ListView listView = (ListView) findViewById(R.id.listview_quake);
        listView.setAdapter(quakeAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (quakeAdapter != null
                        && quakeAdapter.getItem(i) != null) {
                    Quake earthQuakeInfo = quakeAdapter.getItem(i);
                    Log.d(MainActivity.APP_TAG, "Quake Selected : " + earthQuakeInfo);
                    Intent intent = new Intent(QuakeSearchActivity.this, QuakeDetailsActivity.class);
                    intent.putExtra("title", earthQuakeInfo.getTitle());
                    intent.putExtra("location", earthQuakeInfo.getFormattedPlace());
                    intent.putExtra("coordinates", earthQuakeInfo.getFormattedCoordinates());
                    intent.putExtra("time", earthQuakeInfo.getFormattedTime());
                    intent.putExtra("depth", earthQuakeInfo.getDepth());
                    intent.putExtra("eventid", earthQuakeInfo.getEventId());
                    intent.putExtra("significance", earthQuakeInfo.getSignificance());
                    intent.putExtra("status", earthQuakeInfo.getStatus());
                    intent.putExtra("url", earthQuakeInfo.getUrl());
                    startActivity(intent);
                }
            }
        });

        final EditText cityName = (EditText) findViewById(R.id.search_box);
        cityName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // do nothing
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                EditText cName = (EditText) findViewById(R.id.search_box);
                String prefix = cName.getText().toString();
                if (prefix != null && prefix.length() > 0) {
                    quakeAdapter.clear();
                    List<String> cityNames = recommendTrie.recommend(prefix.toLowerCase(), 10);
                    for (Quake quakeInfo : quakeDetails) {
                        if (cityNames.contains(quakeInfo.getFormattedPlace().toLowerCase())) {
                            quakeAdapter.add(quakeInfo);
                        }
                    }
                } else {
                    quakeAdapter.clear();
                    quakeAdapter.addAll(quakeDetails);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // do nothing
            }
        });
    }

    /**
     * AsyncTask to fetch latest quakes data from USGS.
     */
    public static class FetchLatestQuakeTask extends AsyncTask<String, Void, List<Quake>> {

        private ProgressDialog dialog;
        private Context context;

        public FetchLatestQuakeTask(Context context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = ProgressDialog.show(context, "Latest Quakes Data", "Collecting latest quakes data from USGS ... ", false);
            recommendTrie = new RecommendTrie();
        }

        @Override
        protected List<Quake> doInBackground(String... data) {
            String url = Utility.urlType.get("today");
            if (data != null
                    && data.length > 2) {
                return Utility.getQuakeData("QuakeSearchActivity", Utility.urlType.get(data[1]), data[0], data[1], data[2], context);
            }
            return Utility.getQuakeData("QuakeSearchActivity", url, Utility.DEFAULT_MAGNITUDE, Utility.DEFAULT_DURATION, Utility.DEFAULT_DISTANCE, context);
        }

        @Override
        protected void onPostExecute(List<Quake> result) {
            super.onPostExecute(result);
            if (dialog != null
                    && dialog.isShowing()) {
                dialog.dismiss();
            }

            if (result != null
                    && quakeAdapter != null) {
                quakeAdapter.clear();
                quakeDetails.clear();
                if (result.size() > 0) {
                    for (Quake earthquakeInfo : result) {
                        quakeAdapter.add(earthquakeInfo);
                        recommendTrie.addWord(earthquakeInfo.getFormattedPlace());
                        quakeDetails.add(earthquakeInfo);
                    }
                } else {
                    Toast.makeText(context, "No data found with given filters!", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(context, "Couldn't fetch quakes data from USGS, check your internet connection and try again!", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        fetchLatestQuakes(this);
        Utility.updateLastViewed(QuakeSearchActivity.class.getSimpleName(), this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_mini, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case R.id.action_refresh:
                fetchLatestQuakes(this);
                break;
            case R.id.action_settings:
                startActivity(new Intent(QuakeSearchActivity.this, SettingsActivity.class));
                break;
            default:
                Toast.makeText(this, "No Such Action Supported!", Toast.LENGTH_LONG).show();
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * fetch latest quakes data.
     *
     * @param context
     */
    private void fetchLatestQuakes(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        String magnitude = prefs.getString(context.getString(R.string.preference_magnitude_key), null);
        String duration = prefs.getString(context.getString(R.string.preference_duration_key), null);
        String distance = prefs.getString(context.getString(R.string.preference_distance_key), null);
        new QuakeSearchActivity.FetchLatestQuakeTask(context).execute(magnitude, duration, distance);
        updateFooter();
    }

    /**
     * refresh footer with last updated time and user filters.
     *
     */
    private void updateFooter() {
        Date date = new Date();
        SimpleDateFormat timeFormatter = new SimpleDateFormat("h:mm a");
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String magnitude = prefs.getString(this.getString(R.string.preference_magnitude_key), null);
        String duration = prefs.getString(this.getString(R.string.preference_duration_key), null);
        updateTime.setText(("Updated: " + timeFormatter.format(date)).toString());
        updateFilter.setText(("Filters: " + magnitude + "; " + Utility.durationMap.get(duration)).toString());
    }
}
