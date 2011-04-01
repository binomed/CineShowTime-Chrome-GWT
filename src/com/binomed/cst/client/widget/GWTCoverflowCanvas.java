package com.binomed.cst.client.widget;

import com.google.gwt.user.client.Event;
import com.google.gwt.widgetideas.graphics.client.GWTCanvas;

/**
 * Canvas used for the coverflow
 */
public final class GWTCoverflowCanvas extends GWTCanvas {

	public CoverflowMouseMoveEvent coverflowMouseMoveEvent;

	public GWTCoverflowCanvas(int width, int height) {
		super(width, height);
		sinkEvents(Event.MOUSEEVENTS);
	}

	@Override
	public void onBrowserEvent(Event event) {
		super.onBrowserEvent(event);
		if (coverflowMouseMoveEvent != null) {
			int x = event.getClientX() - getAbsoluteLeft();
			switch (event.getTypeInt()) {
			case Event.ONMOUSEDOWN:
				coverflowMouseMoveEvent.setMouseDownCoordonates(x);
				break;
			case Event.ONMOUSEUP:
				coverflowMouseMoveEvent.setMouseUpCoordonates(x);
				coverflowMouseMoveEvent.onMove();
				break;
			}
		}
	}

	public void setCoverflowMouseMoveEvent(CoverflowMouseMoveEvent event) {
		this.coverflowMouseMoveEvent = event;
	}

}