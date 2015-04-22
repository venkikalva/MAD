package com.example.group1a_hw05;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.mad.adapter.SingleTripViewAdapter;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ViewTripActivity extends Activity {
List<SharedPlacedDetails> viewTripList;
ListView tripListView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_trip);
		tripListView = (ListView) findViewById(R.id.viewtrip);
		viewShared();
	}
	
	
	private void viewShared(){
		
		viewTripList = new ArrayList<SharedPlacedDetails>();
		ParseQuery<ParseObject> query = ParseQuery.getQuery("SavTripList");
		query.whereEqualTo("user", ParseUser.getCurrentUser());
		query.findInBackground(new FindCallback<ParseObject>() {

			@Override
			public void done(List<ParseObject> objects, ParseException e) {
				if(objects.size()>0){
					SharedPlacedDetails singletrip;
				Gson gson = new Gson();
				for (ParseObject p : objects) {
					String savObj = p.getString("savetrip");
					singletrip = gson.fromJson(savObj, SharedPlacedDetails.class);
					viewTripList.add(singletrip);
				}
				SingleTripViewAdapter adapter = new SingleTripViewAdapter(
						ViewTripActivity.this, viewTripList);
				tripListView.setAdapter(adapter);
				adapter.setNotifyOnChange(true);
				

			}
			else{
				Toast.makeText(ViewTripActivity.this," No Favourites to show", Toast.LENGTH_LONG).show();
			}	
			}
		});

		
	}
}
