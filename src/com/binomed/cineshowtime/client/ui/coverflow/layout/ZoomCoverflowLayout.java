package com.binomed.cineshowtime.client.ui.coverflow.layout;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.binomed.cineshowtime.client.model.MovieBean;
import com.binomed.cineshowtime.client.model.ProjectionBean;
import com.binomed.cineshowtime.client.resources.i18n.I18N;
import com.binomed.cineshowtime.client.ui.coverflow.CoverElement;
import com.binomed.cineshowtime.client.ui.coverflow.GWTCoverflowCanvas;
import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d.TextAlign;
import com.google.gwt.canvas.dom.client.Context2d.TextBaseline;
import com.google.gwt.i18n.client.DateTimeFormat;

public class ZoomCoverflowLayout implements CoverflowLayout {

	private final int TOP_PADDING = 20;
	public static final int SPACE_BETWEEN_IMAGES = 20;

	private final Map<String, List<ProjectionBean>> movieMap;
	private String centerCoverId;

	public ZoomCoverflowLayout(Map<String, List<ProjectionBean>> movieMap) {
		this.movieMap = movieMap;
	}

	@Override
	public void onInitCovers(final Map<String, CoverElement> covers) {
		int offsetX = 0;
		for (CoverElement cover : covers.values()) {
			// Initialize the cover
			cover.setLeftX(offsetX);
			cover.setTopY(TOP_PADDING);
			// Compute next cover offset X
			offsetX = offsetX + cover.getWidth() + SPACE_BETWEEN_IMAGES;
		}
	}

	@Override
	public void onUpdateCovers(String indexCoverCenter, final Map<String, CoverElement> covers, int totalTranslateX) {
		double indexCenter = getIndexOfCoverId(indexCoverCenter, covers);
		double index = 0;
		for (CoverElement cover : covers.values()) {
			cover.setLeftX(cover.getLeftX() + totalTranslateX);
			if (indexCenter - 3 >= 0 && indexCenter - 3 == index) {
				cover.setScale(0.6d);
			} else if (indexCenter - 2 >= 0 && indexCenter - 2 == index) {
				cover.setScale(0.7d);
			} else if (indexCenter - 1 >= 0 && indexCenter - 1 == index) {
				cover.setScale(0.8d);
			} else if (indexCenter == index) {
				if (cover.getData() != null) {
					centerCoverId = cover.getData().getId();
				}
				cover.setScale(1.0d);
			} else if (indexCenter + 1 < covers.size() && indexCenter + 1 == index) {
				cover.setScale(0.8d);
			} else if (indexCenter + 2 < covers.size() && indexCenter + 2 == index) {
				cover.setScale(0.7d);
			} else if (indexCenter + 3 < covers.size() && indexCenter + 3 == index) {
				cover.setScale(0.6d);
			} else {
				cover.setScale(0.5d);
			}
			index++;
		}
	}

	@Override
	public void onDrawCovers(GWTCoverflowCanvas coverflowCanvas, Map<String, CoverElement> covers) {
		for (CoverElement cover : covers.values()) {
			if (cover.getLeftX() > 0 && cover.getLeftX() < coverflowCanvas.getWidth()) {
				cover.draw(coverflowCanvas.getCanvas());
			}
		}
		showProjectionList(coverflowCanvas.getCanvas(), covers);
	}

	private void showProjectionList(Canvas canvas, Map<String, CoverElement> covers) {
		CoverElement centerCover = covers.get(centerCoverId);
		if (centerCoverId != null && //
				centerCover != null && //
				centerCover.getData() != null //
				&& centerCover.getData() instanceof MovieBean) {
			MovieBean movie = (MovieBean) covers.get(centerCoverId).getData();
			if (movieMap != null && movieMap.size() > 0 && movie != null) {
				List<ProjectionBean> projections = movieMap.get(centerCoverId);
				if (projections != null && projections.size() > 0) {
					generateProjections(canvas, projections, movie.getMovieTime()); // TODO Movie time!
				}
			}
		}
	}

	private void generateProjections(Canvas canvas, List<ProjectionBean> projections, Long movieTime) {
		final Date currentTime = new Date();
		boolean nextShow = true;
		String text = null;
		int index = 0, lineNb = 0, nbCol = 1, colX = 0;

		if (projections.size() > 2 && projections.size() <= 6) {
			nbCol = 2;
		} else if (projections.size() > 6) {
			nbCol = 3;
		}
		for (ProjectionBean projection : projections) {
			// build text
			Date time = new Date(projection.getShowtime());
			Date timeEnd = new Date(projection.getShowtime() + movieTime);
			text = I18N.instance.projectionFrom() + " " + DateTimeFormat.getFormat("HH:mm").format(time) //
					+ " " + I18N.instance.projectionTo() + " " + DateTimeFormat.getFormat("HH:mm").format(timeEnd);//
			if (projection.getLang() != null) {
				text += " (" + projection.getLang() + ")";
			}

			// init text canvas
			canvas.getContext2d().save();
			canvas.getContext2d().translate(0, 0);
			canvas.getContext2d().scale(1, 1);
			canvas.getContext2d().setTextBaseline(TextBaseline.TOP);

			// Set style
			if (time.getTime() < currentTime.getTime()) {
				canvas.getContext2d().setFillStyle("#999999");
				canvas.getContext2d().setFont("italic 10px sans-serif");
			} else if (nextShow) {
				nextShow = false;
				canvas.getContext2d().setFillStyle("#FFFFFF");
				canvas.getContext2d().setFont("bold 11px sans-serif");
			} else {
				canvas.getContext2d().setFillStyle("#FFFFFF");
				canvas.getContext2d().setFont("10px sans-serif");
			}

			// update column number & colX
			if (nbCol == 1) {
				canvas.getContext2d().setTextAlign(TextAlign.CENTER);
				colX = 0;
			} else if (nbCol == 2) {
				if (index < projections.size() / 2) {
					canvas.getContext2d().setTextAlign(TextAlign.LEFT);
					colX = 10;
				} else if (index >= projections.size() / 2) {
					canvas.getContext2d().setTextAlign(TextAlign.RIGHT);
					colX = -10;
				}
			} else if (nbCol == 3) {
				if (index <= 2) {
					canvas.getContext2d().setTextAlign(TextAlign.LEFT);
					colX = 100;
				} else if (index >= 3 && index <= 5) {
					canvas.getContext2d().setTextAlign(TextAlign.CENTER);
					colX = 0;
				} else {
					canvas.getContext2d().setTextAlign(TextAlign.RIGHT);
					colX = -100;
				}
			}

			// draw text
			canvas.getContext2d().fillText(text, (canvas.getCoordinateSpaceWidth() / 2) + colX, 230 + (lineNb * 15), Math.abs(colX));

			// set next line number
			if (lineNb < 2) {
				lineNb++;
			} else {
				lineNb = 0;
			}
			index++;
		}
		canvas.getContext2d().restore();
	}

	private int getIndexOfCoverId(String coverId, Map<String, CoverElement> covers) {
		int index = 0;
		for (String curCurrentId : covers.keySet()) {
			if (curCurrentId.equalsIgnoreCase(coverId)) {
				return index;
			}
			index++;
		}
		return 0;
	}
}
