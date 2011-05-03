package com.binomed.cineshowtime.client.ui;

import com.binomed.cineshowtime.client.IClientFactory;
import com.binomed.cineshowtime.client.event.ui.SeparatorOpenEvent;
import com.binomed.cineshowtime.client.resources.CstResource;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiConstructor;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class HeaderSeparator extends Composite {

	private static HeaderSeparatorUiBinder uiBinder = GWT.create(HeaderSeparatorUiBinder.class);

	@UiField
	Label separatorName;
	@UiField
	Image separatorExpand;
	private IClientFactory factory;
	private String name;
	private String source;
	private boolean expand;
	private boolean expandShow;

	public @UiConstructor
	HeaderSeparator(boolean expandShow, String name) {
		// Initialization
		initWidget(uiBinder.createAndBindUi(this));
		this.expandShow = expandShow;
		separatorExpand.setVisible(expandShow);
		separatorName.setText(name);

		expand = false;
		this.name = name;
		if (expandShow) {
			separatorExpand.setResource(CstResource.instance.plus());
			separatorExpand.addStyleName(CstResource.instance.css().pointerHand());
			separatorExpand.addStyleName(CstResource.instance.css().separatorExpand());
			separatorName.addStyleName(CstResource.instance.css().pointerHand());
		}
	}

	public void setFactory(IClientFactory factory) {
		this.factory = factory;
	}

	public void setSource(String source) {
		this.source = source;
	}

	@UiHandler("separatorExpand")
	public void onExpand(ClickEvent event) {
		manageExpand();
	}

	@UiHandler("separatorName")
	public void onOpenSeparator(ClickEvent event) {
		manageExpand();
	}

	public void manageExpand() {
		if (expandShow) {
			expand = !expand;
			separatorExpand.setResource(expand ? CstResource.instance.minus() : CstResource.instance.plus());
			factory.getEventBusHandler().fireEvent(new SeparatorOpenEvent(source, name));
		}
	}

	interface HeaderSeparatorUiBinder extends UiBinder<Widget, HeaderSeparator> {
	}
}
