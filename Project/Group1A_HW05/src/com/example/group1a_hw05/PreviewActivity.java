package com.example.group1a_hw05;

import java.util.ArrayList;
import java.util.List;

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
	ArrayList<ParseUser> favlist;
	TextView title;
	ImageView image;
	SharedPreferences preference;
	String url;
	Item singleItem = null;
	ParseUser currentUser;
	ArrayList<String> usernames;
	List<ParseUser> sharedUser;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_preview);

		currentUser = ParseUser.getCurrentUser();
		title = (TextView) findViewById(R.id.textView1);
		image = (ImageView) findViewById(R.id.imageView1);
		image.setOnClickListener(this);
		if (getIntent().getExtras() != null) {
			singleItem = (Item) getIntent().getExtras().getSerializable(
					"single");
			title.setText(singleItem.getTitle());
			String imgLink = singleItem.getLargeImgUrl();
			url = singleItem.getAppUrl();
			if (imgLink == null || "".equalsIgnoreCase(imgLink)) {
				image.setImageResource(R.drawable.photo_not_found);
			} else
				Picasso.with(PreviewActivity.this).load(imgLink).into(image);
		}

		usernames = new ArrayList<String>();
		sharedUser = new ArrayList<ParseUser>();
		ParseQuery<ParseUser> query1 = ParseUser.getQuery();
		query1.whereNotEqualTo("username", currentUser.getEmail());
		query1.addAscendingOrder("lName");
		query1.findInBackground(new FindCallback<ParseUser>() {

			@Override
			public void done(List<ParseUser> objects, ParseException e) {
				sharedUser = objects;
				for (ParseUser p : objects) {
					String fname = p.getString("fName");
					String fullname = fname + p.getString("lName");
					usernames.add(fullname);
				}

			}
		});
	
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.imageView1:
			try {
				Intent myIntent = new Intent(PreviewActivity.this,WebActivity.class);
				myIntent.putExtra("url", url);
				startActivity(myIntent);
			} catch (ActivityNotFoundException e) {
				Toast.makeText(
						this,
						"No application can handle this request."
								+ " Please install a webbrowser",
						Toast.LENGTH_LONG).show();
				e.printStackTrace();
			}

			break;
		
		default:
			break;
		}

	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		menu.setHeaderTitle("Users");
		for (String name : usernames) {
			menu.add(name);
		}

		super.onCreateContextMenu(menu, v, menuInfo);
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item
				.getMenuInfo();
		String name = item.getTitle().toString();
		String sharedUserID = "";
		ParseObject todo = new ParseObject("SharedList");
		Gson gson = new Gson();
		for (ParseUser p : sharedUser) {
			if (name.equalsIgnoreCase(p.get("fName").toString()+p.getString("lName"))) {
				sharedUserID = p.getUsername();
			}
		}
		String json = gson.toJson(singleItem);
		todo.put("sharedItem", json);
		todo.put("sharedUser", sharedUserID);
		todo.put("currentUser", currentUser);
		todo.saveInBackground();
		Toast.makeText(PreviewActivity.this, "Shared With" + name, Toast.LENGTH_LONG)
				.show();
		Log.d("DemoParse", "Event Saved!!!");
		return super.onContextItemSelected(item);
	}
}
