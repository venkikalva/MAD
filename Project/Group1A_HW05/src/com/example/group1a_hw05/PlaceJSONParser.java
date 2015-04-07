package com.example.group1a_hw05;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class PlaceJSONParser {
	
	
	 public List<PlaceDetails> parse(JSONObject jObject){
		 
	        JSONArray jPlaces = null;
	        try {
	            /** Retrieves all the elements in the 'places' array */
	            jPlaces = jObject.getJSONArray("results");
	        } catch (JSONException e) {
	            e.printStackTrace();
	        }
	        /** Invoking getPlaces with the array of json object
	        * where each json object represent a place
	        */
	        return getPlaces(jPlaces);
	    }
	 
	    private List<PlaceDetails> getPlaces(JSONArray jPlaces){
	        int placesCount = jPlaces.length();
	        List<PlaceDetails> placesList = new ArrayList<PlaceDetails>();
	        PlaceDetails place = null;
	 
	        /** Taking each place, parses and adds to list object */
	        for(int i=0; i<placesCount;i++){
	            try {
	                /** Call getPlace with place JSON object to parse the place */
	                place = getPlace((JSONObject)jPlaces.get(i));
	                placesList.add(place);
	 
	            } catch (JSONException e) {
	                e.printStackTrace();
	            }
	        }
	 
	        return placesList;
	    }
	 
	    /** Parsing the Place JSON object */
	    private PlaceDetails getPlace(JSONObject jPlace){
	    	PlaceDetails details = new PlaceDetails();
	        String placeName = "-NA-";
	        String vicinity="-NA-";
	        String latitude="";
	        String longitude="";
	 
	        try {
	            // Extracting Place name, if available
	            if(!jPlace.isNull("name")){
	                placeName = jPlace.getString("name");
	                details.setPlaceName(placeName);
	            }
	 
	            // Extracting Place Vicinity, if available
	            if(!jPlace.isNull("vicinity")){
	                vicinity = jPlace.getString("vicinity");
	                details.setVicinity(vicinity);
	                
	            }
	 
	            latitude = jPlace.getJSONObject("geometry").getJSONObject("location").getString("lat");
	            details.setLat(Double.parseDouble(latitude));
	            longitude = jPlace.getJSONObject("geometry").getJSONObject("location").getString("lng");
	            details.setLngt(Double.parseDouble(longitude));
	        } catch (JSONException e) {
	            e.printStackTrace();
	        }
	        return details;
	    }

}
