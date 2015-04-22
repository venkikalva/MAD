package com.mad.bean;

import java.io.Serializable;
import java.util.List;

public class DailyTemp implements Serializable {
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
private String max,min,description,humidity,main,speed,day,precipitation;

public String getPrecipitation() {
	return precipitation;
}

public void setPrecipitation(String precipitation) {
	this.precipitation = precipitation;
}

public String getDay() {
	return day;
}

public void setDay(String day) {
	this.day = day;
}

public String getMax() {
	return max;
}

public void setMax(String max) {
	this.max = max;
}

public String getMin() {
	return min;
}

public void setMin(String min) {
	this.min = min;
}

public String getDescription() {
	return description;
}

public void setDescription(String description) {
	this.description = description;
}

public String getHumidity() {
	return humidity;
}

public void setHumidity(String humidity) {
	this.humidity = humidity;
}

public String getMain() {
	return main;
}

public void setMain(String main) {
	this.main = main;
}

public String getSpeed() {
	return speed;
}

public void setSpeed(String speed) {
	this.speed = speed;
}
}
