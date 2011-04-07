package com.binomed.cineshowtime.client.resources;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.resources.client.ImageResource;

public interface CstResource extends ClientBundle {

	public static final CstResource instance = GWT.create(CstResource.class);

	// Images
	@Source("com/binomed/cineshowtime/client/resources/images/star.png")
	// @ImageOptions(repeatStyle = RepeatStyle.Horizontal, height = 16, width = 16)
	ImageResource star();

	@Source("com/binomed/cineshowtime/client/resources/images/gps.png")
	ImageResource gps();

	// CSS
	@Source("com/binomed/cineshowtime/client/resources/css/CstStyle.css")
	public StyleCss styleCss();

	interface StyleCss extends CssResource {
		String mainPanel();

		String allWidth();
	}

}
