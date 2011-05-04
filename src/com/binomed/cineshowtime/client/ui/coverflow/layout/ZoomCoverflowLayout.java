package com.binomed.cineshowtime.client.ui.coverflow.layout;

import java.util.Map;

import com.binomed.cineshowtime.client.ui.coverflow.CoverElement;
import com.binomed.cineshowtime.client.ui.coverflow.GWTCoverflowCanvas;

public class ZoomCoverflowLayout implements CoverflowLayout {

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
		double indexCenter = getIndexOfCoverId(indexCoverCenter, covers);
		double index = 0;
		for (CoverElement cover : covers.values()) {
			cover.setLeftX(cover.getLeftX() + totalTranslateX);
			if (indexCenter - 3 >= 0 && indexCenter - 3 == index) {
				cover.setScale(0.6d);
			} else if (indexCenter - 2 >= 0 && indexCenter - 2 == index) {
				cover.setScale(0.7d);
			} else if (indexCenter - 1 >= 0 && indexCenter - 1 == index) {
				cover.setScale(0.8d);
			} else if (indexCenter == index) {
				cover.setScale(1.0d);
				showProjectionList(cover);
			} else if (indexCenter + 1 < covers.size() && indexCenter + 1 == index) {
				cover.setScale(0.8d);
			} else if (indexCenter + 2 < covers.size() && indexCenter + 2 == index) {
				cover.setScale(0.7d);
			} else if (indexCenter + 3 < covers.size() && indexCenter + 3 == index) {
				cover.setScale(0.6d);
			} else {
				cover.setScale(0.5d);
			}
			index++;
		}
	}

	private void showProjectionList(CoverElement cover) {
		// TODO Display center cover data (projection list, fleche)
	}

	@Override
	public void onDrawCovers(GWTCoverflowCanvas coverflowCanvas, Map<String, CoverElement> covers) {
		for (CoverElement cover : covers.values()) {
			cover.draw(coverflowCanvas.getCanvas());
		}
	}

	private int getIndexOfCoverId(String coverId, Map<String, CoverElement> covers) {
		int index = 0;
		for (String curCurrentId : covers.keySet()) {
			if (curCurrentId.equalsIgnoreCase(coverId)) {
				return index;
			}
			index++;
		}
		return 0;
	}
}
