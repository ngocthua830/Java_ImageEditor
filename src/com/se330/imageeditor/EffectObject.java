package com.se330.imageeditor;

import java.awt.Color;
import java.awt.Point;
import java.awt.Shape;

public class EffectObject extends ActionObject{
	String effectType = "";
	boolean allImage = true;
	public EffectObject(String effectType, boolean allImage, Point startPoint, Point endPoint){
		this.effectType = effectType;
		this.allImage = allImage;
		this.startPoint = startPoint;
		this.endPoint = endPoint;
	}
	public String getEffectType() {
		return effectType;
	}
	public void setEffectType(String effectType) {
		this.effectType = effectType;
	}
	public boolean isAllImage() {
		return allImage;
	}
	public void setAllImage(boolean allImage) {
		this.allImage = allImage;
	}
	
}
