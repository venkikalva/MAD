package com.example.group1a_hw05;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.os.Bundle;
import android.widget.ImageView;

public class SelectPlaceActivity extends Activity implements View.OnClickListener{
ImageView restaurent;
public final String SELECTED_PLACE = "selectedplace";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_select_place);
		restaurent = (ImageView) findViewById(R.id.restarent);
		restaurent.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.restarent:
			Intent intent = new Intent(SelectPlaceActivity.this,PlaceDetailsActivity.class);
			intent.putExtra(SELECTED_PLACE, "restarent");
			startActivity(intent);
			break;

		default:
			break;
		}
		
	}
}

