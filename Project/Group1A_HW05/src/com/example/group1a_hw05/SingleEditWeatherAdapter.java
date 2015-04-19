package com.example.group1a_hw05;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


public class SingleEditWeatherAdapter extends ArrayAdapter<PlaceDetails> {
	
	

	Context context;
	ArrayList<PlaceDetails> tripItems;
	AlertDialog alertDialog;
	public SingleEditWeatherAdapter(Context context,
			 List<PlaceDetails> objects) {
		super(context, R.layout.single_edit_trip, R.id.triname, objects);
		this.context=context;
		this.tripItems=(ArrayList<PlaceDetails>) objects;
		// TODO Auto-generated constructor stub
	}




	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.single_edit_trip,
					parent, false);
			holder = new ViewHolder();
			holder.title = (TextView) convertView.findViewById(R.id.triname);
			holder.subtitle = (TextView) convertView.findViewById(R.id.textView2);
			holder.remove = (ImageView) convertView.findViewById(R.id.remove);
			convertView.setTag(holder);

		}

		holder = (ViewHolder) convertView.getTag();
		TextView title = holder.title;
		ImageView remove = holder.remove;
		remove.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				alert(position);
				if (alertDialog != null) {
					alertDialog.show();
				}
				
			}
		});
		//TextView subtitle = holder.subtitle;
		title.setText(tripItems.get(position).getPlaceName());
		
		return convertView;
	}

	static class ViewHolder {
		TextView title;
		TextView subtitle;
		ImageView remove;

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
						tripItems.remove(position);
						notifyDataSetChanged();
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
}
