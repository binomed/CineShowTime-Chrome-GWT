package com.binomed.cineshowtime.client.ui.coverflow;

public interface CoverData {

	String getId();

	String getLabel();

	String getCoverUrl();

	void setCoverURL(String coverURL);

	boolean isVideo();

	String getVideoUrl();

}
