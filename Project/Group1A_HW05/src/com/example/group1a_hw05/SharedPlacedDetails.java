package com.example.group1a_hw05;

import java.io.Serializable;
import java.util.List;

import com.mad.bean.PlaceDetails;

public class SharedPlacedDetails implements Serializable {
private List<PlaceDetails>placeList;
private String tripName,traveldate;


public String getTraveldate() {
	return traveldate;
}

public void setTraveldate(String traveldate) {
	this.traveldate = traveldate;
}

public String getTripName() {
	return tripName;
}

public void setTripName(String tripName) {
	this.tripName = tripName;
}

public List<PlaceDetails> getPlaceList() {
	return placeList;
}

public void setPlaceList(List<PlaceDetails> placeList) {
	this.placeList = placeList;
}
}
