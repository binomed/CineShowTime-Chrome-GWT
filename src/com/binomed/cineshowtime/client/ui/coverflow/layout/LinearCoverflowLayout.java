package com.binomed.cineshowtime.client.ui.coverflow.layout;

import java.util.Map;

import com.binomed.cineshowtime.client.ui.coverflow.CoverElement;
import com.binomed.cineshowtime.client.ui.coverflow.GWTCoverflowCanvas;

public class LinearCoverflowLayout implements CoverflowLayout {

	private final int TOP_PADDING = 20;
	public static final int SPACE_BETWEEN_IMAGES = 20;

	@Override
	public void onInitCovers(final Map<String, CoverElement> covers) {
		int offsetX = 0;
		for (CoverElement cover : covers.values()) {
			// Initialize the cover
			cover.setLeftX(offsetX);
			cover.setTopY(TOP_PADDING);
			// Compute next cover offset X
			offsetX = offsetX + cover.getWidth() + SPACE_BETWEEN_IMAGES;
		}
	}

	@Override
	public void onUpdateCovers(String indexCoverCenter, final Map<String, CoverElement> covers, int totalTranslateX) {
		for (CoverElement cover : covers.values()) {
			cover.setLeftX(cover.getLeftX() + totalTranslateX);
		}
	}

	@Override
	public void onDrawCovers(GWTCoverflowCanvas coverflowCanvas, Map<String, CoverElement> covers) {
		for (CoverElement cover : covers.values()) {
			cover.draw(coverflowCanvas.getCanvas());
		}
	}

}