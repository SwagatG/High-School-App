package com.ghimire.swagat.merivaleapp;

import android.content.Context;
import android.graphics.Typeface;
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
import java.lang.*;

public class FetchWeather extends AppCompatActivity {
    JSONObject data;
    boolean isReady;
    ProgressBar progressBar;
    TextView w1, w2, w3;
    float scale;
    Typeface font;
    Context context1;
    //static final String API_KEY = "USE_YOUR_OWN_API_KEY";
    static final String API_URL = "http://api.openweathermap.org/data/2.5/weather?id=6094817&units=metric&APPID=55668083e86877e3c9e32c27748f69f6";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected void Initialize(TextView ww1, TextView ww2, TextView ww3, ProgressBar p1, float s1, Typeface f1, Context c1){
        context1 = c1;
        w1 = ww1;
        w2 = ww2;
        w3 = ww3;
        progressBar = p1;
        scale = s1;
        font = f1;
        new RetrieveFeedTask().execute();
    }

    protected void getImage(int id, char dn){
        //String base = "wi_owm_";
        //StringBuilder sb = new StringBuilder();
        //sb.append(base);
        String icon;// = context1.getString(R.string.wi_owm_night_800);
        if (dn == 'd'){
            switch (id){
                case 200:case 201:case 202: case 230:case 231:case 232:
                    icon = context1.getString(R.string.wi_owm_day_200);
                    break;
                case 210:case 211:case 212:case 221:
                    icon = context1.getString(R.string.wi_owm_day_210);
                    break;
                case 300:case 321:case 301:case 500:
                    icon = context1.getString(R.string.wi_owm_day_300);
                    break;
                case 302:case 310:case 311:case 312:case 313:case 314:case 501:case 502:case 503:case 504:
                    icon = context1.getString(R.string.wi_owm_day_302);
                    break;
                case 511:case 611:case 612:case 615:case 616:case 620:
                    icon = context1.getString(R.string.wi_owm_day_511);
                    break;
                case 520:case 521:case 522:case 701:
                    icon = context1.getString(R.string.wi_owm_day_520);
                    break;
                case 531:case 901:
                    icon = context1.getString(R.string.wi_owm_day_531);
                    break;
                case 600:case 602:case 621:case 622:
                    icon = context1.getString(R.string.wi_owm_day_600);
                    break;
                case 601:
                    icon = context1.getString(R.string.wi_owm_day_601);
                    break;
                case 711:
                    icon = context1.getString(R.string.wi_owm_day_711);
                    break;
                case 721:
                    icon = context1.getString(R.string.wi_owm_day_721);
                    break;
                case 731:case 761:case 762:
                    icon = context1.getString(R.string.wi_owm_day_731);
                    break;
                case 741:
                    icon = context1.getString(R.string.wi_owm_day_741);
                    break;
                case 771:case 801:case 802:case 803:
                    icon = context1.getString(R.string.wi_owm_day_771);
                    break;
                case 781:case 900:
                    icon = context1.getString(R.string.wi_owm_day_781);
                    break;
                case 800:
                    icon = context1.getString(R.string.wi_owm_day_800);
                    break;
                case 804:
                    icon = context1.getString(R.string.wi_owm_day_804);
                    break;
                case 902:
                    icon = context1.getString(R.string.wi_owm_day_902);
                    break;
                case 903:
                    icon = context1.getString(R.string.wi_owm_day_903);
                    break;
                case 904:
                    icon = context1.getString(R.string.wi_owm_day_904);
                    break;
                case 905:
                    icon = context1.getString(R.string.wi_owm_day_905);
                    break;
                case 906:
                    icon = context1.getString(R.string.wi_owm_day_906);
                    break;
                case 957:
                    icon = context1.getString(R.string.wi_owm_day_957);
                    break;
                default:
                    icon = "?";
                    break;
            }
            //sb.append("day_");
        } else {
            switch (id) {
                case 200:
                case 201:
                case 202:
                case 230:
                case 231:
                case 232:
                    icon = context1.getString(R.string.wi_owm_night_200);
                    break;
                case 210:
                case 211:
                case 212:
                case 221:
                    icon = context1.getString(R.string.wi_owm_night_210);
                    break;
                case 300:
                case 321:
                case 301:
                case 500:
                    icon = context1.getString(R.string.wi_owm_night_300);
                    break;
                case 302:
                case 310:
                case 311:
                case 312:
                case 313:
                case 314:
                case 501:
                case 502:
                case 503:
                case 504:
                    icon = context1.getString(R.string.wi_owm_night_302);
                    break;
                case 511:
                case 611:
                case 612:
                case 615:
                case 616:
                case 620:
                    icon = context1.getString(R.string.wi_owm_night_511);
                    break;
                case 520:
                case 521:
                case 522:
                case 701:
                    icon = context1.getString(R.string.wi_owm_night_520);
                    break;
                case 531:
                    icon = context1.getString(R.string.wi_owm_night_531);
                    break;
                case 901:
                    icon =  context1.getString(R.string.wi_owm_night_901);
                    break;
                case 600:
                case 602:
                case 621:
                case 622:
                    icon = context1.getString(R.string.wi_owm_night_600);
                    break;
                case 601:
                    icon = context1.getString(R.string.wi_owm_night_601);
                    break;
                case 711:
                    icon = context1.getString(R.string.wi_owm_night_711);
                    break;
                case 721:
                    icon = context1.getString(R.string.wi_owm_night_721);
                    break;
                case 731:
                case 761:
                case 762:
                    icon = context1.getString(R.string.wi_owm_night_731);
                    break;
                case 741:
                    icon = context1.getString(R.string.wi_owm_night_741);
                    break;
                case 771:
                case 801:
                case 802:
                case 803:
                    icon = context1.getString(R.string.wi_owm_night_771);
                    break;
                case 781:
                case 900:
                    icon = context1.getString(R.string.wi_owm_night_781);
                    break;
                case 800:
                    icon = context1.getString(R.string.wi_owm_night_800);
                    break;
                case 804:
                    icon = context1.getString(R.string.wi_owm_night_804);
                    break;
                case 902:
                    icon = context1.getString(R.string.wi_owm_night_902);
                    break;
                case 903:
                    icon = context1.getString(R.string.wi_owm_night_903);
                    break;
                case 904:
                    icon = context1.getString(R.string.wi_owm_night_904);
                    break;
                case 905:
                    icon = context1.getString(R.string.wi_owm_night_905);
                    break;
                case 906:
                    icon = context1.getString(R.string.wi_owm_night_906);
                    break;
                case 957:
                    icon = context1.getString(R.string.wi_owm_night_957);
                    break;
                default:
                    icon = "?";
                    break;
            }
            //sb.append("night_");
        }
        w3.setText(icon);
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
                w3.setTypeface(font);
                Log.d("TYPEFACE: ", font.toString());
//                w3.setText("&#xf02e");
                getImage(id, icon.charAt(2));
//                tempStr = String.valueOf(temp);
                if (description.length() > 13){
                    w1.setTextSize(36);
                    if (description.length() < 21){
                        int padding_in_dp = 20;  // 16 dps
                        int padding_in_px = (int) (padding_in_dp * scale + 0.5f);
                        w1.setPadding(0,padding_in_px,0,0);
                    }
                }
                w1.setText(description);
                w1.setMaxLines(2);
                w2.setText(tempStr + "°C");
                //w3.setImageResource(R.drawable.completed_icon2);
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