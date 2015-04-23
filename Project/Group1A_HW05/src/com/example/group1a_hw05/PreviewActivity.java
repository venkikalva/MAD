package com.example.group1a_hw05;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import com.google.gson.Gson;
import com.parse.DeleteCallback;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.squareup.picasso.Picasso;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuItem;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View;
/*
 * Team : Ashraf Cherukuru, Savitha Doure, Venkatesh Kalva
 * */
public class PreviewActivity extends Activity implements View.OnClickListener {

ImageView image,weatherSym;
TextView title,temp;
TextView address;
String imagLink;
PlaceDetails placeDetails;
double lat;
double lan;
WeatherDetail weatherDetails;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_preview);
		
		if(getIntent().getExtras().getSerializable("single")!=null){
			 placeDetails = (PlaceDetails) getIntent().getExtras().getSerializable("single");
			imagLink=placeDetails.getImageUrl();
		}
		
		image = (ImageView) findViewById(R.id.imageView1);
		weatherSym = (ImageView) findViewById(R.id.imageView4);
		title = (TextView) findViewById(R.id.cityname1);
		address = (TextView) findViewById(R.id.address);
		temp = (TextView) findViewById(R.id.temp);
		weatherSym.setOnClickListener(this);
			if (imagLink == null || "".equalsIgnoreCase(imagLink)) {
				image.setImageResource(R.drawable.photo_not_found);
			} else
				Picasso.with(PreviewActivity.this).load(imagLink).into(image);
			
			title.setText(placeDetails.getPlaceName());
			address.setText(placeDetails.getVicinity());
			lat=  placeDetails.getLat();
			lan= placeDetails.getLngt();
			new WeatherTask().execute("http://api.openweathermap.org/data/2.5/forecast/daily?lat="+lat+"&lon="+lan+"&cnt=10&&units=imperial&mode=jsonc");
		}
	

	@Override
	public void onClick(View v) {
		if(v.getId()==R.id.imageView4){

			Intent intent = new Intent(PreviewActivity.this,WeatherActivity.class);
			intent.putExtra("weather", weatherDetails);
			startActivity(intent);	
		}
	}
	
	class WeatherTask extends AsyncTask<String, Void, WeatherDetail>{
		String data = null;
		JSONObject jObject;
		WeatherDetail places = null;
		@Override
		protected WeatherDetail doInBackground(String... params) {
			// TODO Auto-generated method stub
			try {
				data = downloadUrl(params[0]);
				PlaceJSONParser placeJsonParser = new PlaceJSONParser();

				try {
					jObject = new JSONObject(data);

					/** Getting the parsed data as a List construct */
					places = placeJsonParser.parseWeather(jObject);

				} catch (Exception e) {
					Log.d("Exception", e.toString());
				}
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return places;
		}

		@Override
		protected void onPostExecute(WeatherDetail result) {
			// TODO Auto-generated method stub
			//super.onPostExecute(result);
			List<DailyTemp> dailyTempList = result.getDailyTemp();
			String dayTemp = dailyTempList.get(0).getDay();
			temp.setText(dayTemp);
			weatherDetails = result;
		}
		
		
	}
	
	
	private static String downloadUrl(String strUrl) throws IOException {
		String data = "";
		InputStream iStream = null;
		HttpURLConnection urlConnection = null;
		try {
			URL url = new URL(strUrl);

			// Creating an http connection to communicate with url
			urlConnection = (HttpURLConnection) url.openConnection();

			// Connecting to url
			urlConnection.connect();

			// Reading data from url
			iStream = urlConnection.getInputStream();

			BufferedReader br = new BufferedReader(new InputStreamReader(
					iStream));

			StringBuffer sb = new StringBuffer();

			String line = "";
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}

			data = sb.toString();

			br.close();

		} catch (Exception e) {
			Log.d("Exception while downloading url", e.toString());
		} finally {
			iStream.close();
			urlConnection.disconnect();
		}

		return data;

	}

	
	}



