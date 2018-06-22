package com.se330.imageeditor;

import java.awt.Dimension;

import javax.swing.JFrame;

public class About extends JFrame{
	public About(){
		this.setMinimumSize(new Dimension(100, 100));
		this.setMaximumSize(new Dimension(100, 100));
		this.setTitle("About");
		this.setSize(300, 300);
		this.setVisible(true);
	}
}
