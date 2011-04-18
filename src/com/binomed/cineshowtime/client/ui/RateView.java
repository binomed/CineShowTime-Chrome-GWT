package com.binomed.cineshowtime.client.ui;

import com.binomed.cineshowtime.client.resources.CstResource;
import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;

public class RateView extends Composite {

	private static RateViewUiBinder uiBinder = GWT.create(RateViewUiBinder.class);

	@UiField
	Image rate1, rate2, rate3, rate4, rate5, rate6, rate7, rate8, rate9, rate10;

	public RateView(boolean full, double rate) {

		// Initialization
		initWidget(uiBinder.createAndBindUi(this));

		ImageResource rateImg1 = CstResource.instance.rate_star_small_off();
		ImageResource rateImg2 = CstResource.instance.rate_star_small_off();
		ImageResource rateImg3 = CstResource.instance.rate_star_small_off();
		ImageResource rateImg4 = CstResource.instance.rate_star_small_off();
		ImageResource rateImg5 = CstResource.instance.rate_star_small_off();
		ImageResource rateImg6 = CstResource.instance.rate_star_small_off();
		ImageResource rateImg7 = CstResource.instance.rate_star_small_off();
		ImageResource rateImg8 = CstResource.instance.rate_star_small_off();
		ImageResource rateImg9 = CstResource.instance.rate_star_small_off();
		ImageResource rateImg10 = CstResource.instance.rate_star_small_off();
		if (rate != -1) {
			switch (Double.valueOf(rate).intValue()) {
			case 10:
				rateImg10 = CstResource.instance.rate_star_small_on();
			case 9:
				rateImg9 = CstResource.instance.rate_star_small_on();
				if ((rate > 9.5) && (rate < 10)) {
					rateImg10 = CstResource.instance.rate_star_small_half();
				}
			case 8:
				rateImg8 = CstResource.instance.rate_star_small_on();
				if ((rate > 8.5) && (rate < 9)) {
					rateImg9 = CstResource.instance.rate_star_small_half();
				}
			case 7:
				rateImg7 = CstResource.instance.rate_star_small_on();
				if ((rate > 7.5) && (rate < 8)) {
					rateImg8 = CstResource.instance.rate_star_small_half();
				}
			case 6:
				rateImg6 = CstResource.instance.rate_star_small_on();
				if ((rate > 6.5) && (rate < 7)) {
					rateImg7 = CstResource.instance.rate_star_small_half();
				}
			case 5:
				rateImg5 = CstResource.instance.rate_star_small_on();
				if ((rate > 5.5) && (rate < 6)) {
					rateImg6 = CstResource.instance.rate_star_small_half();
				}
			case 4:
				rateImg4 = CstResource.instance.rate_star_small_on();
				if ((rate > 4.5) && (rate < 5)) {
					rateImg5 = CstResource.instance.rate_star_small_half();
				}
			case 3:
				rateImg3 = CstResource.instance.rate_star_small_on();
				if ((rate > 3.5) && (rate < 4)) {
					rateImg4 = CstResource.instance.rate_star_small_half();
				}
			case 2:
				rateImg2 = CstResource.instance.rate_star_small_on();
				if ((rate > 2.5) && (rate < 3)) {
					rateImg3 = CstResource.instance.rate_star_small_half();
				}
			case 1:
				rateImg1 = CstResource.instance.rate_star_small_on();
				if ((rate > 1.5) && (rate < 2)) {
					rateImg2 = CstResource.instance.rate_star_small_half();
				}
			case 0:
				if ((rate > 0.5) && (rate < 1)) {
					rateImg1 = CstResource.instance.rate_star_small_half();
				}
			default:
				break;
			}

		}
		rate6.setVisible(full);
		rate7.setVisible(full);
		rate8.setVisible(full);
		rate9.setVisible(full);
		rate10.setVisible(full);

		rate1.setResource(rateImg1);
		rate2.setResource(rateImg2);
		rate3.setResource(rateImg3);
		rate4.setResource(rateImg4);
		rate5.setResource(rateImg5);
		rate6.setResource(rateImg6);
		rate7.setResource(rateImg7);
		rate8.setResource(rateImg8);
		rate9.setResource(rateImg9);
		rate10.setResource(rateImg10);

	}

	interface RateViewUiBinder extends UiBinder<Widget, RateView> {
	}
}
