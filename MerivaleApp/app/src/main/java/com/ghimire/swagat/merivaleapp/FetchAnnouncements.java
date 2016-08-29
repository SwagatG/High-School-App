package com.ghimire.swagat.merivaleapp;

import android.content.Context;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.lang.*;
import java.net.URLConnection;
import java.util.Vector;
import java.util.concurrent.TimeUnit;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class FetchAnnouncements extends AppCompatActivity {
    static final String SPREADSHEET_URL = "https://spreadsheets.google.com/feeds/cells/1QmjIm249kJjBoZD7D3fugk5EHVzz5cWBXh_EP-XLx8w/oi77ko2/public/full";
    Vector<String> announcements = new Vector<>(3,3);
    ProgressBar PBar;
    RecyclerView RView;
    RetrieveFeedTask RTF = new RetrieveFeedTask();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected void Initialize(ProgressBar p1, RecyclerView rv){
        PBar = p1;
        RView = rv;
        RTF.execute();
    }

    protected Vector<String> returnVec(){
        try{
            RTF.get();
            return announcements;
        }catch (Exception e){
            Log.d("FLOP", e.toString());
            return null;
        }
    }

    class RetrieveFeedTask extends AsyncTask<Void, Void, String> {

        private Exception exception;

        protected void onPreExecute() {
            PBar.setVisibility(View.VISIBLE);
            RView.setVisibility(View.GONE);
        }

        protected String doInBackground(Void... urls) {
            try {
                URL url = new URL(SPREADSHEET_URL);
                URLConnection conn = url.openConnection();

                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = factory.newDocumentBuilder();
                Document doc = builder.parse(conn.getInputStream());

                NodeList nodes = doc.getElementsByTagName("entry");
                for (int i = 0; i < nodes.getLength(); i++) {
                    Element element = (Element) nodes.item(i);
                    NodeList title = element.getElementsByTagName("content");
                    Element line = (Element) title.item(0);
                    announcements.add(line.getTextContent());
                }
                return null;
            }
            catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        protected void onPostExecute(String response) {
            PBar.setVisibility(View.GONE);
            RView.setVisibility(View.VISIBLE);
            for (int i = 0; i < announcements.size(); i++){
            }
        }
    }
}