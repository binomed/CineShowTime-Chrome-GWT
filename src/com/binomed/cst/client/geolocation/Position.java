package com.binomed.cst.client.geolocation;

import com.google.gwt.core.client.JavaScriptObject;

public final class Position extends JavaScriptObject {

	protected Position() {
	}

	public native double getLatitude()/*-{
		return this.coords.latitude;
	}-*/;

	public native double getLongitude()/*-{
		return this.coords.longitude;
	}-*/;
}
