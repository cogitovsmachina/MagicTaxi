package org.androidtitlan.locationpollertest;

public class TaxiDriver {

	private String lat;
	private String lon;
	
	
	
	public TaxiDriver(String lat, String lon) {
		super();
		this.lat = lat;
		this.lon = lon;
	}
	public String getLat() {
		return lat;
	}
	public void setLat(String lat) {
		this.lat = lat;
	}
	public String getLon() {
		return lon;
	}
	public void setLon(String lon) {
		this.lon = lon;
	}
	

	
}
