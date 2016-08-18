package com.ghimire.swagat.merivaleapp;

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
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class FetchWeather extends AppCompatActivity {
    JSONObject data;
    boolean isReady;
    ProgressBar progressBar;
    TextView w1, w2;
    ImageView w3;
    float scale;
    //static final String API_KEY = "USE_YOUR_OWN_API_KEY";
    static final String API_URL = "http://api.openweathermap.org/data/2.5/weather?id=6094817&units=metric&APPID=55668083e86877e3c9e32c27748f69f6";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected void Initialize(TextView ww1, TextView ww2, ImageView ww3, ProgressBar p1, float s1){
        w1 = ww1;
        w2 = ww2;
        w3 = ww3;
        progressBar = p1;
        scale = s1;
        new RetrieveFeedTask().execute();
    }

    class RetrieveFeedTask extends AsyncTask<Void, Void, String> {

        private Exception exception;

        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
        }

        protected String doInBackground(Void... urls) {
            try {
                isReady = false;
                URL url = new URL(API_URL);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                try {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    StringBuilder stringBuilder = new StringBuilder();
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        stringBuilder.append(line).append("\n");
                    }
                    bufferedReader.close();
                    return stringBuilder.toString();
                }
                finally{
                    urlConnection.disconnect();
                }
            }
            catch(Exception e) {
                Log.e("ERROR", e.getMessage(), e);
                return null;
            }
        }

        protected void onPostExecute(String response) {
            progressBar.setVisibility(View.GONE);
            isReady = true;
            try{
                data = new JSONObject(response);
                JSONObject details = data.getJSONArray("weather").getJSONObject(0);
                JSONObject main = data.getJSONObject("main");
                int id;
                String description, icon, tempStr;
                double temp;
                description = details.getString("description");
                description = description.substring(0,1).toUpperCase() + description.substring(1).toLowerCase();
                icon = details.getString("icon");
                id = details.getInt("id");
                temp = main.getDouble("temp");
                tempStr = String.format("%.0f", temp);
//                tempStr = String.valueOf(temp);
                if (description.length() > 13){
                    w1.setTextSize(24);
                    if (description.length() < 21){
                        int padding_in_dp = 20;  // 16 dps
                        int padding_in_px = (int) (padding_in_dp * scale + 0.5f);
                        w1.setPadding(0,padding_in_px,0,0);
                    }
                }
                w1.setText(description);
                w1.setMaxLines(2);
                w2.setText(tempStr + "°C");
                w3.setImageResource(R.drawable.completed_icon2);
//                tempStr = String.valueOf(temp);
//                w1.setText(description);
//                w2.setText(tempStr + "°C");
//                w3.setImageResource(R.drawable.completed_icon2);
            }catch (Exception e){
                Log.d("ERROR: ", e.toString());
            }
        }
    }
}