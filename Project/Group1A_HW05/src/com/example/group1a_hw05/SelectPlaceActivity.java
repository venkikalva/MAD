package com.example.group1a_hw05;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.os.Bundle;
import android.widget.ImageView;

public class SelectPlaceActivity extends Activity implements View.OnClickListener{
ImageView restaurent,pub,resort,busstop;
public final String SELECTED_PLACE = "selectedplace";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_select_place);
		restaurent = (ImageView) findViewById(R.id.imageView1);
		pub = (ImageView) findViewById(R.id.imageView2);
		resort = (ImageView) findViewById(R.id.imageView3);
		busstop = (ImageView) findViewById(R.id.imageView4);
		restaurent.setOnClickListener(this);
		pub.setOnClickListener(this);
		resort.setOnClickListener(this);
		busstop.setOnClickListener(this);
		
	}
	@Override
	public void onClick(View v) {
		Intent intent = new Intent(SelectPlaceActivity.this,PlaceDetailsActivity.class);
		switch (v.getId()) {
		case R.id.imageView1:
			intent.putExtra(SELECTED_PLACE, "restarent");
			startActivity(intent);
			break;
		case R.id.imageView2:
			intent.putExtra(SELECTED_PLACE, "restarent");
			startActivity(intent);
			break;
		case R.id.imageView3:
			intent.putExtra(SELECTED_PLACE, "restarent");
			startActivity(intent);
			break;
		case R.id.imageView4:
			intent.putExtra(SELECTED_PLACE, "restarent");
			startActivity(intent);
			break;
		default:
			
			break;
		}
		
	}
}

