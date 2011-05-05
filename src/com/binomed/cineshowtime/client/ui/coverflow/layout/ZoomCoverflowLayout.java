package com.binomed.cineshowtime.client.ui.coverflow.layout;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.binomed.cineshowtime.client.IClientFactory;
import com.binomed.cineshowtime.client.event.service.RequestImdbEvent;
import com.binomed.cineshowtime.client.model.MovieBean;
import com.binomed.cineshowtime.client.model.ProjectionBean;
import com.binomed.cineshowtime.client.resources.i18n.I18N;
import com.binomed.cineshowtime.client.ui.coverflow.CoverElement;
import com.binomed.cineshowtime.client.ui.coverflow.GWTCoverflowCanvas;
import com.binomed.cineshowtime.client.util.StringUtils;
import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d.TextAlign;
import com.google.gwt.canvas.dom.client.Context2d.TextBaseline;
import com.google.gwt.i18n.client.DateTimeFormat;

public class ZoomCoverflowLayout implements CoverflowLayout {

	private final int TOP_PADDING = 20;
	public static final int SPACE_BETWEEN_IMAGES = 20;

	private final Map<String, List<ProjectionBean>> movieMap;
	private String centerCoverId;
	private IClientFactory clientFactory;
	private String theaterId;

	public ZoomCoverflowLayout(IClientFactory clientFactory, Map<String, List<ProjectionBean>> movieMap, String theaterId) {
		this.movieMap = movieMap;
		this.clientFactory = clientFactory;
		this.theaterId = theaterId;
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
			if (cover.getLeftX() > (0 - cover.getWidth()) && cover.getLeftX() < coverflowCanvas.getWidth()) {
				if (cover.getData().getState() != -1) {
					if (cover.getData().getState() == MovieBean.STATE_NONE || cover.getData().getState() == MovieBean.STATE_IN_PROGRESS) {
						clientFactory.getEventBusHandler().fireEvent(new RequestImdbEvent(theaterId, cover.getData().getId()));
					}
				}
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
					generateProjections(canvas, projections, movie); // TODO Movie time!
				}
			}
		}
	}

	private static final int PROJECTIONS_Y = 235;
	private static final int PROJECTIONS_X = 200;
	private static final int LINE_HEIGHT = 20;
	private static final int TIME_WIDTH = 40;

	private void generateProjections(Canvas canvas, List<ProjectionBean> projections, MovieBean movie) {
		StringBuilder movieInfo = new StringBuilder();
		movieInfo.append(DateTimeFormat.getFormat("HH'h'mm'm'").format(new Date(movie.getMovieTime())));
		if (movie.getStyle() != null) {
			movieInfo.append(" - ").append(movie.getStyle());
		}

		// init text canvas
		canvas.getContext2d().save();
		canvas.getContext2d().translate(0, 0);
		canvas.getContext2d().scale(1, 1);
		canvas.getContext2d().setTextBaseline(TextBaseline.TOP);
		canvas.getContext2d().setTextAlign(TextAlign.LEFT);
		canvas.getContext2d().setFillStyle("#FFFFFF");
		canvas.getContext2d().setFont("bold 14px sans-serif");
		canvas.getContext2d().fillText(movieInfo.toString(), PROJECTIONS_X, PROJECTIONS_Y);
		canvas.getContext2d().restore();

		// init text canvas
		canvas.getContext2d().save();
		canvas.getContext2d().translate(0, 0);
		canvas.getContext2d().scale(1, 1);
		canvas.getContext2d().setTextBaseline(TextBaseline.TOP);
		final Date currentTime = new Date();
		boolean nextShow = true;
		String times = null;
		Date showtime = null;
		int colX = 0;
		int colY = 0;
		int nbLang = 1;
		Map<String, Integer> timesXByLang = new java.util.HashMap<String, Integer>();
		String dateFormat = "HH:mm";
		if (StringUtils.equalsIC("12", clientFactory.getDataBaseHelper().readPref(I18N.instance.preference_gen_key_time_format()))) {
			dateFormat = "hh:mm a";
		}
		for (ProjectionBean projection : projections) {
			// build text
			showtime = new Date(projection.getShowtime());
			times = DateTimeFormat.getFormat(dateFormat).format(showtime);

			// Group by lang
			if (projection.getLang() != null && !timesXByLang.containsKey(projection.getLang())) {
				colY = PROJECTIONS_Y + (LINE_HEIGHT * nbLang);
				timesXByLang.put(projection.getLang(), colY);
				canvas.getContext2d().setFillStyle("#FFFFFF");
				canvas.getContext2d().setFont("bold 14px sans-serif");
				canvas.getContext2d().setTextAlign(TextAlign.LEFT);
				colX = PROJECTIONS_X;
				canvas.getContext2d().fillText(projection.getLang() + " : ", colX, colY);
				colX = (int) (colX + canvas.getContext2d().measureText(projection.getLang() + " : ").getWidth());
				nbLang++;
			} else if (projection.getLang() == null && !timesXByLang.containsKey("None")) {
				colY = PROJECTIONS_Y + (LINE_HEIGHT * nbLang);
				timesXByLang.put("None", colY);
				colX = PROJECTIONS_X;
				nbLang++;
			}

			if (timesXByLang.containsKey(projection.getLang())) {
				colY = timesXByLang.get(projection.getLang());
			} else {
				colY = timesXByLang.get("None");
			}

			// Set style
			if (showtime.getTime() < currentTime.getTime()) {
				canvas.getContext2d().setFillStyle("#999999");
				canvas.getContext2d().setFont("italic 13px sans-serif");
			} else if (nextShow) {
				nextShow = false;
				canvas.getContext2d().setFillStyle("#FFFFFF");
				canvas.getContext2d().setFont("bold 14px sans-serif");
			} else {
				canvas.getContext2d().setFillStyle("#FFFFFF");
				canvas.getContext2d().setFont("13px sans-serif");
			}

			canvas.getContext2d().setTextAlign(TextAlign.LEFT);
			// draw text
			canvas.getContext2d().fillText(times, colX, colY);
			colX += TIME_WIDTH;
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
