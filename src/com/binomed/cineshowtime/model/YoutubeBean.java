package com.binomed.cineshowtime.model;

import java.io.InputStream;

public class YoutubeBean {

	private String urlVideo;

	private String urlImg;

	private String videoName;

	private InputStream imgStream;

	public YoutubeBean() {
		super();
	}

	public String getUrlVideo() {
		return urlVideo;
	}

	public void setUrlVideo(String urlVideo) {
		this.urlVideo = urlVideo;
	}

	public String getUrlImg() {
		return urlImg;
	}

	public void setUrlImg(String urlImg) {
		this.urlImg = urlImg;
	}

	public String getVideoName() {
		return videoName;
	}

	public void setVideoName(String videoName) {
		this.videoName = videoName;
	}

	public InputStream getImgStream() {
		return imgStream;
	}

	public void setImgStream(InputStream imgStream) {
		this.imgStream = imgStream;
	}

}
