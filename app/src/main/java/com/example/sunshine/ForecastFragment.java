package com.example.sunshine;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.format.Time;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class ForecastFragment extends Fragment {

	private ArrayAdapter<String> arr_adap;
	
	public ForecastFragment() {
	}

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.forecastfragment, menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		int id = item.getItemId();
        if (id == R.id.action_refresh) {
        	updateweather();;
            return true;
        }
        return super.onOptionsItemSelected(item);
	}

	@Override
	public void onStart() {
		super.onStart();
		updateweather();
	}

	public void updateweather(){
		FetchWeatherTask weathertask = new FetchWeatherTask(getActivity(), arr_adap);
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
		String location = prefs.getString(getString(R.string.pref_location_key), getString(R.string.pref_location_default));
		weathertask.execute(location);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_main, container,
				false);
		
		

		ArrayList<String> data = new ArrayList<>();

		arr_adap = new ArrayAdapter<>(
				getActivity(),
				R.layout.list_item_forecast, 
				R.id.list_item_forecast_textview,
				data);

		ListView lv = (ListView) rootView.findViewById(R.id.listview_forecast);
		lv.setAdapter(arr_adap);
		lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				
				String text = arr_adap.getItem(arg2);
//				Toast toast = Toast.makeText(getContext(), text, Toast.LENGTH_SHORT);
//				toast.show();
				Intent intent = new Intent(getActivity(), DetailActivity.class).putExtra(Intent.EXTRA_TEXT, text);
				startActivity(intent);
				}
			
		}
				); 
		

		return rootView;

	}



}