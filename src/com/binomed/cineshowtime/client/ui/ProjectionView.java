package com.binomed.cineshowtime.client.ui;

import java.util.Date;
import java.util.List;

import com.binomed.cineshowtime.client.model.ProjectionBean;
import com.binomed.cineshowtime.client.resources.I18N;
import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.CaptionPanel;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class ProjectionView extends Composite {

	private static ProjectionViewUiBinder uiBinder = GWT.create(ProjectionViewUiBinder.class);

	@UiField
	CaptionPanel movieSeanceGroup;
	@UiField
	VerticalPanel movieSeanceList;

	public ProjectionView() {
		// Initialization
		initWidget(uiBinder.createAndBindUi(this));
	}

	public void updateProjections(String theaterName, long movieTime, List<ProjectionBean> projections) {
		movieSeanceGroup.setCaptionText(theaterName);
		Label projectionTimeStart;
		Label projectionTimeEnd;
		StringBuilder movieStart = new StringBuilder("");
		boolean nextShow = true;
		Date currentTime = new Date();
		for (ProjectionBean projection : projections) {
			movieStart.delete(0, movieStart.length());
			if (projection.getLang() != null) {
				movieStart = movieStart.append(projection.getLang()).append(" : ");
			}
			Date time = new Date(projection.getShowtime());
			Date timeEnd = new Date(projection.getShowtime() + movieTime);
			projectionTimeStart = new Label(DateTimeFormat.getFormat("HH:mm").format(time));
			projectionTimeEnd = new Label(I18N.instance.endProjection() + DateTimeFormat.getFormat("HH:mm").format(timeEnd));

			String className = "style.projectionFutur";
			className = "style.projectionNear";
			if (time.getTime() < currentTime.getTime()) {
				className = ".projectionPassed";
				className = ".projectionNear";
			} else if (nextShow) {
				nextShow = false;
				className = "projectionNear";
				className = "projectionNear";
			}

			projectionTimeStart.setStylePrimaryName(className);
			projectionTimeEnd.setStyleName(className);

			movieSeanceList.add(projectionTimeStart);
			movieSeanceList.add(projectionTimeEnd);
		}
	}

	interface ProjectionViewUiBinder extends UiBinder<Widget, ProjectionView> {
	}
}
