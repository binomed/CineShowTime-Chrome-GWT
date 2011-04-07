package com.binomed.cineshowtime.client.model;


public class ProjectionBean {

	public ProjectionBean() {
		super();
	}

	private Long showtime;

	private String lang;

	private String reservationLink;

	public Long getShowtime() {
		return showtime;
	}

	public void setShowtime(Long showtime) {
		this.showtime = showtime;
	}

	public String getLang() {
		return lang;
	}

	public void setSubtitle(String subtitle) {
		this.lang = subtitle;
	}

	public String getReservationLink() {
		return reservationLink;
	}

	public void setReservationLink(String reservationLink) {
		this.reservationLink = reservationLink;
	}

}
