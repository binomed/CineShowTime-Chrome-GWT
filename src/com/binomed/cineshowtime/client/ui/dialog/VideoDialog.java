package com.binomed.cineshowtime.client.ui.dialog;

import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;

public class VideoDialog extends DialogBox {

	private final static String WIDTH = "640px";
	private final static String HEIGHT = "390px";

	public VideoDialog(final String name, final String id) {
		setText(name);
		setAnimationEnabled(true);
		setGlassEnabled(true);
		setSize(WIDTH, HEIGHT);
		setAutoHideEnabled(true);

		HTML content = new HTML("<iframe title=\"YouTube video player\" " //
				+ "width=\"" + WIDTH + "\" " //
				+ "height=\"" + HEIGHT + "\" " + "src=\"http://www.youtube.com/embed/" + id + "\" frameborder=\"0\" allowfullscreen></iframe>");

		// Video video = new Video(address);
		// video.setSize(WIDTH, HEIGHT);
		setWidget(content);

	}

}
