package com.binomed.cineshowtime.client.ui.coverflow;

import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.user.client.Window;

public class ImageLoaderFactory {

	private final String url;
	private static LoadImageCallBack imageCallBack;
	private static ImageElement image;

	public ImageLoaderFactory(String url, LoadImageCallBack imageCB) {
		this.url = url;
		imageCallBack = imageCB;
		image = createImageElement(url);
		image.setSrc(url);
	}

	public interface LoadImageCallBack {
		void onImageLoaded(ImageElement imageElement);
	}

	private static void onLoadComplete() {
		Window.alert("of : " + imageCallBack);
		if (imageCallBack != null) {
			imageCallBack.onImageLoaded(image);
		}
	}

	/**
	 * Returns a handle to an img object. Ties back to the ImageLoader instance
	 */
	public native ImageElement createImageElement(String url)/*-{
		var img = new Image();
		img.onload = function() {
		this.@com.binomed.cineshowtime.client.ui.coverflow.ImageLoaderFactory::onLoadComplete();
		}

		return img;
	}-*/;

}
