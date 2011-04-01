package com.binomed.cst.client.widget;

/**
 * Represents mouse events on the coverflow
 */
public abstract class CoverflowMouseMoveEvent {

	// Move the coverflow to the left
	public static final int DIRECTION_LEFT = -1;
	// Move the coverflow to the right
	public static final int DIRECTION_RIGHT = 1;

	private int firstX = 0;
	private int lastX = 0;

	public abstract void onMove(int direction, int distance);

	public void setMouseDownCoordonates(int x) {
		this.firstX = x;
	}

	public void setMouseUpCoordonates(int x) {
		this.lastX = x;
	}

	public void onMove() {
		int distance = lastX - firstX;
		if (distance > 0) {
			onMove(DIRECTION_RIGHT, distance);
		} else if (distance < 0) {
			onMove(DIRECTION_LEFT, distance);
		}
		firstX = 0;
		lastX = 0;
	}

}
