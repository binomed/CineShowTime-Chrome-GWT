package com.binomed.cineshowtime.client.ui.coverflow;

import java.util.Map;
import java.util.Map.Entry;

import com.binomed.cineshowtime.client.model.MovieBean;
import com.binomed.cineshowtime.client.model.TheaterBean;
import com.binomed.cineshowtime.client.service.ws.CineShowTimeWS;
import com.binomed.cineshowtime.client.util.StringUtils;
import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;

public class Coverflow {

	public static final int MOVE_LEFT = -1;
	public static final int MOVE_RIGHT = 1;
	public static final String BACKGROUND_COLOR = "#000000";
	private final int TOP_PADDING = 20;
	private final int SPACE_BETWEEN_IMAGES = 20;

	/** HTML5 canvas object */
	private final GWTCoverflowCanvas coverflowCanvas;
	/** Coverflow center X */
	private final int coverflowCenterX;
	/** Loaded images in the coverflow */
	private CoverElement[] covers;
	/** Indicate if the cover is centered */
	private boolean coverCentered;
	/** index of the centered cover */
	private int indexCoverCentered;

	private final TheaterBean currentTheater;
	private ClickCoverListener clickCoverListener;

	public Coverflow(int width, int height, TheaterBean curentTheater) {
		coverflowCanvas = new GWTCoverflowCanvas(width, height);
		coverflowCenterX = width / 2;
		this.currentTheater = curentTheater;
	}

	public void addClickCoverListener(ClickCoverListener clickCoverListener) {
		this.clickCoverListener = clickCoverListener;
	}

	/**
	 * Initialize the coverflow with images URLs
	 * 
	 * @param imagesUrls
	 *            Images URLs
	 */
	public void init(Map<String, String> imagesUrls) {
		// Add coverflow move listener
		coverflowCanvas.setMouseMoveEvent(new CoverflowMouseEvent() {
			@Override
			public void onClickCover(int clickX) {
				// Determine the distance between the center and the click x coordonate
				int indexSelectedImg = CoverflowUtil.getIndexOfCoverFromX(covers, clickX);
				// Center or open movie details ?
				if (indexSelectedImg != indexCoverCentered) {
					// Center the coverflow on the selected image
					if (indexSelectedImg != -1) {
						moveToCover(indexSelectedImg);
					}
				} else {
					MovieBean movie = CineShowTimeWS.getInstance().getMovie(covers[indexSelectedImg].getIdCover());
					clickCoverListener.onClickCover(currentTheater, movie);
				}
			}

			@Override
			public void onCoverMove(int direction, int distance) {
				coverCentered = false;
				animateCoverflow(direction, distance);
			}
		});

		// Load images for the first time
		ImagesLoader.loadImages(imagesUrls, new ImagesLoader.LoadImagesCallBack() {
			@Override
			public void onImagesLoaded(Map<String, ImageElement> imageElements) {
				covers = new CoverElement[imageElements.size()];
				int offsetX = 0;
				int index = 0;
				for (Entry<String, ImageElement> entry : imageElements.entrySet()) {
					// Initialize the cover
					covers[index] = new CoverElement(entry.getKey(), entry.getValue(), offsetX, TOP_PADDING, 0);
					// Drax the cover
					covers[index].draw(coverflowCanvas.getCanvas());
					// Compute next cover offset X
					offsetX = offsetX + covers[index].getWidth() + SPACE_BETWEEN_IMAGES;
					index++;
				}
				coverflowCanvas.setFrontGradient();
				center();
			}
		});

	}

	/**
	 * Change the image of the specified cover (by the index)
	 * 
	 * @param index
	 *            Index of the cover
	 * @param imageUrl
	 *            Image URL of the cover
	 */
	public void loadCover(final int index, String imageUrl) {
		if (StringUtils.isNotEmpty(imageUrl)) {
			final Image logoImg = new Image(imageUrl);
			if (covers[index] != null) {
				covers[index].setImage((ImageElement) logoImg.getElement().cast());
				covers[index].draw(coverflowCanvas.getCanvas());
			}
			drawCoverflow();
		}
	}

	/**
	 * Animate the coverflow moves <br/>
	 * http://www.html5canvastutorials.com/advanced/html5-canvas-linear-motion-animation/
	 * 
	 * @param direction
	 *            Direction of the coverflow
	 * @param distance
	 *            Distance to animate
	 */
	private void animateCoverflow(final int direction, final int distance) {
		if (covers[0] != null) {
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
							moveToCover(CoverflowUtil.getIndexOfCoverFromX(covers, coverflowCenterX));
						}
					}

					// Compute next covers X coordonates
					for (CoverElement cover : covers) {
						cover.setLeftX(cover.getLeftX() + totalTranslateX);
					}
				}

				@Override
				public void drawStage() {
					drawCoverflow();
				}
			};

			// start animation
			myAnimation.start();
		}
	}

	/**
	 * Draw the entire coverflow
	 */
	public void drawCoverflow() {
		coverflowCanvas.setBackgroundColor();
		for (CoverElement cover : covers) {
			cover.draw(coverflowCanvas.getCanvas());
		}
		coverflowCanvas.setFrontGradient();
	}

	/**
	 * Center and move the coverflow to the cental cover
	 */
	private void center() {
		int middleIndex = 0;
		if (covers != null && covers.length > 0) {
			middleIndex = covers.length / 2;
		}
		moveToCover(middleIndex);
	}

	/**
	 * Move the specified cover index to the center of the coverflow
	 * 
	 * @param coverIndex
	 *            Index of the cover
	 */
	private void moveToCover(int coverIndex) {
		// Compute image center & move distance
		if (coverIndex >= 0 && coverIndex < covers.length) {
			int coverCenterX = covers[coverIndex].getLeftX() + (covers[coverIndex].getWidth() / 2);
			coverCentered = true;
			indexCoverCentered = coverIndex;
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
