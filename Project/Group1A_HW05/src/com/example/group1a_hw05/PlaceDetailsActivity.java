package com.example.group1a_hw05;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.mad.adapter.SingleItemAdapter;
import com.mad.bean.PlaceDetails;

public class PlaceDetailsActivity extends Activity implements View.OnClickListener,BackgroundTask.senddata{
ImageView search;
Button enter;
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
		enter = (Button)findViewById(R.id.enter);
		cityname = (EditText)findViewById(R.id.cityname);
		search.setOnClickListener(this);
		enter.setOnClickListener(this);
		//new BackgroundTask.GeoTask(PlaceDetailsActivity.this,PlaceDetailsActivity.this).execute(cityName);
		context=this;
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.enter:
			String cityName= cityname.getText().toString();
			new BackgroundTask.GeoTask(PlaceDetailsActivity.this,PlaceDetailsActivity.this).execute(cityName);
			//new BackgroundTask.ParserTask(PlaceDetailsActivity.this,PlaceDetailsActivity.this);
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
	
	 public String getLocationName(double lattitude, double longitude) {

		    String cityName = "Not Found";
		    Geocoder gcd = new Geocoder(getBaseContext(), Locale.getDefault());
		    try {

		        List<Address> addresses = gcd.getFromLocation(lattitude, longitude,
		                10);

		        for (Address adrs : addresses) {
		            if (adrs != null) {

		                String city = adrs.getLocality();
		                if (city != null && !city.equals("")) {
		                    cityName = city;
		                    System.out.println("city ::  " + cityName);
		                } else {

		                }
		                // // you should also try with addresses.get(0).toSring();

		            }

		        }
		    } catch (IOException e) {
		        e.printStackTrace();
		    }
		    return cityName;

		}	
		
}
