package com.binomed.cineshowtime.client.ui.coverflow;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.user.client.Timer;

public abstract class Animation {

	private final Canvas canvas;
	private final int timeInterval;

	private final Timer timer;

	public Animation(final Canvas canvas, final int fps) {
		this.canvas = canvas;
		this.timeInterval = 1000 / fps;
		timer = new Timer() {
			@Override
			public void run() {
				loop();
			}
		};
	}

	public abstract void updateStage();

	public abstract void drawStage();

	public void start() {
		timer.scheduleRepeating(timeInterval);
	}

	public void stop() {
		timer.cancel();
	}

	public int getTimeInterval() {
		return timeInterval;
	}

	public void loop() {
		clearCanvas();
		updateStage();
		drawStage();
	}

	public void clearCanvas() {
		canvas.getContext2d().clearRect(0, 0, canvas.getCoordinateSpaceWidth(), canvas.getCoordinateSpaceHeight());
	}

}
