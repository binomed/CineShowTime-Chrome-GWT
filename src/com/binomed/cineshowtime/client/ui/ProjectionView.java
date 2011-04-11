package com.binomed.cineshowtime.client.ui;

import java.util.Date;

import com.binomed.cineshowtime.client.model.ProjectionBean;
import com.binomed.cineshowtime.client.resources.I18N;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class ProjectionView extends Composite {

	private static ProjectionViewUiBinder uiBinder = GWT.create(ProjectionViewUiBinder.class);

	@UiField
	Label projectionTimeStart, projectionTimeEnd;

	public ProjectionView(ProjectionBean projection, long movieTime) {

		// Initialization
		initWidget(uiBinder.createAndBindUi(this));

		StringBuilder movieStart = new StringBuilder("");
		if (projection.getLang() != null) {
			movieStart = movieStart.append(projection.getLang()).append(" : ");
		}
		Date time = new Date(projection.getShowtime());
		Date timeEnd = new Date(projection.getShowtime() + movieTime);
		projectionTimeStart.setText(movieStart.append(time.getHours()).append("h").append(time.getMinutes()).append("m").toString());
		projectionTimeEnd.setText(I18N.instance.endProjection() + timeEnd.getHours() + "h" + timeEnd.getMinutes() + "m");

	}

	interface ProjectionViewUiBinder extends UiBinder<Widget, ProjectionView> {
	}
}
