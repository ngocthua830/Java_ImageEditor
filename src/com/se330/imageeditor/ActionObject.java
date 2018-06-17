package com.se330.imageeditor;

import java.awt.Color;
import java.awt.Point;
import java.awt.Shape;

public class ActionObject {
	String type = "";
	String shapeType = "";
	Shape shape = null;
	int strokeSize = 0;
	Color strokeColor = null;
	boolean fillMode = false;
	Color fillColor = null;
	Point startPoint = new Point();
	Point endPoint = new Point();
	
	String effectType = "";
	public ActionObject(String type, String shapeType, Shape shape, int strokeSize, Color strokeColor, boolean fillMode, Color fillColor, Point startPoint, Point endPoint, String effectType){
		this.type = type;
		this.shapeType = shapeType;
		this.shape = shape;
		this.strokeSize = strokeSize;
		this.strokeColor = strokeColor;
		this.fillMode = fillMode;
		this.fillColor = fillColor;
		this.startPoint = startPoint;
		this.endPoint = endPoint;
		this.effectType = effectType;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Color getStrokeColor() {
		return strokeColor;
	}
	public void setStrokeColor(Color strokeColor) {
		this.strokeColor = strokeColor;
	}
	public Color getFillColor() {
		return fillColor;
	}
	public void setFillColor(Color fillColor) {
		this.fillColor = fillColor;
	}
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
	public String getEffectType() {
		return effectType;
	}
	public void setEffectType(String effectType) {
		this.effectType = effectType;
	}
	public int getStrokeSize() {
		return strokeSize;
	}
	public void setStrokeSize(int strokeSize) {
		this.strokeSize = strokeSize;
	}
	public Shape getShape() {
		return shape;
	}
	public void setShape(Shape shape) {
		this.shape = shape;
	}
	public boolean isFillMode() {
		return fillMode;
	}
	public void setFillMode(boolean fillMode) {
		this.fillMode = fillMode;
	}
	public String getShapeType() {
		return shapeType;
	}
	public void setShapeType(String shapeType) {
		this.shapeType = shapeType;
	}
	
	
	
}
