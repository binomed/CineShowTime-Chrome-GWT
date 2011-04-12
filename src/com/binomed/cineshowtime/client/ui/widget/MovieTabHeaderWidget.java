package com.binomed.cineshowtime.client.ui.widget;

import com.binomed.cineshowtime.client.resources.CstResource;
import com.binomed.cineshowtime.client.ui.MovieView;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TabLayoutPanel;

public class MovieTabHeaderWidget extends Composite {

	private final String movieName;

	private final TabLayoutPanel parentTabPanel;

	private final MovieView movieView;

	private Image closeImage;

	public MovieTabHeaderWidget(final String movieName, final MovieView movieView, final TabLayoutPanel parentTabPanel) {
		this.movieName = movieName;
		this.movieView = movieView;
		this.parentTabPanel = parentTabPanel;
		this.init();

	}

	public void init() {
		HorizontalPanel h = new HorizontalPanel();
		h.add(new Label(this.movieName));
		closeImage = new Image(CstResource.instance.close_tab());
		closeImage.setAltText("close tab!!");
		closeImage.addMouseOverHandler(new MouseOverHandler() {
			@Override
			public void onMouseOver(MouseOverEvent event) {
				closeImage.setResource(CstResource.instance.close_tab_hover());
			}
		});
		closeImage.addMouseOutHandler(new MouseOutHandler() {
			@Override
			public void onMouseOut(MouseOutEvent event) {
				closeImage.setResource(CstResource.instance.close_tab());
			}
		});
		closeImage.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				parentTabPanel.remove(movieView);
			}
		});
		h.add(closeImage);
		initWidget(h);
	}
}
