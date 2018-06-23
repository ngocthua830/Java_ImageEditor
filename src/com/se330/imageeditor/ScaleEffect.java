package com.se330.imageeditor;

import java.awt.Point;

public class ScaleEffect extends EffectObject{
	int width, height;
	public ScaleEffect(String effectType, boolean allImage, Point startPoint,
			Point endPoint, int width, int height) {
		super(effectType, allImage, startPoint, endPoint);
		this.width = width;
		this.height = height;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	
}
