package com.example.group1a_hw05;

import java.util.ArrayList;
import java.util.List;

import com.example.group1a_hw05.AppsActivity.GetListPrograms;
import com.google.gson.Gson;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class EditTripActivity extends Activity implements View.OnClickListener{
PlaceDetails placeDetails;
List<PlaceDetails> placeList;
ListView edittriplist;
ImageView addteditrip;
SharedPlacedDetails details;
SharedPreferences preference;
ParseUser currentUser;
public static final String MyPREFERENCES = "MyPrefs";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_trip);
		currentUser = ParseUser.getCurrentUser();
		placeList = new ArrayList<PlaceDetails>();
		details = new SharedPlacedDetails();
		edittriplist = (ListView) findViewById(R.id.edittriplist);
		addteditrip = (ImageView) findViewById(R.id.addtrip);
		addteditrip.setOnClickListener(this);
		SharedPreferences preference = getApplicationContext()
				.getSharedPreferences(EditTripActivity.MyPREFERENCES,
						Context.MODE_PRIVATE);
		String json = preference.getString("placesList", null);
		if(json!=null){

			Gson gson = new Gson();
			details  = gson.fromJson(json, SharedPlacedDetails.class);	
			placeList = details.getPlaceList();

			if(getIntent().getExtras().getSerializable("edittripdetaiils")!=null){
				placeDetails = (PlaceDetails) getIntent().getExtras().getSerializable("edittripdetaiils");
				placeList.add(placeDetails);
			}
		}else{

			if(getIntent().getExtras().getSerializable("edittripdetaiils")!=null){
				placeDetails = (PlaceDetails) getIntent().getExtras().getSerializable("edittripdetaiils");
				placeList.add(placeDetails);
			}	
		}
		String tripName = preference.getString("tripname", null);
		String travelDate = preference.getString("traveldate", null);
		details.setTripName(tripName);
		details.setTraveldate(travelDate);
		details.setPlaceList(placeList);
		SingleEditWeatherAdapter adapter = new SingleEditWeatherAdapter(this
				, placeList);
		edittriplist.setAdapter(adapter);
		adapter.setNotifyOnChange(true);
	}
	@Override
	public void onClick(View v) {
		if(v.getId()==R.id.addtrip){
			preference = getApplicationContext().getSharedPreferences(
					EditTripActivity.MyPREFERENCES, Context.MODE_PRIVATE);
			Editor editor = preference.edit();
			Gson gson = new Gson();
			String json = gson.toJson(details);
			editor.putString("placesList", json);
			editor.commit();
			Intent myIntent = new Intent(EditTripActivity.this,SelectPlaceActivity.class);
			startActivity(myIntent);
			
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.apps_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.save) {
			ParseObject todo = new ParseObject("SavTripList");
			Gson gson = new Gson();
			String json = gson.toJson(details);
			todo.put("savetrip", json);
			todo.put("user", currentUser);
			todo.saveInBackground(new SaveCallback() {

				@Override
				public void done(ParseException e) {
					// TODO Auto-generated method stub
					if (e == null) {
						preference = getApplicationContext().getSharedPreferences(
								EditTripActivity.MyPREFERENCES, Context.MODE_PRIVATE);
						Editor editor = preference.edit();
			    		editor.clear();
			    		editor.commit();
						Intent intent = new Intent(EditTripActivity.this,ViewTripActivity.class);
						startActivity(intent);
						
						
					} else {
						Toast.makeText(EditTripActivity.this,
								e.getMessage(), Toast.LENGTH_LONG)
								.show();
					}
				}
			});
			return true;
		 

		}
		return super.onOptionsItemSelected(item);
	}
	
}
