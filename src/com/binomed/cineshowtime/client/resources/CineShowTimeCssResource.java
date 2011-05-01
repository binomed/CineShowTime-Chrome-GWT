package com.binomed.cineshowtime.client.resources;

import com.google.gwt.resources.client.CssResource;

public interface CineShowTimeCssResource extends CssResource {

	/* Main window styles */
	String mainPanel();

	String tabPanel();

	String pointerHand();

	/* Theater style */
	String theaterPanelHeader();

	String theaterNameHeader();

	String theaterInfoHeader();

	String theaterContent();

	String theaterCoverlflow();

	/* Movie style */
	String movieName();

	String movieLabel();

	String movieSeparator();

	/* Projections */
	String projectionsCaption();

	String projectionsContent();

	String projectionPassed();

	String projectionNear();

	String projectionFutur();

	/* GWT Redefinition elements */
	@ClassName("gwt-TabPanel")
	String gwtTabPanel();

	@ClassName("gwt-TabPanelBottom")
	String gwtTabPanelBottom();

}
