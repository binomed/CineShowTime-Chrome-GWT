package com.binomed.cineshowtime.client.ui;

import java.util.Date;
import java.util.List;

import com.binomed.cineshowtime.client.IClientFactory;
import com.binomed.cineshowtime.client.model.ProjectionBean;
import com.binomed.cineshowtime.client.resources.CstResource;
import com.binomed.cineshowtime.client.resources.i18n.I18N;
import com.binomed.cineshowtime.client.util.StringUtils;
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

	private IClientFactory clientFactory;

	public void setClientFactory(IClientFactory clientFactory) {
		this.clientFactory = clientFactory;
	}

	public ProjectionView() {
		// Initialization
		initWidget(uiBinder.createAndBindUi(this));
	}

	public void updateProjections(String theaterName, long movieTime, List<ProjectionBean> projections) {
		movieSeanceGroup.setCaptionText(theaterName);
		String text;
		Label projectionTimeStart;
		StringBuilder movieStart = new StringBuilder("");
		boolean nextShow = true;
		Date currentTime = new Date();
		String dateFormat = "HH:mm";
		if (StringUtils.equalsIC("12", clientFactory.getDataBaseHelper().readPref(I18N.instance.preference_gen_key_time_format()))) {
			dateFormat = "hh:mm a";
		}
		int addsTime = Integer.valueOf(clientFactory.getDataBaseHelper().readPref(I18N.instance.preference_gen_key_time_adds()));

		for (ProjectionBean projection : projections) {
			movieStart.delete(0, movieStart.length());
			if (projection.getLang() != null) {
				movieStart = movieStart.append(projection.getLang()).append(" : ");
			}
			Date time = new Date(projection.getShowtime());
			Date timeEnd = new Date(projection.getShowtime() + movieTime + addsTime);
			text = I18N.instance.projectionFrom() + " " + DateTimeFormat.getFormat(dateFormat).format(time) //
					+ " " + I18N.instance.projectionTo() + " " + DateTimeFormat.getFormat(dateFormat).format(timeEnd);//
			if (projection.getLang() != null) {
				text += " (" + projection.getLang() + ")";
			}

			projectionTimeStart = new Label(text);

			String className = CstResource.instance.css().projectionFutur();
			if (time.getTime() < currentTime.getTime()) {
				className = CstResource.instance.css().projectionPassed();
			} else if (nextShow) {
				nextShow = false;
				className = CstResource.instance.css().projectionNear();
			}

			projectionTimeStart.setStyleName(className);

			movieSeanceList.add(projectionTimeStart);
		}
	}

	interface ProjectionViewUiBinder extends UiBinder<Widget, ProjectionView> {
	}
}
