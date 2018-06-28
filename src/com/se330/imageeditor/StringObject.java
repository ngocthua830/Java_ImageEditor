package com.se330.imageeditor;

import java.awt.Color;
import java.awt.Point;

public class StringObject extends ActionObject {
	String content = new String();
	Color color;
	int size = 10;
	
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public StringObject(String content, Point point, Color color, int size){
		this.content = content;
		this.startPoint = point;
		this.color = color;
		this.size = size;
	}
}
