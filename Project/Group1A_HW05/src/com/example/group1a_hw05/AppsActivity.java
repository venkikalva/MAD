package com.example.group1a_hw05;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
/*
 * Team : Ashraf Cherukuru, Savitha Doure, Venkatesh Kalva
 * */
public class AppsActivity extends Activity implements SetUpData {
	ProgressDialog progress;
	ListView mainListView;
	SharedPreferences preference;
	public static final String MyPREFERENCES = "MyPrefs";
	List<Item> items;
	ArrayList<Item> totalItemList = new ArrayList<Item>();
	Item sitem;
	Gson gson;
	static String viewAll = "no";
	ArrayList<Item> favList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_apps);
		new GetListPrograms("applist")
				.execute("https://itunes.apple.com/us/rss/topgrossingapplications/limit=25/xml");
		mainListView = (ListView) findViewById(R.id.listView1);
	}

	class GetListPrograms extends AsyncTask<String, Void, ArrayList<Item>> {
		String loadingitem = "";

		public GetListPrograms(String loadingItem) {
			this.loadingitem = loadingItem;
		}

		@Override
		protected ArrayList<Item> doInBackground(String... params) {
			URL url;
			try {
				url = new URL(params[0]);
				HttpURLConnection con = (HttpURLConnection) url
						.openConnection();
				con.connect();
				int statusCode = con.getResponseCode();
				if (statusCode == HttpURLConnection.HTTP_OK) {

					return ParseUtil.SaxParser.parserXMl(con.getInputStream());
				}
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return null;
		}

		@Override
		protected void onPreExecute() {
			progress = new ProgressDialog(AppsActivity.this);
			progress.setMessage("Loading " + loadingitem);
			progress.setCancelable(false);
			progress.show();
			super.onPreExecute();
		}

		@Override
		protected void onPostExecute(ArrayList<Item> result) {
			Log.d("demo", result.toString());
			progress.cancel();
			totalItemList = result;
			/*SingleItemAdapter adapter = new SingleItemAdapter(
					AppsActivity.this, result);
			setData(adapter, totalItemList);*/
			//adapter.setNotifyOnChange(true);
			super.onPostExecute(result);
		}

		@Override
		protected void onProgressUpdate(Void... values) {
			// TODO Auto-generated method stub
			super.onProgressUpdate(values);
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
		if (id == R.id.action_view_all) {
			if(viewAll.equalsIgnoreCase("yes")){
				new GetListPrograms("applist")
				.execute("https://itunes.apple.com/us/rss/topgrossingapplications/limit=25/xml");
				viewAll = "no";	
			}
			return true;
		 

		} else if (id == R.id.action_logout) {

			ParseUser.getCurrentUser();
			ParseUser.logOut();
			Intent intent = new Intent(AppsActivity.this, LoginActivity.class);
			startActivity(intent);
			Toast.makeText(AppsActivity.this, "Loged Out Successfully",
					Toast.LENGTH_LONG).show();
			finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void setData(ArrayAdapter<Item> adapter, ArrayList<Item> itemList) {
		totalItemList = itemList;
		mainListView.setAdapter(adapter);
		mainListView
				.setOnItemClickListener(new AdapterView.OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {

						Item singleItem = totalItemList.get(position);

						Intent intent = new Intent(AppsActivity.this,
								PreviewActivity.class);
						intent.putExtra("single", singleItem);
						startActivity(intent);

					}
				});

	}

	@Override
	public void onBackPressed() {
		if(viewAll.equalsIgnoreCase("yes")){
			AppsActivity.this.recreate();
			viewAll = "no";
			
		}else{
			finish();
		}
	}

	

	@Override
	public void setDetails(SingleItemAdapter adapter,
			List<PlaceDetails> itemList, Context context) {
		// TODO Auto-generated method stub
		
	}

}
