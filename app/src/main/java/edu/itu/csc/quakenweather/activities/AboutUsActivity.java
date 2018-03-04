package edu.itu.csc.quakenweather.activities;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import edu.itu.csc.quakenweather.R;

/**
 * About Us Activity
 *
 * @author "Jigar Gosalia"
 */
public class AboutUsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#228B22")));
    }

}
