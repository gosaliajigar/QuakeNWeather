package edu.itu.csc.quakenweather.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import edu.itu.csc.quakenweather.R;
import edu.itu.csc.quakenweather.utilities.Utility;

/**
 * Quake Details Activity to display detailed quake information.
 *
 * @author "Jigar Gosalia"
 *
 */
public class QuakeDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quake_details);

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#228B22")));

        Intent intent = getIntent();

        TextView title = (TextView) findViewById(R.id.title_data);
        title.setText(intent.getStringExtra("title"));

        TextView location = (TextView) findViewById(R.id.location_data);
        location.setText(intent.getStringExtra("location"));

        TextView coordinates = (TextView) findViewById(R.id.coordinates_data);
        coordinates.setText(intent.getStringExtra("coordinates"));

        TextView time = (TextView) findViewById(R.id.time_data);
        time.setText(intent.getStringExtra("time"));

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String distance = prefs.getString(this.getString(R.string.preference_distance_key), null);
        TextView depth = (TextView) findViewById(R.id.depth_data);
        depth.setText(Utility.getFormattedDepth(Utility.getConvertedDepth(intent.getDoubleExtra("depth", 0), distance), distance));

        TextView eventId = (TextView) findViewById(R.id.event_id_data);
        eventId.setText(intent.getStringExtra("eventid"));

        TextView significance = (TextView) findViewById(R.id.significance_data);
        significance.setText(intent.getStringExtra("significance"));

        TextView status = (TextView) findViewById(R.id.review_status_data);
        status.setText(intent.getStringExtra("status"));

        String url = intent.getStringExtra("url");
        TextView urlLink = (TextView) findViewById(R.id.url_link_data);
        urlLink.setMovementMethod(LinkMovementMethod.getInstance());
        urlLink.setText(Html.fromHtml(getResources().getString(R.string.link).replace("HREF", url)));

        final double longitude = intent.getDoubleExtra("longitude", 0.0);
        final double latitude = intent.getDoubleExtra("latitude", 0.0);

        Button weatherButton = (Button) findViewById(R.id.weatherButton);
        weatherButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: Add a new activity here to display 7 day weather
                // use the longitude & latitude captured above as input to
                // Weather API
            }
        });
    }

}
