package edu.itu.csc.quakenweather.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import edu.itu.csc.quakenweather.R;
import edu.itu.csc.quakenweather.models.Quake;
import edu.itu.csc.quakenweather.utilities.Utility;

/**
 * QuakeAdapter for displaying quake details on main page.
 *
 * @author "Jigar Gosalia"
 */
public class QuakeAdapter extends ArrayAdapter<Quake> {

    private Context context;

    private List<Quake> data;

    public QuakeAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<Quake> objects) {
        super(context, resource, objects);
        this.context = context;
        this.data = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View row = convertView;
        ViewHolder holder = null;
        if (row == null) {
            LayoutInflater inflater = ((Activity) getContext()).getLayoutInflater();
            row = inflater.inflate(R.layout.list_item_quake, parent, false);

            holder = new ViewHolder();
            holder.magnitudeTextView = (TextView) row.findViewById(R.id.magnitude);
            holder.placeTextView = (TextView) row.findViewById(R.id.place);
            holder.depthTextView = (TextView) row.findViewById(R.id.depth);
            holder.timeTextView = (TextView) row.findViewById(R.id.time);
            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        String distance = prefs.getString(context.getString(R.string.preference_distance_key), null);

        Quake quake = data.get(position);
        holder.magnitudeTextView.setText(quake.getMagnitude());
        holder.magnitudeTextView.setTextColor(Utility.getTextColorFromMagnitude(quake.getMagnitude()));
        holder.placeTextView.setText(quake.getFormattedPlace());
        String depth = Utility.getFormattedDepth(Utility.getConvertedDepth(quake.getDepth(), distance), distance);
        holder.depthTextView.setText("Depth: " + depth);
        holder.timeTextView.setText(quake.getFormattedTime());
        return row;
    }

    static class ViewHolder {
        TextView magnitudeTextView;
        TextView placeTextView;
        TextView depthTextView;
        TextView timeTextView;
    }
}