package com.binomed.cineshowtime.model;


public class ReviewBean {

	public ReviewBean() {
		super();
	}

	private String source;

	private String author;

	private String review;

	private Float rate;

	private String urlReview;

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getReview() {
		return review;
	}

	public void setReview(String review) {
		this.review = review;
	}

	public Float getRate() {
		return rate;
	}

	public void setRate(Float rate) {
		this.rate = rate;
	}

	public String getUrlReview() {
		return urlReview;
	}

	public void setUrlReview(String urlReview) {
		this.urlReview = urlReview;
	}

}
