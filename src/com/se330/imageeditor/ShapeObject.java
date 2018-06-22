package com.se330.imageeditor;

import java.awt.Color;
import java.awt.Point;
import java.awt.Shape;

public class ShapeObject extends ActionObject{
	String shapeType = "";
	Shape shape = null;
	int strokeSize = 0;
	Color strokeColor = null;
	boolean fillMode = false;
	Color fillColor = null;
	
	public ShapeObject(String type, Shape shape, int strokeSize, Color strokeColor, boolean fillMode, Color fillColor, Point startPoint, Point endPoint){
		this.shapeType = type;
		this.shape = shape;
		this.strokeSize = strokeSize;
		this.strokeColor = strokeColor;
		this.fillMode = fillMode;
		this.fillColor = fillColor;
		this.startPoint = startPoint;
		this.endPoint = endPoint;
	}

	public Shape getShape() {
		return shape;
	}

	public void setShape(Shape shape) {
		this.shape = shape;
	}

	public int getStrokeSize() {
		return strokeSize;
	}

	public void setStrokeSize(int strokeSize) {
		this.strokeSize = strokeSize;
	}

	public Color getStrokeColor() {
		return strokeColor;
	}

	public void setStrokeColor(Color strokeColor) {
		this.strokeColor = strokeColor;
	}

	public boolean isFillMode() {
		return fillMode;
	}

	public void setFillMode(boolean fillMode) {
		this.fillMode = fillMode;
	}

	public Color getFillColor() {
		return fillColor;
	}

	public void setFillColor(Color fillColor) {
		this.fillColor = fillColor;
	}

	public String getShapeType() {
		return shapeType;
	}

	public void setShapeType(String shapeType) {
		this.shapeType = shapeType;
	}

	
	
}
