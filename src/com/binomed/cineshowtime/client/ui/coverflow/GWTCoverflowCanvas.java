package com.binomed.cineshowtime.client.ui.coverflow;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.CanvasGradient;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;

/**
 * Canvas used for the coverflow
 */
public final class GWTCoverflowCanvas {

	public Canvas canvas;
	public CoverflowMouseEvent mouseMoveEvent;

	public GWTCoverflowCanvas(int width, int height) {
		canvas = Canvas.createIfSupported();
		canvas.setCoordinateSpaceWidth(width);
		canvas.setCoordinateSpaceHeight(height);
		setBackgroundColor();
		initHandlers();
	}

	private void initHandlers() {
		canvas.addMouseDownHandler(new MouseDownHandler() {
			@Override
			public void onMouseDown(MouseDownEvent event) {
				int x = event.getClientX() - canvas.getAbsoluteLeft();
				mouseMoveEvent.setMouseDownCoordonates(x);
			}
		});

		canvas.addMouseUpHandler(new MouseUpHandler() {
			@Override
			public void onMouseUp(MouseUpEvent event) {
				int x = event.getClientX() - canvas.getAbsoluteLeft();
				mouseMoveEvent.setMouseUpCoordonates(x);
				mouseMoveEvent.onCoverflowMouseEvent();
			}
		});
	}

	public void clearCanvas() {
		canvas.getContext2d().clearRect(0, 0, canvas.getCoordinateSpaceWidth(), canvas.getCoordinateSpaceHeight());
		setBackgroundColor();
	}

	public void setBackgroundColor() {
		canvas.getContext2d().setFillStyle(Coverflow.BACKGROUND_COLOR);
		canvas.getContext2d().fillRect(0, 0, canvas.getCoordinateSpaceWidth(), canvas.getCoordinateSpaceHeight());
	}

	public void setFrontGradient() {
		canvas.getContext2d().translate(0, 0);
		CanvasGradient gradient = canvas.getContext2d().createLinearGradient(0, 0,
				canvas.getCoordinateSpaceWidth() / 2, 0);
		gradient.addColorStop(1, "transparent");
		gradient.addColorStop(0.1, Coverflow.BACKGROUND_COLOR);
		canvas.getContext2d().setFillStyle(gradient);
		canvas.getContext2d().fillRect(0, 0, canvas.getCoordinateSpaceWidth() / 2, canvas.getCoordinateSpaceHeight());
		CanvasGradient gradient2 = canvas.getContext2d().createLinearGradient(canvas.getCoordinateSpaceWidth() / 2, 0,
				canvas.getCoordinateSpaceWidth(), 0);
		gradient2.addColorStop(0.9, Coverflow.BACKGROUND_COLOR);
		gradient2.addColorStop(0, "transparent");
		canvas.getContext2d().setFillStyle(gradient2);
		canvas.getContext2d().fillRect(canvas.getCoordinateSpaceWidth() / 2, 0, canvas.getCoordinateSpaceWidth(),
				canvas.getCoordinateSpaceHeight());
	}

	public void setMouseMoveEvent(CoverflowMouseEvent event) {
		this.mouseMoveEvent = event;
	}

	public Canvas getCanvas() {
		return canvas;
	}

}