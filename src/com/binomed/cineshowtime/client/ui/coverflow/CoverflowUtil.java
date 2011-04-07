package com.binomed.cineshowtime.client.ui.coverflow;

public class CoverflowUtil {

	private CoverflowUtil() {
	}

	public static int getIndexOfCoverFromX(CoverElement[] covers, int x) {
		for (int i = 0; i < covers.length; i++) {
			if (x >= covers[i].getLeftX() && x <= covers[i].getRightX()) {
				return i;
			}
		}
		return -1;
	}

}
