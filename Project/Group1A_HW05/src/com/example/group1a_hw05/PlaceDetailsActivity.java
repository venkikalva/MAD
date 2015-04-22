package com.example.group1a_hw05;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

import com.mad.adapter.SingleItemAdapter;
import com.mad.bean.PlaceDetails;
import com.mad.util.*;

import android.app.Activity;
import android.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

public class PlaceDetailsActivity extends Activity implements View.OnClickListener,BackgroundTask.senddata{
ImageView search;
ImageView gps;
EditText cityname;
LocationManager locManager;
LocationListener mlistner;
public static double lat;
public static double lng;
public static List<PlaceDetails> listOfplaces;
ListView mailListView;
List<PlaceDetails> totalItemList;
Context context;
View view;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_place_details);
		 view = findViewById(R.layout.activity_place_details);
		
		locManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		search = (ImageView)findViewById(R.id.search);
		mailListView = (ListView) findViewById(R.id.listView1);
		gps = (ImageView)findViewById(R.id.gps);
		cityname = (EditText)findViewById(R.id.cityname);
		search.setOnClickListener(this);
		context=this;
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.search:
			String cityName= cityname.getText().toString();
			new BackgroundTask.GeoTask(PlaceDetailsActivity.this,PlaceDetailsActivity.this).execute(cityName);
			//new BackgroundTask.ParserTask(PlaceDetailsActivity.this,PlaceDetailsActivity.this);
			break;
		case R.id.gps:
			if(!locManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
				AlertDialog.Builder builder = new AlertDialog.Builder(this);
				builder.setTitle("GPS is not enables")
				.setMessage("Would You like to turn on GPS")
				.setPositiveButton("Yes", new DialogInterface.OnClickListener(){

					@Override
					public void onClick(DialogInterface dialog, int which) {
						Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
						startActivity(intent);
						
					}
					
					
				}).setNegativeButton("No", new DialogInterface.OnClickListener(){

					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
						finish();
						
					}
					
					
				});
				AlertDialog alert = builder.create();
				alert.show();
			}else{
				mlistner = new LocationListener() {
					
					@Override
					public void onStatusChanged(String provider, int status, Bundle extras) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void onProviderEnabled(String provider) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void onProviderDisabled(String provider) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void onLocationChanged(Location location) {
						new BackgroundTask.GeoTask(PlaceDetailsActivity.this,PlaceDetailsActivity.this).execute(location.toString());
						
					}
				};
				
			}
			break;

		default:
			break;
		}
		
	}
//	@Override
//	public void setDetails(SingleItemAdapter adapter, List<PlaceDetails> list) {
//		// TODO Auto-generated method stub
//		
//	}
	
	
	@Override
	public  void setDetails(SingleItemAdapter adapter,
			List<PlaceDetails> itemList) {
		Log.d("list",itemList.toString());
		//PlaceDetailsActivity context = (PlaceDetailsActivity) mcontext;
		//Context context = PlaceDetailsActivity.this;
		
		//mailListView = (ListView) ((PlaceDetailsActivity) context).findViewById(R.id.listView1);
		//mailListView=(ListView) view.findViewById(R.id.listView1);
		totalItemList = itemList;
		mailListView.setAdapter(adapter);
		mailListView
				.setOnItemClickListener(new AdapterView.OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {

						PlaceDetails singleItem = totalItemList.get(position);

						Intent intent = new Intent(PlaceDetailsActivity.this,
								PreviewActivity.class);
						intent.putExtra("single", singleItem);
						startActivity(intent);

					}
				});
	}
	
	
		
}
