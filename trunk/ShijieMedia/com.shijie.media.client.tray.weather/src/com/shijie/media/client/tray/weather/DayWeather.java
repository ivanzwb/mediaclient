package com.shijie.media.client.tray.weather;

import java.net.URL;

public class DayWeather {
	
	private String city;
	private String dayOfWeek; 
	private String tempLow ="7" ;
	private String tempHigh ="26" ;
	private URL statusIcon;
	private String statusText;
	private String humidity;
	private String wind;
	private String tempCurrent;
	
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getDayOfWeek() {
		return dayOfWeek;
	}
	public void setDayOfWeek(String dayOfWeek) {
		this.dayOfWeek = dayOfWeek;
	}
	public String getTempLow() {
		return tempLow;
	}
	public void setTempLow(String tempLow) {
		this.tempLow = tempLow;
	}
	public String getTempHigh() {
		return tempHigh;
	}
	public void setTempHigh(String tempHigh) {
		this.tempHigh = tempHigh;
	}
	public URL getStatusIcon() {
		return statusIcon;
	}
	public void setStatusIcon(URL statusIcon) {
		this.statusIcon = statusIcon;
	}
	public String getStatusText() {
		return statusText;
	}
	public void setStatusText(String statusText) {
		this.statusText = statusText;
	}
	public String getHumidity() {
		return humidity;
	}
	public void setHumidity(String humidity) {
		this.humidity = humidity;
	}
	public String getWind() {
		return wind;
	}
	public void setWind(String wind) {
		this.wind = wind;
	}
	public String getTempCurrent() {
		return tempCurrent;
	}
	public void setTempCurrent(String tempCurrent) {
		this.tempCurrent = tempCurrent;
	}
	
}
