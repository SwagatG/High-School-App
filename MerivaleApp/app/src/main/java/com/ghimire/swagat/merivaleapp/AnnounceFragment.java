package com.ghimire.swagat.merivaleapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.w3c.dom.Text;
import java.lang.Runnable;

import java.util.List;
import java.util.Vector;

public class AnnounceFragment extends Fragment {
    public AnnounceFragment(){

    }
    Vector<String> announcements = new Vector<>(1,3);

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView =inflater.inflate(R.layout.fragment_announce, null);

        FetchAnnouncements getAnnouncements = new FetchAnnouncements();
        ProgressBar p1 = (ProgressBar) rootView.findViewById(R.id.progressBarAnn);
        getAnnouncements.Initialize(p1);
        announcements = getAnnouncements.returnVec();

        return rootView;
    }

}