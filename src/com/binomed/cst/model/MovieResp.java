package com.binomed.cst.model;

import java.util.List;

public class MovieResp {

	private String cityName;

	private boolean hasMoreResults;

	private MovieBean movie;

	private List<TheaterBean> theaterList;

	public MovieBean getMovie() {
		return movie;
	}

	public void setMovie(MovieBean movie) {
		this.movie = movie;
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

	public MovieResp() {
		super();
	}

}
