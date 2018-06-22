package com.se330.imageeditor;

import java.awt.Color;
import java.awt.Point;
import java.awt.Shape;

public abstract class ActionObject {
	Point startPoint = new Point();
	Point endPoint = new Point();
	public Point getStartPoint() {
		return startPoint;
	}
	public void setStartPoint(Point startPoint) {
		this.startPoint = startPoint;
	}
	public Point getEndPoint() {
		return endPoint;
	}
	public void setEndPoint(Point endPoint) {
		this.endPoint = endPoint;
	}
	
	
}
