package com.binomed.cst.client.widget;

import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.widgetideas.graphics.client.Color;
import com.google.gwt.widgetideas.graphics.client.ImageLoader;

// Make a new canvas
// Coverflow coverflow = new Coverflow(800, 200);
// coverflow.init();
// RootPanel.get("coverflow").add(coverflow.getCanvas());
public class Coverflow {

	private static final int SPACE_BETWEEN_IMAGES = 10;
	private final static int ANIMATION_SPEED = 5;

	/** HTML5 canvas object */
	private final GWTCoverflowCanvas canvas;

	/** Images url to load in the coverflow */
	private final String[] imagesUrls = new String[] {
			"http://www.google.fr/logos/2011/bunsen11-res.png", //
			"http://www.google.fr/logos/2011/bunsen11-res.png", "http://www.google.fr/logos/2011/bunsen11-res.png",
			"http://www.google.fr/logos/2011/bunsen11-res.png", "http://www.google.fr/logos/2011/bunsen11-res.png",
			"http://www.google.fr/logos/2011/bunsen11-res.png", "http://www.google.fr/logos/2011/bunsen11-res.png" };

	/** Loaded images in the coverflow */
	private ImageElement[] images;
	/** Animation timer */
	private static Timer timer;
	private static int firstImageOffsetX = 0;

	public Coverflow(int width, int height) {
		canvas = new GWTCoverflowCanvas(width, height);
		canvas.setBackgroundColor(Color.ALPHA_GREY);
	}

	public void init() {
		// Add coverflow move listener
		canvas.setCoverflowMouseMoveEvent(new CoverflowMouseMoveEvent() {
			@Override
			public void onMove(int direction, int distance) {
				animateCoverflow(direction, distance);
			}
		});

		// Load images for the first time
		ImageLoader.loadImages(imagesUrls, new ImageLoader.CallBack() {
			@Override
			public void onImagesLoaded(ImageElement[] imageElements) {
				int offsetX = 0;
				for (int i = 0; i < imageElements.length; i++) {
					images = imageElements;
					canvas.translate(0, 0);
					canvas.scale(1f, 1f);
					canvas.drawImage(imageElements[i], offsetX, 0, imageElements[0].getWidth(), imageElements[0].getHeight());
					offsetX = offsetX + imageElements[i].getWidth() + SPACE_BETWEEN_IMAGES;
				}
			}
		});
	}

	private void animateCoverflow(final int direction, final int distance) {
		if (timer != null) {
			timer.cancel();
		}
		timer = new Timer() {
			int translateX = 0;

			@Override
			public void run() {
				renderCoverflowImages(translateX, direction);
				if (direction == CoverflowMouseMoveEvent.DIRECTION_RIGHT && translateX <= distance) {
					translateX += ANIMATION_SPEED;
				} else if (direction == CoverflowMouseMoveEvent.DIRECTION_LEFT && translateX >= distance) {
					translateX -= ANIMATION_SPEED;
				} else {
					cancel();
					// Update the offset X of the first image
					firstImageOffsetX += distance;
				}
			}
		};
		timer.schedule(10);
	}

	private void renderCoverflowImages(int translateX, int direction) {
		int offsetX = firstImageOffsetX;
		if (!isImageOutOfBound(offsetX + translateX, direction)) {
			canvas.saveContext();
			canvas.clear();
			for (int i = 0; i < images.length; i++) {
				canvas.drawImage(images[i], offsetX + translateX, 0);
				offsetX += images[i].getWidth() + SPACE_BETWEEN_IMAGES;
			}
			canvas.restoreContext();
			timer.schedule(10);
		}
	}

	private boolean isImageOutOfBound(int offsetX, int direction) {
		if (direction == CoverflowMouseMoveEvent.DIRECTION_RIGHT && offsetX >= 0) {
			timer.cancel();
			firstImageOffsetX = 0;
			return true;
		} else if (direction == CoverflowMouseMoveEvent.DIRECTION_LEFT) {
			// TODO
		}
		return false;
	}

	public Widget getCanvas() {
		return canvas;
	}

}
