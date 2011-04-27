package com.binomed.cineshowtime.client.ui;

import java.util.Date;
import java.util.List;

import com.binomed.cineshowtime.client.model.ProjectionBean;
import com.binomed.cineshowtime.client.resources.I18N;
import com.google.gwt.core.client.GWT;
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
			projectionTimeStart = new Label(movieStart.append(time.getHours()).append("h").append(time.getMinutes()).append("m").toString());
			projectionTimeEnd = new Label(I18N.instance.endProjection() + timeEnd.getHours() + "h" + timeEnd.getMinutes() + "m");

			String className = "style.projectionFutur";
			if (time.getTime() < currentTime.getTime()) {
				className = "style.projectionPassed";
			} else if (nextShow) {
				nextShow = false;
				className = "style.projectionNear";
			}

			projectionTimeStart.setStyleName(className);
			projectionTimeEnd.setStyleName(className);

			movieSeanceList.add(projectionTimeStart);
			movieSeanceList.add(projectionTimeEnd);
		}
	}

	interface ProjectionViewUiBinder extends UiBinder<Widget, ProjectionView> {
	}
}
