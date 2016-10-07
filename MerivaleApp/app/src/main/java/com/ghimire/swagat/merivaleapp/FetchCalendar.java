//package com.ghimire.swagat.merivaleapp;
//
//import android.os.AsyncTask;
//import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;
//import android.support.v7.widget.RecyclerView;
//import android.util.Log;
//import android.view.View;
//import android.widget.ProgressBar;
//
//import org.json.JSONArray;
//import org.json.JSONObject;
//import org.w3c.dom.Document;
//import org.w3c.dom.Element;
//import org.w3c.dom.NodeList;
//
//import java.io.BufferedReader;
//import java.io.InputStreamReader;
//import java.net.HttpURLConnection;
//import java.net.URL;
//import java.net.URLConnection;
//import java.util.Vector;
//
//import javax.xml.parsers.DocumentBuilder;
//import javax.xml.parsers.DocumentBuilderFactory;
//
//public class FetchCalendar extends AppCompatActivity {
//    static final String CALENDAR_URL = "https://www.googleapis.com/calendar/v3/calendars/ocdsb.ca_p5nkm7k1q1r35mj6jfs0p7gprk%40group.calendar.google.com/events?orderBy=startTime&singleEvents=true&timeMax=2016-10-30T10%3A00%3A00-01%3A00&timeMin=2016-08-29T10%3A00%3A00-01%3A00&key=AIzaSyDFTr6vExELccLBwM2z4STVU7VTdokYQh0";
//    JSONObject data;
//    ProgressBar PBar;
//    RecyclerView RView;
//    RetrieveFeedTask RTF = new RetrieveFeedTask();
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//    }
//
//    protected void Initialize(ProgressBar p1, RecyclerView rv){
//        PBar = p1;
//        RView = rv;
//        RTF.execute();
//    }
//
//    protected Vector<String> returnVec(){
//        try{
//            RTF.get();
//            return announcements;
//        }catch (Exception e){
//            Log.d("FLOP", e.toString());
//            return null;
//        }
//    }
//
//    class RetrieveFeedTask extends AsyncTask<Void, Void, String> {
//
//        private Exception exception;
//
//        protected void onPreExecute() {
//            PBar.setVisibility(View.VISIBLE);
//            RView.setVisibility(View.GONE);
//        }
//
//        protected String doInBackground(Void... urls) {
//            try {
//                URL url = new URL(CALENDAR_URL);
//                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
//                try {
//                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
//                    StringBuilder stringBuilder = new StringBuilder();
//                    String line;
//                    while ((line = bufferedReader.readLine()) != null) {
//                        stringBuilder.append(line).append("\n");
//                    }
//                    bufferedReader.close();
//                    return stringBuilder.toString();
//                }
//                finally{
//                    urlConnection.disconnect();
//                }
//            }
//            catch(Exception e) {
//                Log.e("ERROR", e.getMessage(), e);
//                return null;
//            }
//        }
//
//        protected void onPostExecute(String response) {
//            PBar.setVisibility(View.GONE);
//            try{
//                data = new JSONObject(response);
//                JSONArray items = data.getJSONArray("items");
//                for (int i = 0; i < items.length(); i++){
//
//                }
//            }catch (Exception e){
//                Log.d("ERROR: ", e.toString());
//            }
//        }
//    }
//}