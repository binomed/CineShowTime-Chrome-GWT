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

	@Source("com/binomed/cineshowtime/client/resources/images/maps.png")
	ImageResource maps();

	@Source("com/binomed/cineshowtime/client/resources/images/btn_star_big_off.png")
	ImageResource btn_stat_big_off();

	@Source("com/binomed/cineshowtime/client/resources/images/btn_star_big_on.png")
	ImageResource btn_stat_big_on();

	@Source("com/binomed/cineshowtime/client/resources/images/Icone_512x512.png")
	ImageResource Icone_512_512();

	@Source("com/binomed/cineshowtime/client/resources/images/logo.jpg")
	ImageResource logo();

	@Source("com/binomed/cineshowtime/client/resources/images/rate_star_small_half.png")
	ImageResource rate_star_small_half();

	@Source("com/binomed/cineshowtime/client/resources/images/rate_star_small_off.png")
	ImageResource rate_star_small_off();

	@Source("com/binomed/cineshowtime/client/resources/images/rate_star_small_on.png")
	ImageResource rate_star_small_on();

	@Source("com/binomed/cineshowtime/client/resources/images/no_poster.png")
	ImageResource no_poster();

	@Source("com/binomed/cineshowtime/client/resources/images/close_tab.png")
	ImageResource close_tab();

	@Source("com/binomed/cineshowtime/client/resources/images/close_tab_hover.png")
	ImageResource close_tab_hover();

	// CSS
	@Source("com/binomed/cineshowtime/client/resources/css/CstStyle.css")
	public StyleCss styleCss();

	interface StyleCss extends CssResource {
		String mainPanel();

		String allWidth();
	}

}
