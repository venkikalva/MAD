package com.example.group1a_hw05;

import java.util.ArrayList;

import android.content.Context;
import android.widget.ArrayAdapter;
/*
 * Team : Ashraf Cherukuru, Savitha Doure, Venkatesh Kalva
 * */
public class UserListAdapter extends ArrayAdapter<String>{
	
	Context context;
	ArrayList<String> userList;
	int mResource;
	
	 public UserListAdapter(Context context, int resource,ArrayList<String> objects) {
		super(context, resource, objects);
		this.context = context;
		this.userList = objects;
		this.mResource = resource;
		// TODO Auto-generated constructor stub
	}

}
