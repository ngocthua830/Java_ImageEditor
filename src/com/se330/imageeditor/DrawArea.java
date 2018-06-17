package com.se330.imageeditor;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Shape;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.swing.JComponent;

public class DrawArea extends JComponent{
		private static int PREF_W = 700;
		private static int PREF_H = 500;
		
		ArrayList<ActionObject> actionList = new ArrayList<ActionObject>();
		ArrayList<ActionObject> redoList = new ArrayList<ActionObject>();
		private BufferedImage paintImage;
		
		private Graphics2D graph;
		
		int Control = 2;
		int strokeSize = 1;
		boolean fillMode = false;
		Color strokeColor = Color.black;
		Color fillColor = Color.white;
		Point startPoint = new Point();
		Point endPoint = new Point();
		
		
		public int getControl() {
			return Control;
		}
		public void setControl(int control) {
			Control = control;
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
		public Color getFillColor() {
			return fillColor;
		}
		public void setFillColor(Color fillColor) {
			this.fillColor = fillColor;
		}
		
		public static int getPREF_W() {
			return PREF_W;
		}
		public static void setPREF_W(int pREF_W) {
			PREF_W = pREF_W;
		}
		public static int getPREF_H() {
			return PREF_H;
		}
		public static void setPREF_H(int pREF_H) {
			PREF_H = pREF_H;
		}
		
		public boolean isFillMode() {
			return fillMode;
		}
		public void setFillMode(boolean fillMode) {
			this.fillMode = fillMode;
		}
		public DrawArea(){
			addMouseListener(new MouseAdapter(){
				public void mousePressed(MouseEvent arg0) {
					Shape aShape = null;
					if (Control == 1){
						aShape = new BrushShape();
						actionList.add(new ActionObject("shape", "brush", aShape, strokeSize, strokeColor, true, strokeColor, startPoint, endPoint, "none"));
						redoList.clear();
					}else if (Control == 5){
						aShape = new PencilShape();
						actionList.add(new ActionObject("shape", "pencil", aShape, strokeSize, strokeColor, false, fillColor, startPoint, endPoint, "none"));
						redoList.clear();
					}else if (Control == 6){
						aShape = new EraseShape();
						actionList.add(new ActionObject("shape", "erase", aShape, strokeSize, fillColor, true, fillColor, startPoint, endPoint, "none"));
						redoList.clear();
					}
					startPoint = new Point(arg0.getX(), arg0.getY());
					endPoint = startPoint;
				}
				public void mouseReleased(MouseEvent arg0) {
					endPoint = new Point(arg0.getX(), arg0.getY());
					Shape aShape = null;
					if (Control == 1){
						aShape = drawBrush(arg0.getX(), arg0.getY(), strokeSize*5, strokeSize*5);			
						BrushShape brushShape = new BrushShape();
						brushShape.getShapePoints().add(aShape);
						actionList.add(new ActionObject("shape", "brush", brushShape, strokeSize, strokeColor, true, strokeColor, startPoint, endPoint, "none"));
					}else if (Control == 5){
						aShape = drawLine(startPoint.x, startPoint.y, arg0.getX(), arg0.getY());
						PencilShape pencilShape = new PencilShape();
						pencilShape.getShapePoints().add(aShape);
						actionList.add(new ActionObject("shape", "pencil", pencilShape, strokeSize, strokeColor, false, fillColor, startPoint, endPoint, "none"));
					}else if (Control == 2){
						aShape = drawLine(startPoint.x, startPoint.y, arg0.getX(), arg0.getY());
						actionList.add(new ActionObject("shape", "line", aShape, strokeSize, strokeColor, false, fillColor, startPoint, endPoint, "none"));
						redoList.clear();
					}else if (Control == 3){
						aShape = drawRectangle(startPoint.x, startPoint.y, endPoint.x, endPoint.y);
						actionList.add(new ActionObject("shape", "rectangle", aShape, strokeSize, strokeColor, fillMode, fillColor, startPoint, endPoint, "none"));
						redoList.clear();
					}else if (Control == 4){
						aShape = drawEllipse(startPoint.x, startPoint.y, endPoint.x, endPoint.y);
						actionList.add(new ActionObject("shape", "ellipse", aShape, strokeSize, strokeColor, fillMode, fillColor, startPoint, endPoint, "none"));
						redoList.clear();
					}else if (Control == 6){
						aShape = drawRectangle(endPoint.x-strokeSize*3, endPoint.y-strokeSize*3, endPoint.x+strokeSize*3, endPoint.y+strokeSize*3);
						EraseShape eraseShape = new EraseShape();
						eraseShape.getShapePoints().add(aShape);
						actionList.add(new ActionObject("shape", "erase", eraseShape, strokeSize, fillColor, true, fillColor, startPoint, endPoint, "none"));
					}else if (Control == 7){
						EraseShape eraseShape = new EraseShape();
						actionList.add(new ActionObject("string", "none", eraseShape, strokeSize, fillColor, true, fillColor, startPoint, endPoint, "none"));
					}
					repaint();
					startPoint = null;
					endPoint = null;
				}
			});
			
			
			addMouseMotionListener(new MouseMotionAdapter(){
				public void mouseDragged(MouseEvent arg0) {
					Shape aShape = null;
					if (Control == 1){
						aShape = drawBrush(arg0.getX(), arg0.getY(), strokeSize*5, strokeSize*5);			
						BrushShape shape = (BrushShape) actionList.get(actionList.size()-1).getShape();
						(shape.getShapePoints()).add(aShape);
					}else if (Control == 5){
						aShape = drawLine(startPoint.x, startPoint.y, arg0.getX(), arg0.getY());
						PencilShape shape = (PencilShape) actionList.get(actionList.size()-1).getShape();
						(shape.getShapePoints()).add(aShape);
						startPoint.x = arg0.getX();
						startPoint.y = arg0.getY();
					}else if (Control == 6){
						endPoint = new Point(arg0.getX(), arg0.getY());
						aShape = drawRectangle(endPoint.x-strokeSize*3, endPoint.y-strokeSize*3, endPoint.x+strokeSize*3, endPoint.y+strokeSize*3);
						EraseShape shape = (EraseShape) actionList.get(actionList.size()-1).getShape();
						(shape.getShapePoints()).add(aShape);
					}
					endPoint = new Point(arg0.getX(), arg0.getY());
					repaint();
				}

				public void mouseMoved(MouseEvent arg0) {
					//endPoint = new Point(arg0.getX(), arg0.getY());
				}
				
				
			});
		}
		
		public void paint(Graphics g){
			graph = (Graphics2D) g;
			if (paintImage == null){
				newImage(500, 500);
			}
			drawObject(graph);
			if (startPoint != null){
				graph.setStroke(new BasicStroke(strokeSize));
				Shape ashape = null;
				if (Control == 2 || Control == 3 || Control == 4){
					if (Control == 2){
						ashape = drawLine(startPoint.x, startPoint.y, endPoint.x, endPoint.y);
					}else if (Control == 3){
						ashape = drawRectangle(startPoint.x, startPoint.y, endPoint.x, endPoint.y);
					}else if (Control == 4){
						ashape = drawEllipse(startPoint.x, startPoint.y, endPoint.x, endPoint.y);
					}
					if (fillMode == true){
						graph.setPaint(fillColor);
						graph.fill(ashape);
					}
					graph.setPaint(strokeColor);
					graph.draw(ashape);
				}else{
					
				}
			}
		}
		
		private void drawObject(Graphics2D g){
			
			g.drawImage(paintImage, 0, 0, null);
			if (actionList != null){
				for (ActionObject action : actionList){
					if (action.getType() == "shape"){
						if (action.getShapeType() == "brush"){
							BrushShape shape = (BrushShape)action.getShape();
							for (Shape shapePoint : shape.getShapePoints()){
								g.setStroke(new BasicStroke(action.getStrokeSize()));
								g.setPaint(action.getStrokeColor());
								g.draw(shapePoint);
								g.setPaint(action.getFillColor());
								if (action.isFillMode()){
									g.fill(shapePoint);
								}
							}
						}else if (action.getShapeType() == "pencil"){
							PencilShape shape = (PencilShape)action.getShape();
							for (Shape shapePoint : shape.getShapePoints()){
								g.setStroke(new BasicStroke(action.getStrokeSize()));
								g.setPaint(action.getStrokeColor());
								g.draw(shapePoint);
								g.setPaint(action.getFillColor());
								if (action.isFillMode()){
									g.fill(shapePoint);
								}
							}
						}else if (action.getShapeType() == "erase"){
							EraseShape shape = (EraseShape)action.getShape();
							for (Shape shapePoint : shape.getShapePoints()){
								g.setStroke(new BasicStroke(action.getStrokeSize()));
								g.setPaint(action.getStrokeColor());
								g.draw(shapePoint);
								g.setPaint(action.getFillColor());
								if (action.isFillMode()){
									g.fill(shapePoint);
								}
							}
						}else{
							g.setStroke(new BasicStroke(action.getStrokeSize()));
							if (action.isFillMode()){
								g.setPaint(action.getFillColor());
								g.fill(action.getShape());
							}
							g.setPaint(action.getStrokeColor());
							g.draw(action.getShape());
						}
					}else if (action.getType() == "string"){
						g.setFont(g.getFont().deriveFont(30f));
					    g.drawString("Hello World!", 100, 100);
					}

				}
			}
		}
		public void clear() {
			graph.setPaint(Color.white);
			graph.fillRect(0, 0, PREF_W, PREF_H);
			graph.setPaint(Color.black);
			repaint();
		}
		public void undo(){
			if (actionList.size() > 0){
				redoList.add(actionList.get(actionList.size()-1));
				actionList.remove(actionList.size()-1);
				repaint();
			}
		}
		public void redo(){
			if (redoList.size() > 0){
				actionList.add(redoList.get(redoList.size()-1));
				redoList.remove(redoList.size()-1);
				repaint();
			}
		}
		public void newImage(int x, int y){
			actionList.clear();
			redoList.clear();
			PREF_H = y;
			PREF_W = x;
			paintImage = new BufferedImage(x, y, BufferedImage.TYPE_3BYTE_BGR);
			Graphics2D tmp_g = paintImage.createGraphics();
			tmp_g.setPaint(Color.white);
			tmp_g.fillRect(0, 0, PREF_W, PREF_H);
		}
		public void saveImage(String path){
			BufferedImage tmp_paintImage = new BufferedImage(PREF_W, PREF_H, BufferedImage.TYPE_3BYTE_BGR);
			Graphics2D tmp_g = tmp_paintImage.createGraphics();
			drawObject(tmp_g);
			try{
				//ImageIO.write(paintImage1, "PNG", new File(path+".png"));
				ImageIO.write(tmp_paintImage, "PNG", new File("filename.png"));
			}catch(Exception ex){
				
			}
		}
		public void openImage(String str){
	        try{
	        	paintImage = ImageIO.read(new File(str));
	        	actionList.clear();
				redoList.clear();
	        	PREF_H = paintImage.getHeight();
	    		PREF_W = paintImage.getWidth();
	            repaint();
	        }catch (Exception ex){
	            System.out.print("Load error");
	        }
		}
		private Line2D.Float drawLine(int x1, int y1, int x2, int y2){
			return new Line2D.Float(x1,y1,x2,y2);
		}
		private Rectangle2D.Float drawRectangle(int x1, int y1, int x2, int y2){
			int x = Math.min(x1, x2);
			int y = Math.min(y1, y2);
			
			int width = Math.abs(x1-x2);
			int height = Math.abs(y1-y2);
			
			return new Rectangle2D.Float(x, y, width, height);
		}
		private Ellipse2D.Float drawEllipse(int x1, int y1, int x2, int y2){
			int x = Math.min(x1, x2);
			int y = Math.min(y1, y2);
			int width = Math.abs(x1-x2);
			int height = Math.abs(y1-y2);
			return new Ellipse2D.Float(x, y, width, height);
		}
		private Ellipse2D.Float drawBrush(int x1, int y1, int brushStrokeWidth, int brushStrokeHeight){
			return new Ellipse2D.Float(x1, y1, brushStrokeWidth, brushStrokeHeight);
		}
		@Override
		public Dimension getPreferredSize() {
			return new Dimension(PREF_W, PREF_H);
		}
}
