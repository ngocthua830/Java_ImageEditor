package com.se330.imageeditor;

import java.awt.Dimension;
import java.awt.GridBagLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class About extends JFrame{
	public About(){
		JLabel authorLabel1 =new JLabel();  
		authorLabel1.setText("<html><center><h1>Image Editor v1.3</h1><br><br><h2>Author:</h2><br>Nguyen Ngoc Thua<br>Nguyen Minh Vu</center></html>");
	    authorLabel1.setBounds(50,100, 250,20);  
	    this.setLayout(new GridBagLayout());
	    this.add(authorLabel1);
		this.setMinimumSize(new Dimension(100, 100));
		this.setMaximumSize(new Dimension(100, 100));
		this.setTitle("About");
		this.setSize(500, 300);
		this.setVisible(true);
	}
}
