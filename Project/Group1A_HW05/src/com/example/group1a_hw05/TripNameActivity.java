package com.example.group1a_hw05;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.view.View;

public class TripNameActivity extends Activity implements View.OnClickListener  {
Button cancel;
Button continu;
EditText tripname;
public final  String TRIP_NAME = "tripName"; 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_trip_name);
		cancel = (Button) findViewById(R.id.button1);
		continu = (Button) findViewById(R.id.button2);
		tripname = (EditText) findViewById(R.id.editText1);
		cancel.setOnClickListener(this);
		continu.setOnClickListener(this);
		
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.button1:
			finish();
			break;
		case R.id.button2:
			String tripName = tripname.getText().toString(); 
			Intent intent = new Intent(TripNameActivity.this,SelectPlaceActivity.class);
			intent.putExtra(TRIP_NAME, tripName);
			startActivity(intent);
			
			break;

		default:
			break;
		}
		
	}
}
