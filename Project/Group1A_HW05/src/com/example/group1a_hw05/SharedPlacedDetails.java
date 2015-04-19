package com.example.group1a_hw05;

import java.util.List;

public class SharedPlacedDetails {
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
