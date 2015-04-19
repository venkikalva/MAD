package com.example.group1a_hw05;

import java.util.Calendar;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;
import android.view.View;

public class TripNameActivity extends Activity implements View.OnClickListener  {
Button cancel;
Button continu;
EditText tripname,traveldate;
SharedPreferences preference;
public final  String TRIP_NAME = "tripName"; 
static final int DATE_DIALOG_ID = 0;
//variables to save user selected date and time
	public int year, month, day;

	// Picker Dialog first appears
	private int mYear, mMonth, mDay;
	// constructor
		public TripNameActivity() {
			// Assign current Date and Time Values to Variables
			final Calendar c = Calendar.getInstance();
			mYear = c.get(Calendar.YEAR);
			mMonth = c.get(Calendar.MONTH);
			mDay = c.get(Calendar.DAY_OF_MONTH);
		}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_trip_name);
		cancel = (Button) findViewById(R.id.add);
		continu = (Button) findViewById(R.id.button2);
		tripname = (EditText) findViewById(R.id.editText1);
		traveldate = (EditText) findViewById(R.id.traveldate);
		traveldate.setKeyListener(null);
		cancel.setOnClickListener(this);
		continu.setOnClickListener(this);
		// Set ClickListener on etDate
		traveldate.setOnClickListener(new View.OnClickListener() {

			@SuppressWarnings("deprecation")
			public void onClick(View v) {
				// Show the DatePickerDialog
				showDialog(DATE_DIALOG_ID);
			}
		});
	}
	
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.add:
			finish();
			break;
		case R.id.button2:

			if (tripname.equals("") || traveldate.equals("")) {
				Toast.makeText(TripNameActivity.this,
						"Please enter missing data", Toast.LENGTH_SHORT)
						.show();
			}else{
			String tripName = tripname.getText().toString(); 
			String travelDate = traveldate.getText().toString();
			preference = getApplicationContext().getSharedPreferences(
					EditTripActivity.MyPREFERENCES, Context.MODE_PRIVATE);
			Editor editor = preference.edit();
			editor.putString("tripname", tripName);
			editor.putString("traveldate", travelDate);
			editor.commit();
			Intent intent = new Intent(TripNameActivity.this,SelectPlaceActivity.class);
			startActivity(intent);
			}
			break;

		default:
			break;
		}
		
	}
	// Register DatePickerDialog listener
		private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {
			// the callback received when the user "sets" the Date in the
			// DatePickerDialog
			public void onDateSet(DatePicker view, int yearSelected,
					int monthOfYear, int dayOfMonth) {
				year = yearSelected;
				month = monthOfYear + 1;
				day = dayOfMonth;
				// Set the Selected Date in Select date Button
				traveldate.setText(month + "/" + day + "/" + year);
			}
		};
		
		
		@Override
		protected Dialog onCreateDialog(int id) {
			switch (id) {
			case DATE_DIALOG_ID:
				// create a new DatePickerDialog with values you want to show
				return new DatePickerDialog(this, mDateSetListener, mYear, mMonth,
						mDay);
			}
			return null;
		}
}
