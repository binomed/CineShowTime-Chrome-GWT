package com.binomed.cst.model;

import java.util.List;
import java.util.Map;

public class NearResp {

	public NearResp() {
		super();
	}

	private String cityName;

	private boolean hasMoreResults;

	private boolean nearResp;

	private Map<String, MovieBean> mapMovies;

	private List<TheaterBean> theaterList;

	public Map<String, MovieBean> getMapMovies() {
		return mapMovies;
	}

	public void setMapMovies(Map<String, MovieBean> mapMovies) {
		this.mapMovies = mapMovies;
	}

	public List<TheaterBean> getTheaterList() {
		return theaterList;
	}

	public void setTheaterList(List<TheaterBean> theaterList) {
		this.theaterList = theaterList;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public boolean isHasMoreResults() {
		return hasMoreResults;
	}

	public void setHasMoreResults(boolean hasMoreResults) {
		this.hasMoreResults = hasMoreResults;
	}

	public boolean isNearResp() {
		return nearResp;
	}

	public void setNearResp(boolean nearResp) {
		this.nearResp = nearResp;
	}

}
