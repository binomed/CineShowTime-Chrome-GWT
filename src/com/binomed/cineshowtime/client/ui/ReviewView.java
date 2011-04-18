package com.binomed.cineshowtime.client.ui;

import com.binomed.cineshowtime.client.model.ReviewBean;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class ReviewView extends Composite {

	private static ReviewViewUiBinder uiBinder = GWT.create(ReviewViewUiBinder.class);

	@UiField
	Label reviewContent;
	@UiField
	Hyperlink reviewSourceLink, reviewLink;
	@UiField
	VerticalPanel rate;

	public ReviewView(ReviewBean review) {

		// Initialization
		initWidget(uiBinder.createAndBindUi(this));

		// TODO à compléter
		reviewContent.setText(review.getReview());
		reviewLink.setText(review.getAuthor());
		reviewSourceLink.setText(review.getUrlReview());
		rate.add(new RateView(false, Double.valueOf(review.getRate())));

	}

	interface ReviewViewUiBinder extends UiBinder<Widget, ReviewView> {
	}
}
