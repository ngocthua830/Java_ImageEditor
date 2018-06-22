package com.se330.imageeditor;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Robot;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.filechooser.FileFilter;

public class DrawArea extends JComponent{
		private static int PREF_W = 700;
		private static int PREF_H = 500;
		private static int old_PREF_W = 700;
		private static int old_PREF_H = 500;
		
		ArrayList<ActionObject> actionList = new ArrayList<ActionObject>();
		ArrayList<ActionObject> redoList = new ArrayList<ActionObject>();
		private BufferedImage paintImage, paintImage_tmp;
		
		private Graphics2D graph;
		
		int Control = 2;
		int strokeSize = 1;
		int zoomSize = 1;
		boolean fillMode = false;
		Color strokeColor = Color.black;
		Color fillColor = Color.white;
		Point startPoint = new Point();
		Point endPoint = new Point();
		
		String filePath = "";
		Effect effect = new Effect();
		
		
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
		
		public int getZoomSize() {
			return zoomSize;
		}
		public void setZoomSize(int zoomSize) {
			this.zoomSize = zoomSize;
		}
		
		public static int getOld_PREF_W() {
			return old_PREF_W;
		}
		public static void setOld_PREF_W(int old_PREF_W) {
			DrawArea.old_PREF_W = old_PREF_W;
		}
		public static int getOld_PREF_H() {
			return old_PREF_H;
		}
		public static void setOld_PREF_H(int old_PREF_H) {
			DrawArea.old_PREF_H = old_PREF_H;
		}
		
		public String getFilePath() {
			return filePath;
		}
		public void setFilePath(String filePath) {
			this.filePath = filePath;
		}
		public DrawArea(ImagePaint imagePaint){
			final ImagePaint imagepaint = (ImagePaint)imagePaint;
			addMouseListener(new MouseAdapter(){
				public void mousePressed(MouseEvent arg0) {
					Shape aShape = null;
					if (Control == 1){
						aShape = new BrushShape();
						actionList.add(new ShapeObject("brush", aShape, strokeSize, strokeColor, true, strokeColor, startPoint, endPoint));
						redoList.clear();
					}else if (Control == 5){
						aShape = new PencilShape();
						actionList.add(new ShapeObject("pencil", aShape, strokeSize, strokeColor, false, fillColor, startPoint, endPoint));
						redoList.clear();
					}else if (Control == 6){
						aShape = new EraseShape();
						actionList.add(new ShapeObject("erase", aShape, strokeSize, fillColor, true, fillColor, startPoint, endPoint));
						redoList.clear();
					}else if (Control == 10){
						try{
							Point screenPoint = arg0.getLocationOnScreen();
							Robot rb=new Robot();
							strokeColor = rb.getPixelColor(screenPoint.x/zoomSize, screenPoint.y/zoomSize);
							imagepaint.setStrokeBtnColor(strokeColor);
						}catch(Exception ex){
							
						}
					}
					startPoint = new Point(arg0.getX()/zoomSize, arg0.getY()/zoomSize);
					endPoint = startPoint;
				}
				public void mouseReleased(MouseEvent arg0) {
					endPoint = new Point(arg0.getX()/zoomSize, arg0.getY()/zoomSize);
					Shape aShape = null;
					if (Control == 1){
						aShape = drawBrush(startPoint.x, startPoint.y, strokeSize*5, strokeSize*5);			
						BrushShape brushShape = new BrushShape();
						brushShape.getShapePoints().add(aShape);
						actionList.add(new ShapeObject("brush", brushShape, strokeSize, strokeColor, true, strokeColor, startPoint, endPoint));
					}else if (Control == 5){
						aShape = drawLine(startPoint.x, startPoint.y, endPoint.x, endPoint.y);
						PencilShape pencilShape = new PencilShape();
						pencilShape.getShapePoints().add(aShape);
						actionList.add(new ShapeObject("pencil", pencilShape, strokeSize, strokeColor, false, fillColor, startPoint, endPoint));
					}else if (Control == 2){
						aShape = drawLine(startPoint.x, startPoint.y, endPoint.x, endPoint.y);
						actionList.add(new ShapeObject("line", aShape, strokeSize, strokeColor, false, fillColor, startPoint, endPoint));
						redoList.clear();
					}else if (Control == 3){
						aShape = drawRectangle(startPoint.x, startPoint.y, endPoint.x, endPoint.y);
						actionList.add(new ShapeObject("rectangle", aShape, strokeSize, strokeColor, fillMode, fillColor, startPoint, endPoint));
						redoList.clear();
					}else if (Control == 4){
						aShape = drawEllipse(startPoint.x, startPoint.y, endPoint.x, endPoint.y);
						actionList.add(new ShapeObject("ellipse", aShape, strokeSize, strokeColor, fillMode, fillColor, startPoint, endPoint));
						redoList.clear();
					}else if (Control == 6){
						aShape = drawRectangle(endPoint.x-strokeSize*3, endPoint.y-strokeSize*3, endPoint.x+strokeSize*3, endPoint.y+strokeSize*3);
						EraseShape eraseShape = new EraseShape();
						eraseShape.getShapePoints().add(aShape);
						actionList.add(new ShapeObject("erase", eraseShape, strokeSize, fillColor, true, fillColor, startPoint, endPoint));
					}else if (Control == 7){
						
						
					}
					repaint();
					startPoint = null;
					endPoint = null;
				}
			});
			
			
			addMouseMotionListener(new MouseMotionAdapter(){
				public void mouseDragged(MouseEvent arg0) {
					Shape aShape = null;
					endPoint = new Point(arg0.getX()/zoomSize, arg0.getY()/zoomSize);
					if (Control == 1){
						aShape = drawBrush(endPoint.x, endPoint.y, strokeSize*5, strokeSize*5);			
						BrushShape shape = (BrushShape) ((ShapeObject)actionList.get(actionList.size()-1)).getShape();
						(shape.getShapePoints()).add(aShape);
					}else if (Control == 5){
						aShape = drawLine(startPoint.x, startPoint.y, endPoint.x, endPoint.y);
						PencilShape shape = (PencilShape) ((ShapeObject)actionList.get(actionList.size()-1)).getShape();
						(shape.getShapePoints()).add(aShape);
						startPoint.x = arg0.getX()/zoomSize;
						startPoint.y = arg0.getY()/zoomSize;
					}else if (Control == 6){
						endPoint = new Point(arg0.getX(), arg0.getY());
						aShape = drawRectangle(endPoint.x-strokeSize*3, endPoint.y-strokeSize*3, endPoint.x+strokeSize*3, endPoint.y+strokeSize*3);
						EraseShape shape = (EraseShape) ((ShapeObject)actionList.get(actionList.size()-1)).getShape();
						(shape.getShapePoints()).add(aShape);
					}
					imagepaint.setboxSizeLabel(new Point(arg0.getX()-startPoint.x, arg0.getY()-startPoint.y));
					imagepaint.setLabelCoordinate(new Point(arg0.getX()/zoomSize, arg0.getY()/zoomSize));
					repaint();
				}

				public void mouseMoved(MouseEvent arg0) {
					imagepaint.setLabelCoordinate(new Point(arg0.getX()/zoomSize, arg0.getY()/zoomSize));
				}
				
				
			});
			
			addMouseWheelListener(new MouseWheelListener() {
				
				public void mouseWheelMoved(MouseWheelEvent arg0) {
					if (arg0.getWheelRotation() > 0){
						if (zoomSize > 1){
							zoomSize = zoomSize-1;
						}
						
					}else{
						zoomSize = zoomSize+1;
					}
					repaint();
					PREF_W = old_PREF_W*zoomSize;
					PREF_H = old_PREF_H*zoomSize;
					Size(PREF_W, PREF_H);
					imagepaint.scrollpane.revalidate();
					imagepaint.scrollpane.repaint();
					
				}
			});
			
			this.setFocusable(true);
			InputMap im = getInputMap(WHEN_FOCUSED);
			ActionMap am = getActionMap();
			
			im.put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0), "onEnter");
			
			Action up = new SimpleAction("up");
			am.put("onEnter",  up);
			
			
		}
		
		public void paint(Graphics g){
			graph = (Graphics2D) g;
			AffineTransform at = new AffineTransform();
	        at.scale(zoomSize, zoomSize);
	        graph.transform(at);
			if (paintImage == null){
				newImage(900, 700);
			}
			drawObject(graph);
			if (startPoint != null){
				graph.setStroke(new BasicStroke(strokeSize));
				Shape ashape = null;
				if (Control == 2 || Control == 3 || Control == 4||Control == 9){
					if (Control == 2){
						ashape = drawLine(startPoint.x, startPoint.y, endPoint.x, endPoint.y);
					}else if (Control == 3){
						ashape = drawRectangle(startPoint.x, startPoint.y, endPoint.x, endPoint.y);
					}else if (Control == 4){
						ashape = drawEllipse(startPoint.x, startPoint.y, endPoint.x, endPoint.y);
					}else if (Control == 9){
						ashape = drawRectangle(startPoint.x, startPoint.y, endPoint.x, endPoint.y);
					}
					if (fillMode == true){
						graph.setPaint(fillColor);
						graph.fill(ashape);
					}
					graph.setPaint(strokeColor);
					if (Control == 9){
						graph.setStroke(new BasicStroke(1));
						graph.setPaint(new Color(200,200,200));
					}
					graph.draw(ashape);
					//graph.drawImage(paintImage, 0, 0, 100, 100, this);
				}else{
					
				}
			}
		}
		
		private void drawObject(Graphics2D g){
			paintImage_tmp = paintImage;
			g.drawImage(paintImage_tmp, 0, 0, null);
			if (actionList != null){
				for (ActionObject Action : actionList){
					if (Action instanceof ShapeObject){
						ShapeObject action = (ShapeObject)Action;
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
					}else if (Action instanceof EffectObject){
						EffectObject action = (EffectObject)Action;
						if (action.getEffectType() == "sharpen"){
							paintImage_tmp = effect.sharpen(paintImage_tmp);
							
						}
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
			old_PREF_H = PREF_H;
			old_PREF_W = PREF_W;
			paintImage = new BufferedImage(x, y, BufferedImage.TYPE_3BYTE_BGR);
			this.setSize(x, y);
			Graphics2D tmp_g = paintImage.createGraphics();
			tmp_g.setPaint(Color.white);
			tmp_g.fillRect(0, 0, PREF_W, PREF_H);
		}
		public void saveImage(String path, FileFilter type){
			BufferedImage tmp_paintImage = new BufferedImage(PREF_W, PREF_H, BufferedImage.TYPE_3BYTE_BGR);
			Graphics2D tmp_g = tmp_paintImage.createGraphics();
			drawObject(tmp_g);
			try{
				if (type.getDescription() == "PNG Image"){
					ImageIO.write(tmp_paintImage, "PNG", new File(path+".png"));
				}else if (type.getDescription() == "JPEG Image"){
					ImageIO.write(tmp_paintImage, "JPEG", new File(path+".jpg"));
				}else if (type.getDescription() == "BMP Image"){
					ImageIO.write(tmp_paintImage, "BMP", new File(path+".bmp"));
				}
				//ImageIO.write(tmp_paintImage, "PNG", new File("filename.png"));
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
				old_PREF_H = PREF_H;
				old_PREF_W = PREF_W;
				this.setSize(PREF_W, PREF_H);
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
		private void Size(int w, int h){
			this.setSize(new Dimension(w, h));
		}
		
		public void sharpen(){
			EffectObject effectObject = new EffectObject("sharpen", true, null, null);
			actionList.add(effectObject);
			repaint();
		}
		public void blur(){
			
		}
		
		@Override
		public Dimension getPreferredSize() {
			return new Dimension(PREF_W, PREF_H);
		}
}
