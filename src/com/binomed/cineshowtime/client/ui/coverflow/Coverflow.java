package com.binomed.cineshowtime.client.ui.coverflow;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.binomed.cineshowtime.client.model.MovieBean;
import com.binomed.cineshowtime.client.model.TheaterBean;
import com.binomed.cineshowtime.client.resources.CstResource;
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
	private final Map<String, CoverElement> covers;
	/** Indicate if the cover is centered */
	private boolean coverCentered;
	/** Revelant index covers of the coverflow */
	private String idCoverCentered;
	private String idFirstCover;
	private String idMiddleCover;

	private final TheaterBean currentTheater;
	private ClickCoverListener clickCoverListener;

	public Coverflow(int width, int height, TheaterBean curentTheater) {
		coverflowCanvas = new GWTCoverflowCanvas(width, height);
		coverflowCenterX = width / 2;
		this.currentTheater = curentTheater;
		this.covers = new HashMap<String, CoverElement>();
	}

	public void addClickCoverListener(ClickCoverListener clickCoverListener) {
		this.clickCoverListener = clickCoverListener;
	}

	/**
	 * Initialize the coverflow with covers
	 * 
	 * @param covers
	 *            All Covers elements with an unique String ID as key Images URLs
	 */
	public void init(final List<CoverData> coversData) {
		// Initialize Coverflow data
		this.idFirstCover = coversData.get(0).getId();
		this.idMiddleCover = coversData.get(coversData.size() / 2).getId();
		for (CoverData coverData : coversData) {
			if (StringUtils.isEmpty(coverData.getCoverUrl())) {
				coverData.setCoverURL(CstResource.instance.no_poster().getURL());
			}
			covers.put(coverData.getId(), new CoverElement(coverData));
		}

		// Add coverflow move listener
		coverflowCanvas.setMouseMoveEvent(new CoverflowMouseEvent() {
			@Override
			public void onClickCover(int clickX) {
				// Determine the distance between the center and the click x coordonate
				String idSelectedCover = CoverflowUtil.getIdOfCoverFromX(covers, clickX);
				// Center or open movie details ?
				if (!StringUtils.equalsIC(idSelectedCover, idCoverCentered)) {
					if (idSelectedCover != null) {
						moveToCover(idSelectedCover);
					}
				} else {
					MovieBean movie = CineShowTimeWS.getInstance().getMovie(idSelectedCover);
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
		CoversLoader.loadImages(covers, new CoversLoader.LoadImagesCallBack() {
			@Override
			public void onImagesLoaded(Map<String, ImageElement> imageElements) {
				int offsetX = 0;
				CoverElement initCover = null;
				for (Entry<String, ImageElement> entry : imageElements.entrySet()) {
					// Get the initialized cover
					initCover = covers.get(entry.getKey());
					// Initialize the cover
					initCover.setImage(entry.getValue());
					initCover.setLeftX(offsetX);
					initCover.setTopY(TOP_PADDING);
					// Drax the cover
					initCover.draw(coverflowCanvas.getCanvas());
					// Compute next cover offset X
					offsetX = offsetX + initCover.getWidth() + SPACE_BETWEEN_IMAGES;
				}
				coverflowCanvas.setFrontGradient();
				moveToCover(idMiddleCover);
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
	public void loadCover(String idCover, String imageUrl) {
		if (StringUtils.isNotEmpty(imageUrl)) {
			CoverElement loadedCover = covers.get(idCover);
			final Image logoImg = new Image(imageUrl);
			if (loadedCover != null) {
				loadedCover.setImage((ImageElement) logoImg.getElement().cast());
				loadedCover.draw(coverflowCanvas.getCanvas());
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
		if (covers.containsKey(idFirstCover)) {
			final CoverElement firstCover = covers.get(idFirstCover);
			final int firstLeftX = firstCover.getLeftX();

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

					if (Math.abs(firstLeftX - firstCover.getLeftX()) >= Math.abs(distance)) {
						stop();
						if (!coverCentered) {
							// Finish the move and center to an image
							moveToCover(CoverflowUtil.getIdOfCoverFromX(covers, coverflowCenterX));
						}
					}

					// Compute next covers X coordonates
					for (CoverElement cover : covers.values()) {
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
		for (CoverElement cover : covers.values()) {
			cover.draw(coverflowCanvas.getCanvas());
		}
		coverflowCanvas.setFrontGradient();
	}

	/**
	 * Move the specified cover index to the center of the coverflow
	 * 
	 * @param coverIndex
	 *            Index of the cover
	 */
	private void moveToCover(String idCover) {
		// Compute image center & move distance
		if (idCover != null && covers.containsKey(idCover)) {
			CoverElement goToCover = covers.get(idCover);
			int coverCenterX = goToCover.getLeftX() + (goToCover.getWidth() / 2);
			coverCentered = true;
			idCoverCentered = idCover;
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
