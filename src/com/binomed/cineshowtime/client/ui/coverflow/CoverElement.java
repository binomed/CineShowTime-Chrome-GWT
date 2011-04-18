package com.binomed.cineshowtime.client.ui.coverflow;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.CanvasGradient;
import com.google.gwt.dom.client.ImageElement;

public class CoverElement {

	private final static int DEFAULT_IMG_HEIGHT = 150;
	private final static int DEFAULT_IMG_WIDTH = 100;

	private final CoverData data;
	private ImageElement image;
	private int leftX;
	private int topY;
	private int zIndex;
	private int sens;

	private boolean isTransformed;

	public CoverElement(CoverData data) {
		this.data = data;
	}

	public void draw(Canvas canvas) {
		// double sx = 0.75; // .75 horizontal shear
		// double sy = 0; // no vertical shear
		// // apply custom transform
		// canvas.getContext2d().setTransform(1, sy, sx, 1, 0, 0);
		// canvas.getContext2d().save();
		// canvas.getContext2d().translate(0, 0);
		// canvas.getContext2d().scale(1, 1);
		// canvas.getContext2d().rect(leftX, topY, DEFAULT_IMG_WIDTH, DEFAULT_IMG_HEIGHT);
		// canvas.getContext2d().setFillStyle("black");
		// canvas.getContext2d().setShadowColor("#8ED6FF");
		// canvas.getContext2d().setShadowBlur(10);
		// canvas.getContext2d().fill();
		// canvas.getContext2d().restore();

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

		// Draw cover label
		canvas.getContext2d().save();
		canvas.getContext2d().translate(0, 0);
		canvas.getContext2d().setFillStyle("#00f");
		canvas.getContext2d().setTextBaseline("top");
		canvas.getContext2d().setFont("bold 15px sans-serif");
		canvas.getContext2d().fillText(data.getLabel(), leftX, topY + DEFAULT_IMG_HEIGHT);
		canvas.getContext2d().restore();
	}

	public void setPosition(int position) {
		zIndex = Math.abs(position);
		if (position < 0) {
			sens = -1;
		} else if (position > 0) {
			sens = -1;
		} else {
			sens = 0;
		}
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

	public void setCenterX(int centerX) {
		this.leftX = centerX - (getWidth() / 2);
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

	public void setTransform(boolean transform) {
		this.isTransformed = transform;
	}

}
