package com.binomed.cineshowtime.client.model;

import java.util.Date;

public class RequestBean {

	private String cityName;
	private Date time;
	private String theaterId;
	private String movieName;
	private double latitude, longitude;
	private boolean nullResult;
	private boolean nearResp;
	private boolean favSearch;

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public String getTheaterId() {
		return theaterId;
	}

	public void setTheaterId(String theaterId) {
		this.theaterId = theaterId;
	}

	public String getMovieName() {
		return movieName;
	}

	public void setMovieName(String movieName) {
		this.movieName = movieName;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public boolean isNullResult() {
		return nullResult;
	}

	public void setNullResult(boolean nullResult) {
		this.nullResult = nullResult;
	}

	public boolean isNearResp() {
		return nearResp;
	}

	public void setNearResp(boolean nearResp) {
		this.nearResp = nearResp;
	}

	public boolean isFavSearch() {
		return favSearch;
	}

	public void setFavSearch(boolean favSearch) {
		this.favSearch = favSearch;
	}

}
