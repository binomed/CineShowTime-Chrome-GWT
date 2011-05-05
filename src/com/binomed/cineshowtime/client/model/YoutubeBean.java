package com.binomed.cineshowtime.client.model;

import com.binomed.cineshowtime.client.ui.coverflow.CoverData;

public class YoutubeBean implements CoverData {

	private String urlVideo;

	private String urlImg;

	private String videoName;

	private String videoId;

	public YoutubeBean() {
		super();
	}

	public String getVideoId() {
		return videoId;
	}

	public void setVideoId(String videoId) {
		this.videoId = videoId;
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

	@Override
	public String getId() {
		return videoName;
	}

	@Override
	public String getLabel() {
		return videoName;
	}

	@Override
	public String getCoverUrl() {
		return urlImg;
	}

	@Override
	public void setCoverURL(String coverURL) {
		setUrlImg(coverURL);

	}

	@Override
	public boolean isVideo() {
		return true;
	}

	@Override
	public String getVideoUrl() {
		return urlVideo;
	}

	@Override
	public int getState() {
		return -1;
	}

}
