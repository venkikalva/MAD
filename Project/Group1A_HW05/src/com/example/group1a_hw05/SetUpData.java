package com.example.group1a_hw05;

import java.util.ArrayList;
import java.util.List;

import android.widget.ArrayAdapter;
/*
 * Team : Ashraf Cherukuru, Savitha Doure, Venkatesh Kalva
 * */
public interface SetUpData {
	
	public void setData(ArrayAdapter<Item> adapter,ArrayList<Item> itemList);

	public void setDetails(SingleItemAdapter adapter,
			List<PlaceDetails> itemList);
	

}
