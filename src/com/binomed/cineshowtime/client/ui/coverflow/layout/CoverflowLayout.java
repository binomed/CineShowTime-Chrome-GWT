package com.binomed.cineshowtime.client.ui.coverflow.layout;

import java.util.Map;

import com.binomed.cineshowtime.client.ui.coverflow.CoverElement;
import com.binomed.cineshowtime.client.ui.coverflow.GWTCoverflowCanvas;

public interface CoverflowLayout {

	void onInitCovers(Map<String, CoverElement> covers);

	void onUpdateCovers(String coverId, Map<String, CoverElement> covers, int totalTranslateX);

	void onDrawCovers(GWTCoverflowCanvas coverflowCanvas, Map<String, CoverElement> covers);
}
