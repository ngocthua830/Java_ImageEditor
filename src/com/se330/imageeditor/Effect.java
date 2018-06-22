package com.se330.imageeditor;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;

public class Effect {
	public Effect(){
		
	}
	public BufferedImage sharpen(BufferedImage paintImage){
        float[] sharpen = new float[] {
            0.0f, -1.0f, 0.0f,
            -1.0f, 5.0f, -1.0f,
            0.0f, -1.0f, 0.0f
        };
        Kernel kernel = new Kernel(3,3,sharpen);
        BufferedImageOp op = new ConvolveOp(kernel);
        BufferedImage sharpened = op.filter(paintImage, null);
        return sharpened;
	}
}
