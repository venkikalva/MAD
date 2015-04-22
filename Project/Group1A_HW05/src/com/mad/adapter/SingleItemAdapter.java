package com.mad.adapter;

import java.util.ArrayList;
import java.util.List;

import com.example.group1a_hw05.R;
import com.example.group1a_hw05.R.drawable;
import com.example.group1a_hw05.R.id;
import com.example.group1a_hw05.R.layout;
import com.mad.bean.PlaceDetails;
import com.squareup.picasso.Picasso;

import android.content.Context;
import android.graphics.Color;
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
			holder.address = (TextView) convertView
					.findViewById(R.id.addres);
			holder.thumbnail = (ImageView) convertView
					.findViewById(R.id.imageView1);
			holder.openstatus = (TextView) convertView
					.findViewById(R.id.openstatus);
			convertView.setTag(holder);

		}

		holder = (ViewHolder) convertView.getTag();
		TextView title = holder.title;
		TextView address = holder.address;
		TextView openStatus = holder.openstatus;
		ImageView thumbnail = holder.thumbnail;
		title.setText(progItems.get(position).getPlaceName());
		title.setTextAppearance(context, android.R.style.TextAppearance_DeviceDefault_Medium);
		address.setText(progItems.get(position).getVicinity());
		address.setTextColor(Color.GRAY);
		if((progItems.get(position).getOpenstatus()!=null)){
		if((progItems.get(position).getOpenstatus().equalsIgnoreCase("true"))){
		openStatus.setText("Now Open");	
		openStatus.setTextColor(Color.BLUE);
		}else{
			openStatus.setText("Closed");
			openStatus.setTextColor(Color.RED);
		}
		
		}else{
			openStatus.setText("-NA-");
		}
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
		TextView address;
		TextView openstatus;
		ImageView thumbnail;
	}
}
