package com.mad.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.mad.bean.DailyTemp;
import com.mad.bean.PlaceDetails;
import com.mad.bean.WeatherDetail;

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
	 
	 public WeatherDetail parseWeather(JSONObject jObject){
		 
	        JSONArray jPlaces = null;
	        WeatherDetail weatherDetail = new WeatherDetail();
	        List<DailyTemp> dailyTempList = new ArrayList<DailyTemp>();
	        DailyTemp dailyTemp = null;
	        try {
	            /** Retrieves all the elements in the 'places' array */
	          JSONObject  cityObj = jObject.getJSONObject("city");
	          weatherDetail.setCityName(cityObj.getString("name"));
	          weatherDetail.setCountry(cityObj.getString("country"));
	          jPlaces= jObject.getJSONArray("list");
	          for(int i=0;i<jPlaces.length();i++){
	        	  dailyTemp = getPlaceTemp((JSONObject)jPlaces.get(i));
	        	  dailyTempList.add(dailyTemp);  
	          }
	          
	          Log.d("dailytemp", dailyTempList.toString());
	          weatherDetail.setDailyTemp(dailyTempList);
	          
	          
	        } catch (JSONException e) {
	            e.printStackTrace();
	        }
	        /** Invoking getPlaces with the array of json object
	        * where each json object represent a place
	        */
	        return weatherDetail;
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
	        String openStatus="";
	 
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
	            if(!jPlace.isNull("icon")){
	                String imageUrl = jPlace.getString("icon");
	                details.setImageUrl(imageUrl);
	                
	            }
	            
	            latitude = jPlace.getJSONObject("geometry").getJSONObject("location").getString("lat");
	            details.setLat(Double.parseDouble(latitude));
	            longitude = jPlace.getJSONObject("geometry").getJSONObject("location").getString("lng");
	            details.setLngt(Double.parseDouble(longitude));
	            openStatus =jPlace.getJSONObject("opening_hours").getString("open_now");
	            details.setOpenstatus(openStatus);
	        } catch (JSONException e) {
	            e.printStackTrace();
	        }
	        return details;
	    }
	    private DailyTemp getPlaceTemp(JSONObject jPlace){
	    	DailyTemp dailyTemp = new DailyTemp();
	    	
	    	try {
				JSONObject temp = jPlace.getJSONObject("temp");
				if(!temp.isNull("day")){
	                dailyTemp.setDay(temp.getString("day"));
	            }
				if(!temp.isNull("min")){
	                dailyTemp.setMin(temp.getString("min"));
	            }if(!temp.isNull("max")){
	                dailyTemp.setMax(temp.getString("max"));
	            }if(!jPlace.isNull("humidity")){
	                dailyTemp.setHumidity(jPlace.getString("humidity"));
	            }if(!jPlace.isNull("speed")){
	                dailyTemp.setSpeed(jPlace.getString("speed"));
	            }
	            JSONArray array= jPlace.getJSONArray("weather");
	            for(int i=0;i<array.length();i++){
	            	//dailyTemp.setMain(array.getString(index));
	            	
	            }
				
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return dailyTemp;
	    	
	    	
	    }
	    

}
