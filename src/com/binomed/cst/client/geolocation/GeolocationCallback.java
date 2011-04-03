package com.binomed.cst.client.geolocation;

import com.google.gwt.core.client.JavaScriptObject;

public interface GeolocationCallback {

	void onLocation(Position position);

	void onError(JavaScriptObject jso);

}
