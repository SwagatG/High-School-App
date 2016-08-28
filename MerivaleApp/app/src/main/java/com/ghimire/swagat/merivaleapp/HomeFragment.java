package com.ghimire.swagat.merivaleapp;

import android.content.Context;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class HomeFragment extends Fragment {
    public Context context;

    public HomeFragment(){
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView =inflater.inflate(R.layout.fragment_home, null);
        context = getActivity();

        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("E, MMM dd");
        String formattedDate = df.format(c.getTime());
        SimpleDateFormat tf = new SimpleDateFormat("hh:mm aa");
        String formattedTime = tf.format(c.getTime());

        TextView dateView = (TextView) rootView.findViewById(R.id.date);
        dateView.setText(formattedDate);
        TextView timeView = (TextView) rootView.findViewById(R.id.time);
        timeView.setText(formattedTime);

        String DayofMonth = new StringBuilder().append(formattedDate.charAt(formattedDate.length()-2)).append(formattedDate.charAt(formattedDate.length()-1)).toString();
        int daySched = Integer.parseInt(DayofMonth);
        ImageView dateImg = (ImageView) rootView.findViewById(R.id.dayImg);
        if (daySched % 2 == 1){
            dateImg.setImageResource(R.drawable.day_1);
        } else{
            dateImg.setImageResource(R.drawable.day_2);
        }

        TextView w1 = (TextView) rootView.findViewById(R.id.weatherType);
        TextView w2 = (TextView) rootView.findViewById(R.id.temperature);
        TextView w3 = (TextView) rootView.findViewById(R.id.weatherImg);
        ProgressBar p1 = (ProgressBar) rootView.findViewById(R.id.progressBar);
        float s1 = getResources().getDisplayMetrics().density;
        Typeface f1 = Typeface.createFromAsset(context.getAssets(), "fonts/weather.ttf");

        FetchWeather getWeather = new FetchWeather();
        getWeather.Initialize(w1, w2, w3, p1, s1, f1, context);

        return rootView;
    }

    /*public void setupHome(ViewGroup container){
        FloatingActionButton fab = (FloatingActionButton) container.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }*/
}
