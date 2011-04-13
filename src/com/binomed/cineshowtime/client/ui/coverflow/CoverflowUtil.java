package com.binomed.cineshowtime.client.ui.coverflow;

import java.util.Map;
import java.util.Map.Entry;

public class CoverflowUtil {

	private CoverflowUtil() {
	}

	public static String getIdOfCoverFromX(Map<String, CoverElement> covers, int x) {
		for (Entry<String, CoverElement> coverEntry : covers.entrySet()) {
			if (x >= coverEntry.getValue().getLeftX() && x <= coverEntry.getValue().getRightX()) {
				return coverEntry.getKey();
			}
		}
		return null;
	}

}
