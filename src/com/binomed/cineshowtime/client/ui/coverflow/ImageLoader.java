package com.binomed.cineshowtime.client.ui.coverflow;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.google.gwt.dom.client.ImageElement;

public class ImageLoader {

	private static List<ImageLoader> imgLoaders = new ArrayList<ImageLoader>();

	private LoadImageCallBack callBack;
	private String imageId;
	private String imageUrl;
	private ImageElement imageElement;

	private ImageLoader(String imageId, String imageUrl) {
		this.imageId = imageId;
		this.imageUrl = imageUrl;
	}

	public interface LoadImageCallBack {
		void onImageLoaded(String coverId, ImageElement imageElement);
	}

	public static void load(String imageId, String imageUrl, LoadImageCallBack imageCB) {
		ImageLoader loader = new ImageLoader(imageId, imageUrl);
		ImageLoader.imgLoaders.add(loader);
		loader.callBack = imageCB;
		loader.imageElement = loader.createImageElement(imageUrl);
		loader.imageElement.setSrc(imageUrl);
	}

	public static void loadImages(Map<String, CoverElement> imagesUrls, LoadImageCallBack cb) {
		for (Entry<String, CoverElement> entry : imagesUrls.entrySet()) {
			load(entry.getKey(), entry.getValue().getCoverURL(), cb);
		}
	}

	private void onLoadComplete() {
		if (callBack != null) {
			callBack.onImageLoaded(imageId, imageElement);
			ImageLoader.imgLoaders.remove(this);
		}
	}

	public native ImageElement createImageElement(String url)/*-{
		var img = new Image();
		var __this = this;
		img.onload = function() {
			if (!img.__isLoaded) {
				__this.@com.binomed.cineshowtime.client.ui.coverflow.ImageLoader::onLoadComplete()();
			}
		}

		return img;
	}-*/;

}
