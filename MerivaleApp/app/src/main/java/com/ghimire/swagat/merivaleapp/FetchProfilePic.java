package com.ghimire.swagat.merivaleapp;

import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.lang.*;

public class FetchProfilePic extends AppCompatActivity {

    Context context;
    String imgUrl;
    Drawable pic;
    ImageView picView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected void Initialize (String u1, ImageView i1, Context c1) {
        context = c1;
        imgUrl = u1;
        picView = i1;
        new RetrieveFeedTask().execute();
    }

    class RetrieveFeedTask extends AsyncTask<Void, Void, String> {

        private Exception exception;

        protected void onPreExecute() {
        }

        protected String doInBackground(Void... urls) {
            try {
                InputStream is = (InputStream) new URL(imgUrl).getContent();
                Drawable d = Drawable.createFromStream(is, "src name");
                pic = d;
                return null;
            } catch (Exception e) {
                Log.d("ERROR: ", e.toString());
                return null;
            }
        }

        protected void onPostExecute(String response) {
            try {
                    picView.setImageDrawable(pic);
            } catch (Exception e) {
                Log.d("ERROR: ", e.toString());
            }
        }
    }
}