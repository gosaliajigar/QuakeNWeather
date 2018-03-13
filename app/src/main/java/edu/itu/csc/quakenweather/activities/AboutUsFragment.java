package edu.itu.csc.quakenweather.activities;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Map;

import edu.itu.csc.quakenweather.R;
import edu.itu.csc.quakenweather.database.RegistrationProvider;
import edu.itu.csc.quakenweather.utilities.Utility;

/**
 * About Us fragment containing a simple view.
 *
 * @author "Jigar Gosalia"
 */
public class AboutUsFragment extends Fragment {

    public AboutUsFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_about_us, container, false);

        Map<String, String> map = Utility.getRegistrationEntry(getContext());
        TextView installText = (TextView)rootView.findViewById(R.id.install_text);
        TextView installDate = (TextView)rootView.findViewById(R.id.install_data);
        TextView lastViewText = (TextView)rootView.findViewById(R.id.last_text);
        TextView lastViewDate = (TextView)rootView.findViewById(R.id.last_data);
        if (map != null && map.size() > 0) {
            if (map.get(RegistrationProvider.INSTALL_DATE) != null) {
                installText.setVisibility(View.VISIBLE);
                installDate.setVisibility(View.VISIBLE);
                installDate.setText(Utility.getFormattedDate(Utility.formatter, map.get(RegistrationProvider.INSTALL_DATE)));
            } else {
                installText.setVisibility(View.INVISIBLE);
                installDate.setVisibility(View.INVISIBLE);
            }
            if (map.get(RegistrationProvider.LAST_DATE) != null) {
                lastViewText.setVisibility(View.VISIBLE);
                lastViewDate.setVisibility(View.VISIBLE);
                lastViewDate.setText(Utility.getFormattedDate(Utility.formatter, map.get(RegistrationProvider.LAST_DATE)));
            } else {
                lastViewText.setVisibility(View.INVISIBLE);
                lastViewDate.setVisibility(View.INVISIBLE);
            }
        } else {
            installText.setVisibility(View.INVISIBLE);
            installDate.setVisibility(View.INVISIBLE);
            lastViewText.setVisibility(View.INVISIBLE);
            lastViewDate.setVisibility(View.INVISIBLE);
        }
        return rootView;
    }

}
