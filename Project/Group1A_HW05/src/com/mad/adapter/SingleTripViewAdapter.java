package com.mad.adapter;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import com.example.group1a_hw05.R;
import com.example.group1a_hw05.SharedPlacedDetails;
import com.example.group1a_hw05.R.id;
import com.example.group1a_hw05.R.layout;
import com.google.gson.Gson;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParsePush;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class SingleTripViewAdapter extends ArrayAdapter<SharedPlacedDetails> {
	Context context;
	ArrayList<SharedPlacedDetails> viewtrip;
	AlertDialog alertDialog;
	int PICK_CONTACT=1;

	public SingleTripViewAdapter(Context context,
			List<SharedPlacedDetails> objects) {
		super(context, R.layout.single_view_trip, R.id.triname, objects);
		this.context = context;
		this.viewtrip = (ArrayList<SharedPlacedDetails>) objects;
		// TODO Auto-generated constructor stub
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.single_view_trip, parent,
					false);
			holder = new ViewHolder();
			holder.title = (TextView) convertView.findViewById(R.id.triname);
			holder.date = (TextView) convertView.findViewById(R.id.distance);
			holder.share = (ImageView) convertView
					.findViewById(R.id.imageView1);
			holder.delete = (ImageView) convertView
					.findViewById(R.id.imageView2);

			convertView.setTag(holder);

		}

		holder = (ViewHolder) convertView.getTag();
		TextView title = holder.title;
		TextView dateval = holder.date;
		title.setText(viewtrip.get(position).getTripName());
		dateval.setText(viewtrip.get(position).getTraveldate());
		ImageView share = holder.share;
		ImageView delete = holder.delete;
		delete.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				alert(position);
				if (alertDialog != null) {
					alertDialog.show();
				}

			}
		});
		
		share.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
               // startActivityForResult(intent, PICK_CONTACT);
                
                ParseQuery<ParseInstallation> query = ParseInstallation.getQuery();
                query.whereEqualTo("userId",  "0VZF1l5qyA");
 /*               ParseQuery<ParseObject> query1 = ParseQuery.getQuery("SavTripList");
        		query1.whereEqualTo("user", ParseUser.getCurrentUser());*/

                // Notification for Android users
                ParsePush androidPush = new ParsePush();
                androidPush.setMessage("Your suitcase has been filled with tiny robots!");
                androidPush.setQuery(query);
                androidPush.sendInBackground();
				
			}
		});
		return convertView;
	}

	static class ViewHolder {
		TextView title;
		TextView date;
		ImageView share;
		ImageView delete;

	}

	public void alert(final int position) {
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
				context);
		// set dialog message
		alertDialogBuilder
				.setCancelable(false)
				.setTitle("Want to delete the Trip?")
				.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						deleteTripinParse(position);
					}
				})
				.setNegativeButton("No",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								dialog.cancel();
							}
						});
		// create alert dialog
		alertDialog = alertDialogBuilder.create();
		// show it

	}
	
	private void deleteTripinParse(final int position){
		ParseQuery<ParseObject> query = ParseQuery.getQuery("SavTripList");
		query.whereEqualTo("user", ParseUser.getCurrentUser());
		query.findInBackground(new FindCallback<ParseObject>() {
			@Override
			public void done(List<ParseObject> objects, ParseException e) {
				if(objects.size()>0){
					SharedPlacedDetails savedtrip;
					Gson gson = new Gson();
					for (ParseObject p : objects) {
						String savetrip= p.getString("savetrip");
						Log.d("json", savetrip);
						savedtrip = gson.fromJson(savetrip, SharedPlacedDetails.class);
						if(savedtrip.getTripName().equalsIgnoreCase(viewtrip.get(position).getTripName()))
						{
						p.deleteInBackground();
						}
					}
					viewtrip.remove(position);
					notifyDataSetChanged();
					Toast.makeText(context," Deleted the Trip Succesfully", Toast.LENGTH_LONG).show();
				}else{
					Toast.makeText(context,"No SharedList to clear", Toast.LENGTH_LONG).show();
				}
				
			}
			
		});
		
	}

}
