package com.example.group1a_hw05;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import org.json.JSONObject;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.media.Image.Plane;
import android.os.AsyncTask;
import android.util.Log;
public class BackgroundTask {
	static Address fetchedAddress;
	static double latitude;
	static double longitude;
static PlaceDetailsActivity activity;

	static class GeoTask extends AsyncTask<String, Void, List<Address>> {
		Context mContext;
		senddata listner;

		public GeoTask(Context context,senddata activity) {
			this.mContext = context;
			this.listner=activity;
			
			
		}

		@Override
		protected List<Address> doInBackground(String... params) {
			List<Address> addressList = null;

			Geocoder geoCoder = new Geocoder(mContext);

			try {
				Log.d("Inclass9b", "In try");
				addressList = geoCoder.getFromLocationName(params[0], 1);

			} catch (IOException e) {
				Log.d("InClass9b", e.toString());
				e.printStackTrace();
			}

			return addressList;
		}

		@Override
		protected void onPostExecute(List<Address> result) {
			if (result == null) {
				Log.d("Inclass9b", "Result null");
			} else {
				Log.d("Inclass9b", "Size: " + result.size());
				if (result.size() > 0) {
					fetchedAddress = result.get(0);
					latitude = fetchedAddress.getLatitude();
					longitude = fetchedAddress.getLongitude();

					StringBuilder sb = new StringBuilder(
							"https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
					sb.append("location=" + latitude + "," + longitude);
					sb.append("&radius=5000");
					sb.append("&types=" + "restaurant");
					sb.append("&key=AIzaSyB4LICuTqTClu8vkkQvpM7v2US23LANqPk");
					sb.append("&sensor=true");

					// Creating a new non-ui thread task to download json data
					PlacesTask placesTask = new  BackgroundTask.PlacesTask(mContext,listner);

					// Invokes the "doInBackground()" method of the class
					// PlaceTask
					placesTask.execute(sb.toString());
					Log.d("url", sb.toString());

				} else
					fetchedAddress = null;
			}
		}
	}

	static class PlacesTask extends AsyncTask<String, Integer, String> {
		Context mContext;
		senddata listner;
		public PlacesTask(Context context,senddata listner) {
			this.mContext = context;
			this.listner=listner;
		}
		String data = null;

		// Invoked by execute() method of this object
		@Override
		protected String doInBackground(String... url) {
			try {
				data = downloadUrl(url[0]);
			} catch (Exception e) {
				Log.d("Background Task", e.toString());
			}
			return data;
		}

		// Executed after the complete execution of doInBackground() method
		@Override
		protected void onPostExecute(String result) {
			ParserTask parserTask = new ParserTask(mContext,listner);

			// Start parsing the Google places in JSON format
			// Invokes the "doInBackground()" method of the class ParseTask
			parserTask.execute(result);
		}

	}

	private static String downloadUrl(String strUrl) throws IOException {
		String data = "";
		InputStream iStream = null;
		HttpURLConnection urlConnection = null;
		try {
			URL url = new URL(strUrl);
			urlConnection = (HttpURLConnection) url.openConnection();
			urlConnection.connect();
			int statusCode = urlConnection.getResponseCode();
			if(statusCode==HttpURLConnection.HTTP_OK){
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

		}
		}catch (Exception e) {
			Log.d("Exception while downloading url", e.toString());
		} finally {
			iStream.close();
			urlConnection.disconnect();
		}

		return data;

	}

	static class ParserTask extends
			AsyncTask<String, Integer, List<PlaceDetails>> {
		Context mContext;
		senddata listener;
		public ParserTask(Context context,senddata listner) {
			this.mContext = context;
			this.listener=listner;
		}
		JSONObject jObject;

		// Invoked by execute() method of this object
		@Override
		protected List<PlaceDetails> doInBackground(
				String... jsonData) {

			List<PlaceDetails> places = null;
			PlaceJSONParser placeJsonParser = new PlaceJSONParser();

			try {
				jObject = new JSONObject(jsonData[0]);

				/** Getting the parsed data as a List construct */
				places = placeJsonParser.parse(jObject);

			} catch (Exception e) {
				Log.d("Exception", e.toString());
			}
			return places;
		}

		// Executed after the complete execution of doInBackground() method
		@Override
		protected void onPostExecute(List<PlaceDetails> list) {
			SingleItemAdapter adapter = new SingleItemAdapter(mContext
					, list);
			PlaceDetailsActivity pd = new PlaceDetailsActivity();
			
			//pd.setDetails(adapter, list,mContext);
			listener.setDetails(adapter, list);
			
			
		}
	}
	
	public interface senddata{
		public void setDetails(SingleItemAdapter adapter,List<PlaceDetails> list);
	}
}
