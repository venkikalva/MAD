package com.example.group1a_hw05;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.mad.adapter.SingleEditWeatherAdapter;
import com.mad.bean.PlaceDetails;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.location.Location;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class EditTripActivity extends Activity implements View.OnClickListener {
	PlaceDetails placeDetails;
	List<PlaceDetails> placeList;
	ListView edittriplist;
	ImageView addteditrip;
	SharedPlacedDetails details;
	SharedPreferences preference;
	ParseUser currentUser;
	String tripName, travelDate;
	String update = "no";

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
		if (json != null) {

			Gson gson = new Gson();
			details = gson.fromJson(json, SharedPlacedDetails.class);
			placeList = details.getPlaceList();

			if (getIntent().getExtras().getSerializable("edittripdetaiils") != null) {
				placeDetails = (PlaceDetails) getIntent().getExtras()
						.getSerializable("edittripdetaiils");
				placeList.add(placeDetails);
			}
		} else {

			if (getIntent().getExtras().getSerializable("edittripdetaiils") != null) {
				placeDetails = (PlaceDetails) getIntent().getExtras()
						.getSerializable("edittripdetaiils");
				placeList.add(placeDetails);
			}
		}
			String updates = preference.getString("update", null);
		if(updates!=null){
		update = "yes";	
		}
		tripName = preference.getString("tripname", null);
		travelDate = preference.getString("traveldate", null);

		if (getIntent().getExtras().getSerializable("single") != null) {
			SharedPlacedDetails viewDetails = (SharedPlacedDetails) getIntent()
					.getExtras().getSerializable("single");
			tripName = viewDetails.getTripName();
			travelDate = viewDetails.getTraveldate();
			placeList = viewDetails.getPlaceList();
			update = "yes";

		}
		details.setTripName(tripName);
		details.setTraveldate(travelDate);
		if (placeList.size() > 1) {
			for (int i = 0; i < placeList.size() - 1; i++) {
				Location loc1 = new Location("");
				loc1.setLatitude(placeList.get(i).getLat());
				loc1.setLongitude(placeList.get(i).getLngt());
				Location loc2 = new Location("");
				loc2.setLatitude(placeList.get(i + 1).getLat());
				loc2.setLongitude(placeList.get(i + 1).getLngt());
				float distance = loc1.distanceTo(loc2) / 1609;
				NumberFormat formatter = NumberFormat.getCurrencyInstance();
				String output = formatter.format(distance);
				placeList.get(i).setDistance(Float.parseFloat(output));
			}
		}
		details.setPlaceList(placeList);
		SingleEditWeatherAdapter adapter = new SingleEditWeatherAdapter(this,
				placeList);
		edittriplist.setAdapter(adapter);
		adapter.setNotifyOnChange(true);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.addtrip:
			preference = getApplicationContext().getSharedPreferences(
					EditTripActivity.MyPREFERENCES, Context.MODE_PRIVATE);
			Editor editor = preference.edit();
			Gson gson = new Gson();
			String json = gson.toJson(details);
			editor.putString("placesList", json);
			editor.commit();
			Intent myIntent = new Intent(EditTripActivity.this,
					SelectPlaceActivity.class);
			startActivity(myIntent);
			break;
		case R.id.save:
			saveATrip();
			break;

		default:
			break;
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
		if (id == R.id.logout) {
		

		}
		return super.onOptionsItemSelected(item);
	}
	
	
	private void saveATrip(){
		if (update.equalsIgnoreCase("yes")) {
			Gson gson = new Gson();
			final String json = gson.toJson(details);
			ParseQuery<ParseObject> query = ParseQuery
					.getQuery("SavTripList");
			query.whereEqualTo("user", ParseUser.getCurrentUser());
			query.whereEqualTo("tripname", tripName);
			query.findInBackground(new FindCallback<ParseObject>() {

				@Override
				public void done(List<ParseObject> objects, ParseException e) {
					for (ParseObject p : objects) {
						p.put("savetrip", json);
						p.saveInBackground(new SaveCallback() {
							
							@Override
							public void done(ParseException e) {
								if(e==null){
									
									Intent intent = new Intent(EditTripActivity.this,
											TabViewActivity.class);
									startActivity(intent);
								}
								
							}
						});
					}

				}
			});
		} else {
			ParseObject todo = new ParseObject("SavTripList");
			Gson gson = new Gson();
			String json = gson.toJson(details);
			todo.put("savetrip", json);
			todo.put("user", currentUser);
			todo.put("tripname", tripName);
			todo.saveInBackground(new SaveCallback() {

				@Override
				public void done(ParseException e) {
					// TODO Auto-generated method stub
					if (e == null) {
						preference = getApplicationContext()
								.getSharedPreferences(
										EditTripActivity.MyPREFERENCES,
										Context.MODE_PRIVATE);
						Editor editor = preference.edit();
						editor.clear();
						editor.commit();
						Intent intent = new Intent(EditTripActivity.this,
								TabViewActivity.class);
						startActivity(intent);

					} else {
						Toast.makeText(EditTripActivity.this,
								e.getMessage(), Toast.LENGTH_LONG).show();
					}
				}
			});
		}

		
	}

}
