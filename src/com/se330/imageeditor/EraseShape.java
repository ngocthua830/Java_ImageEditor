package com.se330.imageeditor;

import java.awt.Shape;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public class EraseShape extends Rectangle2D{

	ArrayList<Shape> shapePoints = new ArrayList<Shape>();
	
	
	public ArrayList<Shape> getShapePoints() {
		return shapePoints;
	}

	public void setShapePoints(ArrayList<Shape> shapePoints) {
		this.shapePoints = shapePoints;
	}
	
	@Override
	public Rectangle2D createIntersection(Rectangle2D arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Rectangle2D createUnion(Rectangle2D arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int outcode(double arg0, double arg1) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setRect(double arg0, double arg1, double arg2, double arg3) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public double getHeight() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getWidth() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getX() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getY() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}
	
}
