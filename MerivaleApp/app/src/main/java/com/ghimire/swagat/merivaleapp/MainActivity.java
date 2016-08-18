package com.ghimire.swagat.merivaleapp;

import android.media.Image;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.TextureView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONObject;
import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private void getDateTime(){
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("E, MMM dd");
        String formattedDate = df.format(c.getTime());
        SimpleDateFormat tf = new SimpleDateFormat("hh:mm aa");
        String formattedTime = tf.format(c.getTime());
        TextView dateView = (TextView) findViewById(R.id.date);
        dateView.setText(formattedDate);
        TextView timeView = (TextView) findViewById(R.id.time);
        timeView.setText(formattedTime);
        String DayofMonth = new StringBuilder().append(formattedDate.charAt(formattedDate.length()-2)).append(formattedDate.charAt(formattedDate.length()-1)).toString();
        //int daySched = c.HOUR_OF_DAY;
        int daySched = Integer.parseInt(DayofMonth);
        //Log.d("DAYSCHED:", Integer.toString(daySched));
        ImageView dateImg = (ImageView) findViewById(R.id.dayImg);
        if (daySched % 2 == 1){
            dateImg.setImageResource(R.drawable.day_1);
        } else{
            dateImg.setImageResource(R.drawable.day_2);
        }
        TextView w1 = (TextView) findViewById(R.id.weatherType);
        TextView w2 = (TextView) findViewById(R.id.temperature);
        ImageView w3 = (ImageView) findViewById(R.id.weatherImg);
        ProgressBar p1 = (ProgressBar) findViewById(R.id.progressBar);
        float s1 = getResources().getDisplayMetrics().density;
        FetchWeather getWeather = new FetchWeather();
        getWeather.Initialize(w1, w2, w3, p1, s1);
        //syncWeather.fetchData();
        //syncWeather.UpdateWeather(w1, w2, w3);
        //Log.d("AAY: ", "LMAO2");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getDateTime();

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action
        } else if (id == R.id.nav_news) {

        } else if (id == R.id.nav_calendar) {

        } else if (id == R.id.nav_marks) {

        } else if (id == R.id.nav_homework) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
