package com.se330.imageeditor;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;

public class Effect {
	public Effect(){
		
	}
	public BufferedImageOp sharpen(){
        float[] sharpen = new float[] {
            0.0f, -1.0f, 0.0f,
            -1.0f, 5.0f, -1.0f,
            0.0f, -1.0f, 0.0f
        };
        Kernel kernel = new Kernel(3,3,sharpen);
        BufferedImageOp op = new ConvolveOp(kernel);
        return op;
	}
	public BufferedImageOp sobel(){
		float[] sobel = new float[] {
	            -1.0f, 0.0f, 1.0f,
	            -1.0f, 0.0f, 1.0f,
	            -1.0f, 0.0f, 1.0f
	    };
        Kernel kernel = new Kernel(3,3,sobel);
        BufferedImageOp op = new ConvolveOp(kernel);
        return op;
	}
	public BufferedImageOp gaussian(){
		float[] gaussian = new float[] {
				1/16f, 1/8f, 1/16f,
	            1/8f, 1/4f, 1/8f,
	            1/16f, 1/8f, 1/16f
	    };
        Kernel kernel = new Kernel(3,3,gaussian);
        BufferedImageOp op = new ConvolveOp(kernel);
        return op;
	}
	public BufferedImageOp laplacian1(){
		float[] laplacian1 = new float[] {
				0.0f, 1.0f, 0.0f,
	            1.0f, -4.0f, 1.0f,
	            0.0f, 1.0f, 0.0f
	    };
        Kernel kernel = new Kernel(3,3,laplacian1);
        BufferedImageOp op = new ConvolveOp(kernel);
        return op;
	}
	public BufferedImageOp laplacian2(){
		float[] laplacian1 = new float[] {
				1.0f, 1.0f, 1.0f,
	            1.0f, -8.0f, 1.0f,
	            1.0f, 1.0f, 1.0f
	    };
        Kernel kernel = new Kernel(3,3,laplacian1);
        BufferedImageOp op = new ConvolveOp(kernel);
        return op;
	}
	public BufferedImageOp laplacian3(){
		float[] laplacian1 = new float[] {
				-0.5f, 2.0f, -0.5f,
	            2.0f, -6.0f, 2.0f,
	            -0.5f, 2.0f, -0.5f
	    };
        Kernel kernel = new Kernel(3,3,laplacian1);
        BufferedImageOp op = new ConvolveOp(kernel);
        return op;
	}
}
