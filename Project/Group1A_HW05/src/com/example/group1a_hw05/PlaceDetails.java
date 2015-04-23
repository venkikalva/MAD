package com.example.group1a_hw05;

import java.io.Serializable;

public class PlaceDetails implements Serializable {
	
	private double lat;
	private double lngt;
	private String placeName,vicinity,url,imageUrl;
	
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	@Override
	public String toString() {
		return "PlaceDetails [lat=" + lat + ", lngt=" + lngt + ", placeName="
				+ placeName + ", vicinity=" + vicinity + "]";
	}
	public double getLat() {
		return lat;
	}
	public void setLat(double lat) {
		this.lat = lat;
	}
	public double getLngt() {
		return lngt;
	}
	public void setLngt(double lngt) {
		this.lngt = lngt;
	}
	public String getPlaceName() {
		return placeName;
	}
	public void setPlaceName(String placeName) {
		this.placeName = placeName;
	}
	public String getVicinity() {
		return vicinity;
	}
	public void setVicinity(String vicinity) {
		this.vicinity = vicinity;
	}

}
