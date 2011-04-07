package com.binomed.cineshowtime.client.ui.coverflow;

import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Widget;

public class Coverflow {

	public final static int MOVE_LEFT = -1;
	public final static int MOVE_RIGHT = 1;
	public final static String BACKGROUND_COLOR = "#000000";
	private final static int TOP_PADDING = 20;
	private final static int SPACE_BETWEEN_IMAGES = 20;

	/** HTML5 canvas object */
	private final GWTCoverflowCanvas coverflowCanvas;
	/** Coverflow center X */
	private static int coverflowCenterX;
	/** Loaded images in the coverflow */
	private static CoverElement[] covers;
	/** Indicate if the cover is centered */
	private static boolean coverCentered;
	/** index of the centered cover */
	private static int indexImageCentered;

	public Coverflow(int width, int height) {
		coverflowCanvas = new GWTCoverflowCanvas(width, height);
		coverflowCenterX = width / 2;
	}

	/**
	 * Initialize the coverflow with images URLs
	 * @param imagesUrls Images URLs
	 */
	public void init(String[] imagesUrls) {
		// Add coverflow move listener
		coverflowCanvas.setMouseMoveEvent(new CoverflowMouseEvent() {
			@Override
			public void onClickCover(int clickX) {
				// Determine the distance between the center and the click x coordonate
				int indexSelectedImg = CoverflowUtil.getIndexOfCoverFromX(covers, clickX);
				// Center or open movie details ?
				if (indexSelectedImg != indexImageCentered) {
					// Center the coverflow on the selected image
					if (indexSelectedImg != -1) {
						centerCover(indexSelectedImg);
					}
				} else {
					// Show the movie details
					Window.alert("Show movie");
				}
			}

			@Override
			public void onCoverMove(int direction, int distance) {
				coverCentered = false;
				animateCoverflow(direction, distance);
			}
		});

		// Load images for the first time
		ImageLoader.loadImages(imagesUrls, new ImageLoader.CallBack() {
			@Override
			public void onImagesLoaded(ImageElement[] imageElements) {
				covers = new CoverElement[imageElements.length];
				int offsetX = 0;
				for (int i = 0; i < imageElements.length; i++) {
					// Initialize the cover
					covers[i] = new CoverElement(imageElements[i], offsetX, TOP_PADDING, 0);
					// Drax the cover
					covers[i].draw(coverflowCanvas.getCanvas());
					// Compute next cover offset X
					offsetX = offsetX + covers[i].getWidth() + SPACE_BETWEEN_IMAGES;
				}
				coverflowCanvas.setFrontGradient();
			}
		});
	}

	/**
	 * Animate the coverflow moves <br/>
	 * http://www.html5canvastutorials.com/advanced/html5-canvas-linear-motion-animation/
	 * @param direction Direction of the coverflow
	 * @param distance Distance to animate
	 */
	private void animateCoverflow(final int direction, final int distance) {
		final int firstLeftX = covers[0].getLeftX();

		Animation myAnimation = new Animation(coverflowCanvas.getCanvas(), 100) {
			final int linearSpeed = 100; // pixels / second
			final int linearDistEachFrame = linearSpeed * getTimeInterval() / 1000;
			int totalTranslateX = 0;

			@Override
			public void updateStage() {
				if (direction == MOVE_RIGHT) {
					totalTranslateX += linearDistEachFrame;
				} else if (direction == MOVE_LEFT) {
					totalTranslateX -= linearDistEachFrame;
				}

				if (Math.abs(firstLeftX - covers[0].getLeftX()) >= Math.abs(distance)) {
					stop();
					if (!coverCentered) {
						// Finish the move and center to an image
						centerCover(CoverflowUtil.getIndexOfCoverFromX(covers, coverflowCenterX));
					}
				}

				// Compute next covers X coordonates
				for (CoverElement cover : covers) {
					cover.setLeftX(cover.getLeftX() + totalTranslateX);
				}
			}

			@Override
			public void drawStage() {
				coverflowCanvas.setBackgroundColor();
				for (CoverElement cover : covers) {
					cover.draw(coverflowCanvas.getCanvas());
				}
				coverflowCanvas.setFrontGradient();
			}
		};

		// start animation
		myAnimation.start();
	}

	private void centerCover(int indexImage) {
		// Compute image center & move distance
		if (indexImage >= 0 && indexImage < covers.length) {
			int coverCenterX = covers[indexImage].getLeftX() + (covers[indexImage].getWidth() / 2);
			coverCentered = true;
			indexImageCentered = indexImage;
			int distance = coverflowCenterX - coverCenterX;
			// render image
			if (distance <= 0) { // Go left
				animateCoverflow(MOVE_LEFT, distance);
			} else { // Go right
				animateCoverflow(MOVE_RIGHT, distance);
			}
		}
	}

	public Widget getCanvas() {
		return coverflowCanvas.getCanvas();
	}

}
