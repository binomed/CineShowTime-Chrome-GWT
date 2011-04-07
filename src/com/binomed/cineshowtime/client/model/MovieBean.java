package com.binomed.cineshowtime.client.model;

import java.util.List;

public class MovieBean {

	private String id;

	private String cid;

	private String imdbId;

	private Integer year;

	private String movieName;

	private String englishMovieName;

	private Long movieTime;

	private String lang;

	private String style;

	private String description;

	private String trDescription;

	private String urlImg;

	private String urlImdb;

	private String urlWikipedia;

	private Double rate;

	private boolean imdbDesrciption;

	private String actorList;

	private String directorList;

	private List<ReviewBean> reviews;

	private List<YoutubeBean> youtubeVideos;

	private List<String> theaterList;

	public MovieBean() {
		super();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public String getImdbId() {
		return imdbId;
	}

	public void setImdbId(String imdbId) {
		this.imdbId = imdbId;
	}

	public String getMovieName() {
		return movieName;
	}

	public void setMovieName(String movieName) {
		this.movieName = movieName;
	}

	public String getEnglishMovieName() {
		return englishMovieName;
	}

	public void setEnglishMovieName(String englishMovieName) {
		this.englishMovieName = englishMovieName;
	}

	public Long getMovieTime() {
		return movieTime;
	}

	public void setMovieTime(Long movieTime) {
		this.movieTime = movieTime;
	}

	public String getLang() {
		return lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTrDescription() {
		return trDescription;
	}

	public void setTrDescription(String trDescription) {
		this.trDescription = trDescription;
	}

	public String getUrlImg() {
		return urlImg;
	}

	public void setUrlImg(String urlImg) {
		this.urlImg = urlImg;
	}

	public String getUrlImdb() {
		return urlImdb;
	}

	public void setUrlImdb(String urlImdb) {
		this.urlImdb = urlImdb;
	}

	public Double getRate() {
		return rate;
	}

	public void setRate(Double rate) {
		this.rate = rate;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public boolean isImdbDesrciption() {
		return imdbDesrciption;
	}

	public void setImdbDesrciption(boolean imdbDesrciption) {
		this.imdbDesrciption = imdbDesrciption;
	}

	public String getActorList() {
		return actorList;
	}

	public void setActorList(String actorList) {
		this.actorList = actorList;
	}

	public String getDirectorList() {
		return directorList;
	}

	public void setDirectorList(String directorList) {
		this.directorList = directorList;
	}

	public String getUrlWikipedia() {
		return urlWikipedia;
	}

	public void setUrlWikipedia(String urlWikipedia) {
		this.urlWikipedia = urlWikipedia;
	}

	public List<ReviewBean> getReviews() {
		return reviews;
	}

	public void setReviews(List<ReviewBean> reviews) {
		this.reviews = reviews;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public List<YoutubeBean> getYoutubeVideos() {
		return youtubeVideos;
	}

	public void setYoutubeVideos(List<YoutubeBean> youtubeVideos) {
		this.youtubeVideos = youtubeVideos;
	}

	public List<String> getTheaterList() {
		return theaterList;
	}

	public void setTheaterList(List<String> theaterList) {
		this.theaterList = theaterList;
	}

}
