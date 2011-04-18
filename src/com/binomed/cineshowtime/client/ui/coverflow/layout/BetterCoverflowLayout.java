package com.binomed.cineshowtime.client.ui.coverflow.layout;

import java.util.Map;

import com.binomed.cineshowtime.client.ui.coverflow.CoverElement;
import com.binomed.cineshowtime.client.ui.coverflow.GWTCoverflowCanvas;

public class BetterCoverflowLayout implements CoverflowLayout {

	private final int TOP_PADDING = 70;
	public static final int SPACE_BETWEEN_IMAGES = 20;

	public int indexCoverCenter;

	@Override
	public void onInitCovers(final Map<String, CoverElement> covers) {
		this.indexCoverCenter = covers.size() / 2;
		final int w = 800;
		final int cx = w / 2;
		final int DX = 120;
		final int DY = 30;
		int position = -(covers.size() / 2);
		for (CoverElement cover : covers.values()) {
			// Initialize the cover
			cover.setCenterX(cx + DX * position);
			cover.setTopY(TOP_PADDING - (Math.abs(position) * DY));
			position++;
		}
	}

	// public void onUpdateCovers(String indexCoverCenter, final Map<String, CoverElement> covers, int totalTranslateX) {
	// int position = -(covers.size() / 2);
	// for (CoverElement cover : covers.values()) {
	// cover.setLeftX(cover.getLeftX() + totalTranslateX);
	// position++;
	// }
	// }

	@Override
	public void onUpdateCovers(String coverId, final Map<String, CoverElement> covers, int totalTranslateX) {
		this.indexCoverCenter = getIndexOfCoverId(coverId, covers);
		final int DY = 30;
		int position = -(covers.size() / 2);
		if (indexCoverCenter <= covers.size()) {
			position = -indexCoverCenter;
		} else {
			position = indexCoverCenter;
		}
		for (CoverElement cover : covers.values()) {
			// TODO ordre de dessin
			cover.setPosition(Math.abs(position));
			// Update X translation
			cover.setLeftX(cover.getLeftX() + totalTranslateX);
			// Update Y translation
			if (totalTranslateX <= DY) {
				cover.setTopY(TOP_PADDING - (Math.abs(position) * DY));
			}
			// TODO Transformation
			cover.setTransform(true);
			// Go to next position
			position++;
		}
	}

	@Override
	public void onDrawCovers(GWTCoverflowCanvas coverflowCanvas, Map<String, CoverElement> covers) {
		// TODO dessiner dans l'ordre
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
