package com.ghimire.swagat.merivaleapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Calendar;

public class CalendarFragment extends Fragment {
    public CalendarFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int date = c.get(Calendar.DATE);
        int hour = c.get(Calendar.HOUR);
        int minute = c.get(Calendar.MINUTE);
        int second = c.get(Calendar.SECOND);
        int a = c.get(Calendar.AM_PM);
        hour += 12 * a;
        month ++;

        Log.d("year", Integer.toString(year));
        Log.d("month", Integer.toString(month));
        Log.d("date", Integer.toString(date));
        Log.d("hour", Integer.toString(hour));
        Log.d("min", Integer.toString(minute));
        Log.d("sec", Integer.toString(second));
        Log.d("dst", Integer.toString(a));


        View rootView =inflater.inflate(R.layout.fragment_calendar, null);

        return rootView;
    }

}