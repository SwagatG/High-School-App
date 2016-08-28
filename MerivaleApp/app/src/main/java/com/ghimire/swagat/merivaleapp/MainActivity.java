package com.ghimire.swagat.merivaleapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
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

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.api.Status;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        GoogleApiClient.OnConnectionFailedListener,
        View.OnClickListener {

    public String currFrag = null;
    public Fragment fragment;
    public FragmentTransaction fragmentTransaction;

    private static final String TAG = "SignIn";
    private static final int RC_SIGN_IN = 9001;

    private GoogleApiClient mGoogleApiClient;
    private TextView mStatusTextView;
    private ProgressDialog mProgressDialog;

    private GoogleSignInAccount acct;

    public void getGoogleInfo() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        Log.d("NAV", navigationView.toString());
        TextView uN = (TextView) navigationView.findViewById(R.id.userName);
        TextView uE = (TextView) navigationView.findViewById(R.id.userEmail);
        ImageView uP = (ImageView) navigationView.findViewById(R.id.userPic);
        if (uN != null){
            uN.setText(acct.getDisplayName());
            uE.setText(acct.getEmail());
        } else {
            Log.d("NAV", "fail");
        }

        //uP.setImageURI(acct.getPhotoUrl());
        if (acct.getPhotoUrl() != null) {
            FetchProfilePic getPic = new FetchProfilePic();
            getPic.Initialize(acct.getPhotoUrl().toString(), uP, this);
            //Drawable profPic = LoadImageFromWeb(acct.getPhotoUrl().toString());
            //uP.setImageDrawable(profPic);
            //Log.d("URI:", profPic.toString());
        } else{
            if (uN != null){
                uP.setImageResource(R.drawable.ic_profilepic);
            }
        }

        /*try {
            InputStream is = (InputStream) new URL(url).getContent();
            Log.d("inFunc:", is.toString());
            Drawable d = Drawable.createFromStream(is, "src name");
            return d;
        } catch (Exception e) {
            Log.d("inFunc:", e.toString());
            return null;
        }*/
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signin_main);
        mStatusTextView = (TextView) findViewById(R.id.status);

        // Button listeners
        findViewById(R.id.sign_in_button).setOnClickListener(this);
        // [START configure_signin]
        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestScopes(new Scope(Scopes.PROFILE))
                .requestEmail()
                .build();
        // [END configure_signin]

        // [START build_client]
        // Build a GoogleApiClient with access to the Google Sign-In API and the
        // options specified by gso.
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        // [END build_client]

        // [START customize_button]
        // Customize sign-in button. The sign-in button can be displayed in
        // multiple sizes and color schemes. It can also be contextually
        // rendered based on the requested scopes. For example. a red button may
        // be displayed when Google+ scopes are requested, but a white button
        // may be displayed when only basic profile is requested. Try adding the
        // Scopes.PLUS_LOGIN scope to the GoogleSignInOptions to see the
        // difference.
        SignInButton signInButton = (SignInButton) findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_STANDARD);
        signInButton.setScopes(gso.getScopeArray());
        // [END customize_button]
    }

    @Override
    public void onStart() {
        super.onStart();

        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(mGoogleApiClient);
        if (opr.isDone()) {
            // If the user's cached credentials are valid, the OptionalPendingResult will be "done"
            // and the GoogleSignInResult will be available instantly.
            Log.d(TAG, "Got cached sign-in");
            GoogleSignInResult result = opr.get();
            handleSignInResult(result);
        } else {
            // If the user has not previously signed in on this device or the sign-in has expired,
            // this asynchronous branch will attempt to sign in the user silently.  Cross-device
            // single sign-on will occur in this branch.
            showProgressDialog();
            opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                @Override
                public void onResult(GoogleSignInResult googleSignInResult) {
                    hideProgressDialog();
                    handleSignInResult(googleSignInResult);
                }
            });
        }
    }

    // [START onActivityResult]
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }
    // [END onActivityResult]

    // [START handleSignInResult]
    private void handleSignInResult(GoogleSignInResult result) {
        Log.d(TAG, "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            acct = result.getSignInAccount();
            mStatusTextView.setText(getString(R.string.signed_in_fmt, acct.getDisplayName()));
            updateUI(true);
        } else {
            // Signed out, show unauthenticated UI.
            updateUI(false);
        }
    }
    // [END handleSignInResult]

    // [START signIn]
    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    // [END signIn]

    // [START signOut]
    private void signOut() {
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        // [START_EXCLUDE]
                        updateUI(false);
                        // [END_EXCLUDE]
                    }
                });
    }
    // [END signOut]

    // [START revokeAccess]
    /*private void revokeAccess() {
        Auth.GoogleSignInApi.revokeAccess(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        // [START_EXCLUDE]
                        updateUI(false);
                        // [END_EXCLUDE]
                    }
                });
    }*/
    // [END revokeAccess]

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        // An unresolvable error has occurred and Google APIs (including Sign-In) will not
        // be available.
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
    }

    private void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage(getString(R.string.loading));
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.show();
    }

    private void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.hide();
        }
    }

    private void updateUI(boolean signedIn) {
        if (signedIn) {
            setupHome();
            //MainActivity startSession = new MainActivity();
            //findViewById(R.id.sign_in_button).setVisibility(View.GONE);
            //findViewById(R.id.sign_out_and_disconnect).setVisibility(View.VISIBLE);
        } else {
            mStatusTextView.setText(R.string.signed_out);

            //findViewById(R.id.sign_in_button).setVisibility(View.VISIBLE);
            //findViewById(R.id.sign_out_and_disconnect).setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sign_in_button:
                signIn();
                break;
            /*case R.id.sign_out_button:
                signOut();
                break;
            case R.id.disconnect_button:
                revokeAccess();
                break;*/
        }
    }

    private void setupHome(){
        setContentView(R.layout.main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (currFrag == null){
            fragment = new HomeFragment();
            fragmentTransaction = getSupportFragmentManager().beginTransaction().add(R.id.container, fragment);
            fragmentTransaction.commit();
            currFrag = "home";
            setTitle("HOME");
            Log.d("FRAGS:", Integer.toString(getSupportFragmentManager().getBackStackEntryCount()));
            getSupportFragmentManager().popBackStack();
        }

        //getDateTime();

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
        getMenuInflater().inflate(R.menu.main, menu);
        getGoogleInfo();

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d("onOptionsItemSelected", "AAY");
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            setContentView(R.layout.signin_main);
            signOut();
            findViewById(R.id.sign_in_button).setOnClickListener(this);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        Log.d("onNavigationItemSelec", "AAY");
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            Log.d("Nav", "fragment_home");
            if (currFrag != "home"){
                fragment = new HomeFragment();
                fragmentTransaction = getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment);
                fragmentTransaction.commit();
                currFrag = "home";
                setTitle("HOME");
            }
        } else if (id == R.id.nav_announce) {
            Log.d("Nav", "news");
            if (currFrag != "announce"){
                fragment = new AnnounceFragment();
                fragmentTransaction = getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment);
                fragmentTransaction.commit();
                currFrag = "announce";
                setTitle("DAILY ANNOUNCEMENTS");
            }
        } else if (id == R.id.nav_calendar) {
            Log.d("Nav", "cal");
            if (currFrag != "calendar"){
                fragment = new CalendarFragment();
                fragmentTransaction = getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment);
                fragmentTransaction.commit();
                currFrag = "calendar";
                setTitle("CALENDAR");
            }
        } else if (id == R.id.nav_classes) {
            Log.d("Nav", "class");
            if (currFrag != "classes"){
                fragment = new ClassesFragment();
                fragmentTransaction = getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment);
                fragmentTransaction.commit();
                currFrag = "classes";
                setTitle("CLASSES");
            }
        } else if (id == R.id.nav_marks) {
            Log.d("Nav", "mark");
            if (currFrag != "marks"){
                fragment = new MarksFragment();
                fragmentTransaction = getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment);
                fragmentTransaction.commit();
                currFrag = "marks";
                setTitle("MARKS");
            }
        } else if (id == R.id.nav_homework) {
            Log.d("Nav", "hw");
            if (currFrag != "homework"){
                fragment = new HomeworkFragment();
                fragmentTransaction = getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment);
                fragmentTransaction.commit();
                currFrag = "homework";
                setTitle("HOMEWORK");
            }
        } else if (id == R.id.nav_info) {
            Log.d("Nav", "hw");
            if (currFrag != "about"){
                fragment = new AboutFragment();
                fragmentTransaction = getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment);
                fragmentTransaction.commit();
                currFrag = "about";
                setTitle("ABOUT");
            }
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
