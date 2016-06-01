package com.example.sunshine;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;



 @TargetApi(Build.VERSION_CODES.HONEYCOMB) public class MainActivity extends ActionBarActivity {

     public static final String LOG_TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new ForecastFragment())
                    .commit();
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
        if (id == R.id.action_settings) {
            Intent startsettingsactivity = new Intent(this, SettingsActivity.class);
            startActivity(startsettingsactivity);
            return true;
        }
        else if(id == R.id.pref_location){
            openPreferredLocationInMap();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

     public void openPreferredLocationInMap(){

         SharedPreferences sharedpref = PreferenceManager.getDefaultSharedPreferences(this);
         String location = sharedpref.getString(getString(R.string.pref_location_key), getString(R.string.pref_location_default));

         Uri geolocation = Uri.parse("geo:0").buildUpon().appendQueryParameter("q", location).build();

         Intent intent = new Intent(Intent.ACTION_VIEW);
         intent.setData(geolocation);

         if(intent.resolveActivity(getPackageManager()) != null)
             startActivity(intent);
         else
             Log.d(LOG_TAG, "Couldn't start intent on" + location);
     }
    /**
     * A placeholder fragment containing a simple view.
     */
    
}

