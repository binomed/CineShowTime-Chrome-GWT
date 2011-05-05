package com.binomed.cineshowtime.client.ui.coverflow.event;

import com.binomed.cineshowtime.client.ui.coverflow.Coverflow;

/**
 * Represents mouse events on the coverflow
 */
public abstract class CoverflowMouseEvent {

	// Click or move threshold (in px)
	private static final int CLICK_OR_MOVE_THRESHOLD = 5;

	private int firstX = 0;
	private int lastX = 0;

	public abstract void onCoverFling(int direction, int distance);

	public abstract void onClickCover(int x);

	public abstract void onCoverMove(int x);

	public abstract void onCoverOver(int x);

	public void setMouseDownCoordonates(int x) {
		this.firstX = x;
	}

	public void setMouseUpCoordonates(int x) {
		this.lastX = x;
	}

	public void onCoverflowMouseEvent() {
		int distance = lastX - firstX;
		if (Math.abs(distance) <= CLICK_OR_MOVE_THRESHOLD) {
			onClickCover(firstX);
		} else {
			if (distance > 0) {
				onCoverFling(Coverflow.MOVE_RIGHT, distance);
			} else if (distance < 0) {
				onCoverFling(Coverflow.MOVE_LEFT, distance);
			}
		}
		firstX = 0;
		lastX = 0;
	}
}
