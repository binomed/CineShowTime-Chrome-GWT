package com.binomed.cineshowtime.client.ui.coverflow;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.binomed.cineshowtime.client.resources.CstResource;
import com.binomed.cineshowtime.client.ui.coverflow.event.ClickCoverListener;
import com.binomed.cineshowtime.client.ui.coverflow.event.CoverflowMouseEvent;
import com.binomed.cineshowtime.client.ui.coverflow.layout.BetterCoverflowLayout;
import com.binomed.cineshowtime.client.ui.coverflow.layout.CoverflowLayout;
import com.binomed.cineshowtime.client.ui.coverflow.layout.SimpleCoverflowLayout;
import com.binomed.cineshowtime.client.util.StringUtils;
import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.user.client.ui.Widget;

public class Coverflow {

	public static final int MOVE_LEFT = -1;
	public static final int MOVE_RIGHT = 1;
	public static final String BACKGROUND_COLOR = "#555555";

	/** HTML5 canvas object */
	private final GWTCoverflowCanvas coverflowCanvas;
	/** Loaded images in the coverflow */
	private final Map<String, CoverElement> covers;
	/** Indicate if the cover is centered */
	private boolean coverCentered;
	/** Revelant index covers of the coverflow */
	private String idCoverCentered;
	private String idFirstCover;
	private String idMiddleCover;

	private final CoverflowLayout dispo = new BetterCoverflowLayout();

	private ClickCoverListener clickCoverListener;

	public Coverflow(int width, int height) {
		coverflowCanvas = new GWTCoverflowCanvas(width, height);
		this.covers = new HashMap<String, CoverElement>();
	}

	public void addClickCoverListener(ClickCoverListener clickCoverListener) {
		this.clickCoverListener = clickCoverListener;
	}

	/**
	 * Initialize the coverflow with covers
	 * @param covers All Covers elements with an unique String ID as key Images URLs
	 */
	public void init(final List<CoverData> coversData) {
		// Initialize Coverflow data
		this.idFirstCover = coversData.get(0).getId();
		this.idMiddleCover = coversData.get(coversData.size() / 2).getId();

		for (CoverData coverData : coversData) {
			if (StringUtils.isEmpty(coverData.getCoverUrl())) {
				coverData.setCoverURL(CstResource.instance.loading_preview_portrait().getURL());
			}
			covers.put(coverData.getId(), new CoverElement(coverData));
		}
		dispo.onInitCovers(covers);

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
					if (clickCoverListener != null) {
						clickCoverListener.onClickCover(idSelectedCover);
					}
				}
			}

			@Override
			public void onCoverMove(int direction, int distance) {
				coverCentered = false;
				moveToCover(direction, distance);
			}
		});

		ImageLoader.loadImages(covers, new ImageLoader.LoadImageCallBack() {
			@Override
			public void onImageLoaded(String coverId, ImageElement imageElement) {
				final CoverElement loadedCover = covers.get(coverId);
				if (loadedCover != null) {
					loadedCover.setImage(imageElement);
					loadedCover.draw(coverflowCanvas.getCanvas());
				}
			}
		});
		moveToCover(idMiddleCover);
	}

	/**
	 * Change the image of the specified cover (by the index)
	 * @param index Index of the cover
	 * @param imageUrl Image URL of the cover
	 */
	public void updateCover(String idCover, String imageUrl) {
		if (StringUtils.isEmpty(imageUrl)) {
			imageUrl = CstResource.instance.no_poster().getURL();
		}
		ImageLoader.load(idCover, imageUrl, new ImageLoader.LoadImageCallBack() {
			@Override
			public void onImageLoaded(String coverId, ImageElement imageElement) {
				final CoverElement loadedCover = covers.get(coverId);
				if (loadedCover != null) {
					loadedCover.setImage(imageElement);
					drawCoverflow();
				}
			}
		});
	}

	private void moveToCover(int direction, int distance) {
		// Finish the move and center to an image
		if (direction == MOVE_LEFT) {
			moveToCover(CoverflowUtil.getIdOfCoverFromX(covers, (coverflowCanvas.getWidth() / 2) - distance));
		} else if (direction == MOVE_RIGHT) {
			moveToCover(CoverflowUtil.getIdOfCoverFromX(covers, (coverflowCanvas.getWidth() / 2) + distance));
		}
	}

	/**
	 * Move the specified cover id to the center of the coverflow
	 * @param coverIndex Index of the cover
	 */
	private void moveToCover(String idCover) {
		// Compute image center & move distance
		if (idCover != null && covers.containsKey(idCover)) {
			CoverElement goToCover = covers.get(idCover);
			int coverCenterX = goToCover.getLeftX() + (goToCover.getWidth() / 2);
			coverCentered = true;
			idCoverCentered = idCover;
			int distance = (coverflowCanvas.getWidth() / 2) - coverCenterX;
			// render image
			if (distance <= 0) { // Go left
				animateCoverflow(MOVE_LEFT, Math.abs(distance));
			} else { // Go right
				animateCoverflow(MOVE_RIGHT, Math.abs(distance));
			}
		}
	}

	/**
	 * Animate the coverflow moves <br/>
	 * http://www.html5canvastutorials.com/advanced/html5-canvas-linear-motion-animation/
	 * @param direction Direction of the coverflow
	 * @param distance Distance to animate
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

					if (Math.abs(firstLeftX - firstCover.getLeftX()) >= (distance - SimpleCoverflowLayout.SPACE_BETWEEN_IMAGES)) {
						stop();
						if (!coverCentered) {
							// Finish the move and center to an image
							moveToCover(CoverflowUtil.getIdOfCoverFromX(covers, (coverflowCanvas.getWidth() / 2)));
						}
					}

					// Compute next covers X coordonates
					dispo.onUpdateCovers(idCoverCentered, covers, totalTranslateX);
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

	public void drawCoverflow() {
		coverflowCanvas.clearCanvas();
		dispo.onDrawCovers(coverflowCanvas, covers);
		drawCoverflowWidget();
		coverflowCanvas.setFrontGradient();
	}

	private void drawCoverflowWidget() {
		// Draw the image
		coverflowCanvas.getCanvas().getContext2d().save();
		coverflowCanvas.getCanvas().getContext2d().translate(0, 0);
		coverflowCanvas.getCanvas().getContext2d().scale(1, 1);
		coverflowCanvas.getCanvas().getContext2d().setFillStyle("#FF0000");
		coverflowCanvas.getCanvas().getContext2d().beginPath();
		coverflowCanvas.getCanvas().getContext2d().arc(coverflowCanvas.getWidth() / 2, coverflowCanvas.getHeight() / 2, 2, 0, Math.PI * 2, true);
		coverflowCanvas.getCanvas().getContext2d().closePath();
		coverflowCanvas.getCanvas().getContext2d().fill();
		coverflowCanvas.getCanvas().getContext2d().restore();
	}

	public Widget getCanvas() {
		return coverflowCanvas.getCanvas();
	}

}
