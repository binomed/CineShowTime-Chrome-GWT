package com.binomed.cineshowtime.client.ui.coverflow;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.CanvasGradient;
import com.google.gwt.dom.client.ImageElement;

public class CoverElement {

	private final static int DEFAULT_IMG_HEIGHT = 150;
	private final static int DEFAULT_IMG_WIDTH = 100;

	private CoverData data;
	private ImageElement image;
	private int leftX;
	private int topY;
	private int zIndex;

	public CoverElement(CoverData data) {
		this.data = data;
	}

	public void draw(Canvas canvas) {
		// Draw the image
		canvas.getContext2d().save();
		canvas.getContext2d().translate(0, 0);
		canvas.getContext2d().scale(1, 1);
		canvas.getContext2d().drawImage(image, leftX, topY, DEFAULT_IMG_WIDTH, DEFAULT_IMG_HEIGHT);
		canvas.getContext2d().restore();

		// Draw the mirror image
		canvas.getContext2d().save();
		canvas.getContext2d().setGlobalAlpha(0.5);
		canvas.getContext2d().translate(0, DEFAULT_IMG_HEIGHT);
		canvas.getContext2d().scale(-1, 1);
		canvas.getContext2d().rotate(Math.PI);
		canvas.getContext2d().drawImage(image, leftX, -(topY + DEFAULT_IMG_HEIGHT), DEFAULT_IMG_WIDTH, DEFAULT_IMG_HEIGHT);
		canvas.getContext2d().restore();

		// Add gradient to the mirror image
		canvas.getContext2d().save();
		canvas.getContext2d().translate(0, 0);
		CanvasGradient gradient = canvas.getContext2d().createLinearGradient(0, topY + DEFAULT_IMG_HEIGHT, 0, topY + 2 * DEFAULT_IMG_HEIGHT);
		gradient.addColorStop(0, "transparent");
		gradient.addColorStop(0.5, Coverflow.BACKGROUND_COLOR);
		gradient.addColorStop(1, Coverflow.BACKGROUND_COLOR);
		canvas.getContext2d().setFillStyle(gradient);
		canvas.getContext2d().fillRect(leftX, topY + DEFAULT_IMG_HEIGHT, DEFAULT_IMG_WIDTH, DEFAULT_IMG_HEIGHT);
		canvas.getContext2d().restore();

		// Draw movie title
		// canvas.getContext2d().save();
		// canvas.getContext2d().translate(0, 0);
		// canvas.getContext2d().setFillStyle("#00f");
		// canvas.getContext2d().setTextBaseline("top");
		// canvas.getContext2d().setFont("bold 20px sans-serif");
		// canvas.getContext2d().fillText(this.labelCover, leftX, topY + DEFAULT_IMG_HEIGHT);
		// canvas.getContext2d().restore();
	}

	public ImageElement getImage() {
		return image;
	}

	public void setImage(ImageElement image) {
		this.image = image;
	}

	public int getLeftX() {
		return leftX;
	}

	public int getRightX() {
		return leftX + getWidth();
	}

	public void setLeftX(int leftX) {
		this.leftX = leftX;
	}

	public int getTopY() {
		return topY;
	}

	public void setTopY(int topY) {
		this.topY = topY;
	}

	public int getZIndex() {
		return zIndex;
	}

	public void setZIndex(int zIndex) {
		this.zIndex = zIndex;
	}

	public int getWidth() {
		return DEFAULT_IMG_WIDTH;
	}

	public String getCoverURL() {
		return data.getCoverUrl();
	}

	public void setCoverURL(String coverURL) {
		data.setCoverURL(coverURL);
	}

}
