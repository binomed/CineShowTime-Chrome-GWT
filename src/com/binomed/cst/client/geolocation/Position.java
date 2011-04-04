package com.binomed.cst.client.geolocation;

import com.google.gwt.core.client.JavaScriptObject;

/**
 * Implementation of a native position returned by geolocation
 */
public final class Position extends JavaScriptObject {

	protected Position() {
	}

	/**
	 * Return the latitude of the position
	 * 
	 * @return Latitude
	 */
	public native double getLatitude()/*-{
		return this.coords.latitude;
	}-*/;

	/**
	 * Return the longitude of the position
	 * 
	 * @return longitude
	 */
	public native double getLongitude()/*-{
		return this.coords.longitude;
	}-*/;
}
