package com.binomed.cineshowtime.client.ui.coverflow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.google.gwt.dom.client.ImageElement;

/**
 * Provides a mechanism for deferred execution of a callback method once all specified Images are loaded.
 */
public class CoversLoader {

	/**
	 * Interface to allow anonymous instantiation of a CallBack Object with method that gets invoked when all the images
	 * are loaded.
	 */
	public interface LoadImagesCallBack {
		void onImagesLoaded(Map<String, ImageElement> imageElements);
	}

	/**
	 * Static internal collection of ImageLoader instances. ImageLoader is not instantiable externally.
	 */
	private static List<CoversLoader> imageLoaders = new ArrayList<CoversLoader>();

	/**
	 * Takes in an array of url Strings corresponding to the images needed to be loaded. The onImagesLoaded() method in
	 * the specified CallBack object is invoked with an array of ImageElements corresponding to the original input array
	 * of url Strings once all the images report an onload event.
	 * 
	 * @param urls
	 *            Array of urls for the images that need to be loaded
	 * @param cb
	 *            CallBack object
	 */
	public static void loadImages(Map<String, CoverElement> imagesUrls, LoadImagesCallBack cb) {
		CoversLoader il = new CoversLoader();
		for (Entry<String, CoverElement> entry : imagesUrls.entrySet()) {
			il.addHandle(entry.getKey(), il.prepareImage(entry.getValue().getCoverURL()));
		}
		il.finalize(cb);
		CoversLoader.imageLoaders.add(il);
		// Go ahead and fetch the images now
		for (Entry<String, CoverElement> entry : imagesUrls.entrySet()) {
			il.images.get(entry.getKey()).setSrc(entry.getValue().getCoverURL());
		}
	}

	private LoadImagesCallBack imagesCallBack = null;
	private final Map<String, ImageElement> images = new HashMap<String, ImageElement>();
	private int loadedImages = 0;
	private int totalImages = 0;

	private CoversLoader() {
	}

	/**
	 * Stores the ImageElement reference so that when all the images report an onload, we can return the array of all
	 * the ImageElements.
	 * 
	 * @param img
	 */
	private void addHandle(String id, ImageElement img) {
		this.totalImages++;
		this.images.put(id, img);
	}

	/**
	 * Invokes the onImagesLoaded method in the CallBack if all the images are loaded AND we have a CallBack specified.
	 * Called from the JSNI onload event handler.
	 */
	private void dispatchIfComplete() {
		if (imagesCallBack != null && isAllLoaded()) {
			imagesCallBack.onImagesLoaded(images);
			// remove the image loader
			CoversLoader.imageLoaders.remove(this);
		}
	}

	/**
	 * Sets the callback object for the ImageLoader. Once this is set, we may invoke the callback once all images that
	 * need to be loaded report in from their onload event handlers.
	 * 
	 * @param cb
	 */
	private void finalize(LoadImagesCallBack cb) {
		this.imagesCallBack = cb;
	}

	private void incrementLoadedImages() {
		this.loadedImages++;
	}

	private boolean isAllLoaded() {
		return (loadedImages == totalImages);
	}

	/**
	 * Returns a handle to an img object. Ties back to the ImageLoader instance
	 */
	private native ImageElement prepareImage(String url)/*-{
		// if( callback specified )
		// do nothing

		var img = new Image();
		var __this = this;

		img.onload = function() {
			if (!img.__isLoaded) {

				// __isLoaded should be set for the first time here.
				// if for some reason img fires a second onload event
				// we do not want to execute the following again (hence the guard)
				img.__isLoaded = true;
				__this.@com.binomed.cineshowtime.client.ui.coverflow.CoversLoader::incrementLoadedImages()();
				img.onload = null;

				// we call this function each time onload fires
				// It will see if we are ready to invoke the callback
				__this.@com.binomed.cineshowtime.client.ui.coverflow.CoversLoader::dispatchIfComplete()();
			} else {
				// we invoke the callback since we are already loaded
				__this.@com.binomed.cineshowtime.client.ui.coverflow.CoversLoader::dispatchIfComplete()();
			}
		}

		return img;
	}-*/;

}
