package com.binomed.cineshowtime.client.resources;

import com.google.gwt.resources.client.CssResource;

public interface CineShowTimeCssResource extends CssResource {

	/* Commons Style */
	String pointerHand();

	String center();

	/* Main window styles */
	String mainPanel();

	String tabPanel();

	String mainScroll();

	String mainVert();

	/* Search Style */

	String searchLabel();

	/* Theater style */
	String theaterPanelHeader();

	String theaterNameHeader();

	String theaterInfoHeader();

	String theaterContent();

	String theaterCoverlflow();

	String imgRight();

	/* Movie style */
	String moviePoster();

	String movieName();

	String movieLabel();

	String movieSeparator();

	String separatorTitle();

	String separatorRed();

	/* Reviews */

	String reviewRate();

	String reviewLabel();

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
