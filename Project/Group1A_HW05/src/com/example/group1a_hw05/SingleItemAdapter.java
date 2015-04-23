package com.example.group1a_hw05;

import java.util.ArrayList;
import java.util.List;

import com.squareup.picasso.Picasso;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
/*
 * Team : Ashraf Cherukuru, Savitha Doure, Venkatesh Kalva
 * */
public class SingleItemAdapter extends ArrayAdapter<PlaceDetails> {
	

	Context context;
	ArrayList<PlaceDetails> progItems;
	
	public SingleItemAdapter(Context context,
			 List<PlaceDetails> objects) {
		super(context, R.layout.activity_single_item, R.id.cityname1, objects);
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
			convertView = inflater.inflate(R.layout.activity_single_item,
					parent, false);
			holder = new ViewHolder();
			holder.title = (TextView) convertView.findViewById(R.id.cityname1);
			holder.devName = (TextView) convertView
					.findViewById(R.id.humidity);
			holder.thumbnail = (ImageView) convertView
					.findViewById(R.id.imageView1);
			holder.price = (TextView) convertView
					.findViewById(R.id.tempdeg);
			holder.date = (TextView) convertView
					.findViewById(R.id.wind);
			convertView.setTag(holder);

		}

		holder = (ViewHolder) convertView.getTag();
		TextView title = holder.title;
		TextView devname = holder.devName;
		TextView price = holder.price;
		ImageView thumbnail = holder.thumbnail;
		TextView dateval = holder.date;
		title.setText(progItems.get(position).getPlaceName());
		/*devname.setText(progItems.get(position).getDevname());
		price.setText(progItems.get(position).getPrice()+"");*/
		String imgLink = progItems.get(position).getImageUrl();
		if (imgLink == null || "".equalsIgnoreCase(imgLink)) {
			thumbnail.setImageResource(R.drawable.photo_not_found);
		} else
			Picasso.with(context).load(imgLink).into(thumbnail);
		//dateval.setText(progItems.get(position).getDate());
		return convertView;
	}

	static class ViewHolder {
		TextView title;
		TextView devName;
		TextView price;
		ImageView thumbnail;
		TextView date;

	}
}
