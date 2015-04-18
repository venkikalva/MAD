package com.example.group1a_hw05;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


public class SingleEditWeatherAdapter extends ArrayAdapter<PlaceDetails> {
	
	

	Context context;
	ArrayList<PlaceDetails> progItems;
	
	public SingleEditWeatherAdapter(Context context,
			 List<PlaceDetails> objects) {
		super(context, R.layout.single_edit_trip, R.id.triname, objects);
		this.context=context;
		this.progItems=(ArrayList<PlaceDetails>) objects;
		// TODO Auto-generated constructor stub
	}




	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.single_edit_trip,
					parent, false);
			holder = new ViewHolder();
			holder.title = (TextView) convertView.findViewById(R.id.triname);
			holder.subtitle = (TextView) convertView.findViewById(R.id.textView2);
			convertView.setTag(holder);

		}

		holder = (ViewHolder) convertView.getTag();
		TextView title = holder.title;
		//TextView subtitle = holder.subtitle;
		title.setText(progItems.get(position).getPlaceName());
		return convertView;
	}

	static class ViewHolder {
		TextView title;
		TextView subtitle;
		

	}

}
