package com.binomed.cineshowtime.model;

import java.util.List;
import java.util.Map;

public class TheaterBean {

	private String id;

	private String theaterName;

	private String phoneNumber;

	private LocalisationBean place;

	private Map<String, List<ProjectionBean>> movieMap;

	public TheaterBean() {
		super();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTheaterName() {
		return theaterName;
	}

	public void setTheaterName(String theaterName) {
		this.theaterName = theaterName;
	}

	public Map<String, List<ProjectionBean>> getMovieMap() {
		return movieMap;
	}

	public void setMovieMap(Map<String, List<ProjectionBean>> movieMap) {
		this.movieMap = movieMap;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public LocalisationBean getPlace() {
		return place;
	}

	public void setPlace(LocalisationBean place) {
		this.place = place;
	}

}
