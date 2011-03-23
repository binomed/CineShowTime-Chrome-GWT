package com.binomed.cst.model;


public class LocalisationBean {

	private Float distance;
	private Long distanceTime;
	private Double latitude;
	private Double longitude;
	private String searchQuery;
	private String countryName;
	private String countryNameCode;
	private String cityName;
	private String postalCityNumber;

	public LocalisationBean() {
		super();
	}

	public Float getDistance() {
		return distance;
	}

	public void setDistance(Float distance) {
		this.distance = distance;
	}

	public Long getDistanceTime() {
		return distanceTime;
	}

	public void setDistanceTime(Long distanceTime) {
		this.distanceTime = distanceTime;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public String getSearchQuery() {
		return searchQuery;
	}

	public void setSearchQuery(String searchQuery) {
		this.searchQuery = searchQuery;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public String getCountryNameCode() {
		return countryNameCode;
	}

	public void setCountryNameCode(String countryNameCode) {
		this.countryNameCode = countryNameCode;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getPostalCityNumber() {
		return postalCityNumber;
	}

	public void setPostalCityNumber(String postalCityNumber) {
		this.postalCityNumber = postalCityNumber;
	}

	@Override
	public String toString() {
		return cityName + ", " + postalCityNumber + " " + countryName; //$NON-NLS-1$//$NON-NLS-2$
	}

}
