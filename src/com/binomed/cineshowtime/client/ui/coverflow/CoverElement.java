package com.binomed.cineshowtime.client.ui.coverflow;

import com.binomed.cineshowtime.client.resources.CstResource;
import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.CanvasGradient;
import com.google.gwt.canvas.dom.client.Context2d.TextAlign;
import com.google.gwt.canvas.dom.client.Context2d.TextBaseline;
import com.google.gwt.dom.client.ImageElement;

public class CoverElement {

	private final static int DEFAULT_IMG_HEIGHT = 150;
	private final static int DEFAULT_IMG_WIDTH = 100;

	private int height;
	private int width;

	private final CoverData data;
	private ImageElement image;
	private ImageElement playVideoImg;
	private int leftX;
	private int topY;
	private double scale;

	public CoverElement(CoverData data) {
		this.data = data;
		if (data != null && data.isVideo()) {
			height = DEFAULT_IMG_WIDTH;
			width = DEFAULT_IMG_HEIGHT;
			ImageLoader.load(data.getId(), CstResource.instance.play().getURL(), new ImageLoader.LoadImageCallBack() {
				@Override
				public void onImageLoaded(String coverId, ImageElement imageElement) {
					playVideoImg = imageElement;
				}
			});
		} else {
			height = DEFAULT_IMG_HEIGHT;
			width = DEFAULT_IMG_WIDTH;
		}
		this.scale = 1;
	}

	public void draw(Canvas canvas) {
		// Draw the image
		canvas.getContext2d().save();
		canvas.getContext2d().translate(0, 0);
		canvas.getContext2d().scale(1, 1);
		canvas.getContext2d().drawImage(image, leftX, topY, getWidth(), getHeight());
		canvas.getContext2d().restore();

		// Draw the image play
		if (data != null && data.isVideo() && playVideoImg != null) {
			canvas.getContext2d().save();
			canvas.getContext2d().setGlobalAlpha(0.5);
			canvas.getContext2d().translate(0, 0);
			canvas.getContext2d().scale(1, 1);
			canvas.getContext2d().drawImage(playVideoImg //
					, leftX + ((getWidth()) / 2) - 24 //
					, topY + ((getHeight()) / 2) - 24 //
					, 48, 48);
			canvas.getContext2d().restore();
		}

		// Draw the mirror image
		canvas.getContext2d().save();
		canvas.getContext2d().setGlobalAlpha(0.5);
		canvas.getContext2d().translate(0, getHeight());
		canvas.getContext2d().scale(-1, 1);
		canvas.getContext2d().rotate(Math.PI);
		canvas.getContext2d().drawImage(image, leftX, -(topY + (getHeight())), getWidth(), getHeight());
		canvas.getContext2d().restore();

		// Draw the mirror image play
		if (data != null && data.isVideo() && playVideoImg != null) {
			canvas.getContext2d().save();
			canvas.getContext2d().setGlobalAlpha(0.25);
			canvas.getContext2d().translate(0, getHeight());
			canvas.getContext2d().scale(-1, 1);
			canvas.getContext2d().rotate(Math.PI);
			canvas.getContext2d().drawImage(playVideoImg //
					, leftX + ((getWidth()) / 2) - 24 //
					, -(topY + (getHeight())) + ((getHeight()) / 2) - 24 //
					, 48, 48);
			canvas.getContext2d().restore();
		}

		// Add gradient to the mirror image
		canvas.getContext2d().save();
		canvas.getContext2d().translate(0, 0);
		canvas.getContext2d().scale(1, 1);
		CanvasGradient gradient = canvas.getContext2d().createLinearGradient(0, topY + (getHeight()), 0, topY + 2 * (getHeight()));
		gradient.addColorStop(0, "transparent");
		gradient.addColorStop(0.5, Coverflow.BACKGROUND_COLOR);
		gradient.addColorStop(1, Coverflow.BACKGROUND_COLOR);
		canvas.getContext2d().setFillStyle(gradient);
		canvas.getContext2d().fillRect(leftX, topY + (getHeight()), getWidth(), getHeight());
		canvas.getContext2d().restore();

		// Draw cover label
		writeDataLabelTextBlock(canvas);

	}

	private void writeDataLabelTextBlock(Canvas canvas) {
		final int MAX = 10;
		canvas.getContext2d().save();
		canvas.getContext2d().translate(0, 0);
		canvas.getContext2d().scale(1, 1);
		canvas.getContext2d().setFillStyle("#FFFFFF");
		canvas.getContext2d().setTextAlign(TextAlign.CENTER);
		canvas.getContext2d().setTextBaseline(TextBaseline.TOP);
		canvas.getContext2d().setFont("bold 15px sans-serif");
		String[] splitted = data.getLabel().split("\\s");
		StringBuffer sb = new StringBuffer();
		int index = 0;
		if (splitted != null && splitted.length > 0) {
			for (int i = 0; i < splitted.length; i++) {
				if (sb.length() >= MAX) {
					canvas.getContext2d().fillText(sb.toString(), leftX + (getWidth() / 2), topY + getHeight() + (index * 15), getWidth());
					sb.delete(0, sb.length());
					sb.append(splitted[i]).append(" ");
					index++;
				} else {
					sb.append(splitted[i]).append(" ");
				}
				if (sb.length() > 0 && i == splitted.length - 1) {
					canvas.getContext2d().fillText(sb.toString(), leftX + (getWidth() / 2), topY + getHeight() + (index * 15), getWidth());
					index++;
				}
			}
		} else {
			canvas.getContext2d().fillText(data.getLabel(), leftX + (getWidth() / 2), topY + getHeight(), getWidth());
		}
		canvas.getContext2d().restore();
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

	public int getWidth() {
		return (int) (width * scale);
	}

	public int getHeight() {
		return (int) (height * scale);
	}

	public String getCoverURL() {
		return data.getCoverUrl();
	}

	public void setCoverURL(String coverURL) {
		data.setCoverURL(coverURL);
	}

	public void setScale(double scale) {
		this.scale = scale;
	}

}
