package com.se330.imageeditor;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Robot;
import java.awt.Shape;
import java.awt.color.ColorSpace;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ColorConvertOp;
import java.awt.image.ColorModel;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JTextField;
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
		
		private Graphics2D graph, graph_tmp;
		
		int Control = 2;
		int strokeSize = 1;
		float zoomSize = 1;
		boolean fillMode = false;
		Color strokeColor = Color.black;
		Color fillColor = Color.white;
		Point startPoint = new Point();
		Point endPoint = new Point();
		Point roiStartPoint = new Point();
		Point roiEndPoint = new Point();
		
		String filePath = "";
		Effect effect = new Effect();
		
		String stringToDraw = "";	// string that is drawing
		Point stringPoint = new Point(); // coordiante of string drawing
		int stringSize = 15;
		
		///////////////////////////////////////SETTER AND GETTER/////////////////////////
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
		
		public float getZoomSize() {
			return zoomSize;
		}
		public void setZoomSize(float zoomSize) {
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
		
		public String getStringToDraw() {
			return stringToDraw;
		}
		public void setStringToDraw(String stringToDraw) {
			this.stringToDraw = stringToDraw;
		}
		public Point getStringPoint() {
			return stringPoint;
		}
		public void setStringPoint(Point stringPoint) {
			this.stringPoint = stringPoint;
		}
		
		public int getStringSize() {
			return stringSize;
		}
		public void setStringSize(int stringSize) {
			this.stringSize = stringSize;
		}
		//////////////////////////////////////////////////////////////////////////////////////////////////
		public DrawArea(ImagePaint imagePaint){
			final DrawArea drawArea = this;
			final ImagePaint imagepaint = (ImagePaint)imagePaint;
			
			///////////////////////Add Text Panel//////////////////////////
			final JTextField textField = new JTextField(10);
			textField.addKeyListener(new KeyListener() {
				
				public void keyTyped(KeyEvent arg0) {
					if (textField.getText() != ""){
						drawArea.setStringToDraw(textField.getText());
						drawArea.setStringPoint(startPoint);
						drawArea.repaint();
					}
				}
				
				public void keyReleased(KeyEvent arg0) {
					if (textField.getText() != ""){
						drawArea.setStringToDraw(textField.getText());
						drawArea.setStringPoint(startPoint);
						drawArea.repaint();
					}
					
				}
				
				public void keyPressed(KeyEvent arg0) {
					if (textField.getText() != ""){
						drawArea.setStringToDraw(textField.getText());
						drawArea.setStringPoint(startPoint);
						drawArea.repaint();
					}
					
				}
			});
			Object[] items =
		        {
					1,
					2,
					3,
					4,
					5,
					6,
					7,
					8,
					9,
					10,
					11,
					12,
					13,
					14,
					15,
					20,
					25,
					30,
					35,
					40,
					45,
					50,
					60,
					70,
					100,
					150,
					200
					
				};
			final JComboBox stringSizeCbb = new JComboBox( items );
			stringSizeCbb.setToolTipText("stroke size");
			stringSizeCbb.setSelectedIndex(14);
			stringSizeCbb.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){
					stringSize = Integer.parseInt(stringSizeCbb.getSelectedItem().toString());
					drawArea.repaint();
				}
			});
			JLabel stringFormLabelSize = new JLabel("Size: ");
			final JPanel addStringFormPanel = new JPanel();
			addStringFormPanel.add(textField);
			addStringFormPanel.add(stringFormLabelSize);
			addStringFormPanel.add(stringSizeCbb);
			
			///////////////////////END Add Text Panel////////////////////////
			
			addMouseListener(new MouseAdapter(){
				public void mousePressed(MouseEvent arg0) {
					startPoint = new Point((int)(arg0.getX()/zoomSize), (int)(arg0.getY()/zoomSize));
					endPoint = startPoint;
					graph_tmp = (Graphics2D)paintImage_tmp.getGraphics();
					Shape aShape = null;
					if (Control == 9){
						roiStartPoint = new Point((int)(arg0.getX()/zoomSize), (int)(arg0.getY()/zoomSize));
						roiEndPoint = null;
					}else{
						roiEndPoint = null;
						roiStartPoint = null;
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
								strokeColor = rb.getPixelColor((int)(screenPoint.x/zoomSize), (int)(screenPoint.y/zoomSize));
								imagepaint.setStrokeBtnColor(strokeColor);
							}catch(Exception ex){
								
							}
						}else if (Control == 7){//String button
							
							textField.setText("");
							int result = JOptionPane.showConfirmDialog(null, addStringFormPanel, "Add Text", JOptionPane.OK_CANCEL_OPTION);
							if (result == JOptionPane.OK_OPTION){
								drawArea.setStringToDraw("");
								drawArea.setStringPoint(null);
								drawArea.addString(textField.getText(), startPoint);
								drawArea.repaint();
							}else{
								drawArea.setStringToDraw("");
								drawArea.setStringPoint(null);
								drawArea.repaint();
							}
						}
					}
					
				}
				public void mouseReleased(MouseEvent arg0) {
					endPoint = new Point((int)(arg0.getX()/zoomSize), (int)(arg0.getY()/zoomSize));
					Shape aShape = null;
					graph_tmp.setStroke(new BasicStroke(strokeSize));
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
						graph_tmp.setPaint(strokeColor);
						graph_tmp.draw(aShape);
						redoList.clear();
					}else if (Control == 3){
						aShape = drawRectangle(startPoint.x, startPoint.y, endPoint.x, endPoint.y);
						actionList.add(new ShapeObject("rectangle", aShape, strokeSize, strokeColor, fillMode, fillColor, startPoint, endPoint));
						if (fillMode == true){
							graph_tmp.setPaint(fillColor);
							graph_tmp.fill(aShape);
						}
						graph_tmp.setPaint(strokeColor);
						graph_tmp.draw(aShape);
						redoList.clear();
					}else if (Control == 4){
						aShape = drawEllipse(startPoint.x, startPoint.y, endPoint.x, endPoint.y);
						actionList.add(new ShapeObject("ellipse", aShape, strokeSize, strokeColor, fillMode, fillColor, startPoint, endPoint));
						if (fillMode == true){
							graph_tmp.setPaint(fillColor);
							graph_tmp.fill(aShape);
						}
						graph_tmp.setPaint(strokeColor);
						graph_tmp.draw(aShape);
						redoList.clear();
					}else if (Control == 6){
						aShape = drawRectangle(endPoint.x-strokeSize*3, endPoint.y-strokeSize*3, endPoint.x+strokeSize*3, endPoint.y+strokeSize*3);
						EraseShape eraseShape = new EraseShape();
						eraseShape.getShapePoints().add(aShape);
						actionList.add(new ShapeObject("erase", eraseShape, strokeSize, fillColor, true, fillColor, startPoint, endPoint));
					}else if (Control == 7){ //String button
						
					}
					repaint();
					startPoint = null;
					endPoint = null;
				}
			});
			
			
			addMouseMotionListener(new MouseMotionAdapter(){
				public void mouseDragged(MouseEvent arg0) {
					Shape aShape = null;
					endPoint = new Point((int)(arg0.getX()/zoomSize), (int)(arg0.getY()/zoomSize));
					if (Control == 1){
						aShape = drawBrush(endPoint.x, endPoint.y, strokeSize*5, strokeSize*5);			
						BrushShape shape = (BrushShape) ((ShapeObject)actionList.get(actionList.size()-1)).getShape();
						(shape.getShapePoints()).add(aShape);
					}else if (Control == 5){
						aShape = drawLine(startPoint.x, startPoint.y, endPoint.x, endPoint.y);
						PencilShape shape = (PencilShape) ((ShapeObject)actionList.get(actionList.size()-1)).getShape();
						(shape.getShapePoints()).add(aShape);
						startPoint = new Point((int)(arg0.getX()/zoomSize), (int)(arg0.getY()/zoomSize));
					}else if (Control == 6){
						endPoint = new Point(arg0.getX(), arg0.getY());
						aShape = drawRectangle(endPoint.x-strokeSize*3, endPoint.y-strokeSize*3, endPoint.x+strokeSize*3, endPoint.y+strokeSize*3);
						EraseShape shape = (EraseShape) ((ShapeObject)actionList.get(actionList.size()-1)).getShape();
						(shape.getShapePoints()).add(aShape);
					}else if (Control == 9){
						roiEndPoint = new Point((int)(arg0.getX()/zoomSize), (int)(arg0.getY()/zoomSize));
						repaint();
					}
					imagepaint.setboxSizeLabel(new Point(arg0.getX()-startPoint.x, arg0.getY()-startPoint.y));
					imagepaint.setLabelCoordinate(new Point((int)(arg0.getX()/zoomSize), (int)(arg0.getY()/zoomSize)));
					repaint();
				}

				public void mouseMoved(MouseEvent arg0) {
					if (zoomSize > 1){
						imagepaint.setLabelCoordinate(new Point((int)(arg0.getX()/zoomSize), (int)(arg0.getY()/zoomSize)));
					}else{
						imagepaint.setLabelCoordinate(new Point((int)(arg0.getX()), (int)(arg0.getY())));
					}
				}
				
				
			});
			
			addMouseWheelListener(new MouseWheelListener() {
				
				public void mouseWheelMoved(MouseWheelEvent arg0) {
					if (arg0.isControlDown()){
						if (arg0.getWheelRotation() >= 1){
							if (zoomSize > 1){
								zoomSize = zoomSize-1;
								PREF_W = (int)(old_PREF_W*zoomSize);
								PREF_H = (int)(old_PREF_H*zoomSize);
							}else if (zoomSize <= 1 && zoomSize >= 0.5){
								zoomSize = zoomSize - 0.1f;
								PREF_W = (int)(old_PREF_W*zoomSize);
								PREF_H = (int)(old_PREF_H*zoomSize);
							}
							
						}else if (arg0.getWheelRotation() < 1){
							if (zoomSize >= 1){
								zoomSize = zoomSize+1;
								PREF_W = (int)(old_PREF_W*zoomSize);
								PREF_H = (int)(old_PREF_H*zoomSize);
							}else if (zoomSize < 1){
								zoomSize = zoomSize + 0.1f;
								PREF_W = (int)(old_PREF_W*zoomSize);
								PREF_H = (int)(old_PREF_H*zoomSize);
							}
						}
						repaint();
						
						Size(PREF_W, PREF_H);
						imagepaint.scrollpane.revalidate();
						imagepaint.scrollpane.repaint();
					}
					
				}
			});

		}
		
		public void paint(Graphics g){
			graph = (Graphics2D) g;
			AffineTransform at = new AffineTransform();
	        at.scale(zoomSize, zoomSize);
	        graph.transform(at);
			if (paintImage == null){
				newImage(900, 700);
			}
			graph.drawImage(paintImage_tmp, 0, 0, null);
			if (stringToDraw != ""){
				graph.setPaint(strokeColor);
				graph.setFont(new Font("TimesRoman", Font.PLAIN, stringSize)); 
				graph.drawString(stringToDraw, stringPoint.x, stringPoint.y);
				
			}
			//drawObject(graph, paintImage_tmp);
			if (Control == 9 && roiEndPoint != null && roiStartPoint != null){
				graph.setStroke(new BasicStroke(1));
				graph.setPaint(new Color(200,200,200));
				graph.drawRect((int)roiStartPoint.getX(), (int)roiStartPoint.getY(), (int)roiEndPoint.getX(),(int) roiEndPoint.getY());
			}
			if (startPoint != null && Control != 9){
				graph.setStroke(new BasicStroke(strokeSize));
				Shape ashape = null;
				if (Control == 2 || Control == 3 || Control == 4||Control == 9){
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
					//graph.drawImage(paintImage, 0, 0, 100, 100, this);
				}else{
					if (Control == 5){
						PencilShape shape = (PencilShape)((ShapeObject)(actionList.get(actionList.size()-1))).getShape();
						for (Shape shapePoint : shape.getShapePoints()){
							graph_tmp.setStroke(new BasicStroke(((ShapeObject)(actionList.get(actionList.size()-1))).getStrokeSize()));
							graph_tmp.setPaint(((ShapeObject)(actionList.get(actionList.size()-1))).getStrokeColor());
							graph_tmp.draw(shapePoint);
							graph_tmp.setPaint(((ShapeObject)(actionList.get(actionList.size()-1))).getFillColor());
							if (((ShapeObject)(actionList.get(actionList.size()-1))).isFillMode()){
								graph_tmp.fill(shapePoint);
							}
						}
					}else if (Control == 1){
						BrushShape shape = (BrushShape)((ShapeObject)(actionList.get(actionList.size()-1))).getShape();
						for (Shape shapePoint : shape.getShapePoints()){
							graph_tmp.setStroke(new BasicStroke(((ShapeObject)(actionList.get(actionList.size()-1))).getStrokeSize()));
							graph_tmp.setPaint(((ShapeObject)(actionList.get(actionList.size()-1))).getStrokeColor());
							graph_tmp.draw(shapePoint);
							graph_tmp.setPaint(((ShapeObject)(actionList.get(actionList.size()-1))).getFillColor());
							if (((ShapeObject)(actionList.get(actionList.size()-1))).isFillMode()){
								graph_tmp.fill(shapePoint);
							}
						}
					}else if (Control == 6){
						EraseShape shape = (EraseShape)((ShapeObject)(actionList.get(actionList.size()-1))).getShape();
						for (Shape shapePoint : shape.getShapePoints()){
							graph_tmp.setStroke(new BasicStroke(((ShapeObject)(actionList.get(actionList.size()-1))).getStrokeSize()));
							graph_tmp.setPaint(((ShapeObject)(actionList.get(actionList.size()-1))).getStrokeColor());
							graph_tmp.draw(shapePoint);
							graph_tmp.setPaint(((ShapeObject)(actionList.get(actionList.size()-1))).getFillColor());
							if (((ShapeObject)(actionList.get(actionList.size()-1))).isFillMode()){
								graph_tmp.fill(shapePoint);
							}
						}
					}
				}
			}
		}
		
		private void drawObject(Graphics2D g, BufferedImage image){
			//Graphics2D g = (Graphics2D) paintImage_tmp.getGraphics();

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
							BufferedImage tmp = deepCopy(image);
					        effect.sharpen().filter(tmp, image);
							
						}else if (action.getEffectType() == "sobel"){
							BufferedImage tmp = deepCopy(image);
					        effect.sobel().filter(tmp, image);
						}else if (action.getEffectType() == "gaussian"){
							BufferedImage tmp = deepCopy(image);
					        effect.gaussian().filter(tmp, image);
						}else if (action.getEffectType() == "resize"){
							int w =((ScaleEffect)((EffectObject)action)).getWidth();
							int h = ((ScaleEffect)((EffectObject)action)).getHeight();
							Image tmp = paintImage_tmp.getScaledInstance(w, h, Image.SCALE_SMOOTH);
							BufferedImage dimg = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
						    Graphics2D g2d = dimg.createGraphics();
						    g2d.drawImage(tmp, 0, 0, null);
						    g2d.dispose();
						    paintImage_tmp = deepCopy(dimg);
						    g = (Graphics2D)paintImage_tmp.getGraphics();
						    PREF_H = h;
				    		PREF_W = w;
							this.setSize(PREF_W, PREF_H);
							
						}else if (action.getEffectType() == "resizeCanvas"){
							int w =((ScaleEffect)((EffectObject)action)).getWidth();
							int h = ((ScaleEffect)((EffectObject)action)).getHeight();
							BufferedImage dimg = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
						    Graphics2D g2d = dimg.createGraphics();
						    g2d.drawImage(paintImage_tmp, 0, 0, null);
						    g2d.dispose();
						    paintImage_tmp = deepCopy(dimg);
						    g = (Graphics2D)paintImage_tmp.getGraphics();
						    PREF_H = h;
				    		PREF_W = w;
							this.setSize(PREF_W, PREF_H);
							
						}else if (action.getEffectType() == "invertColor"){
							invertImage(paintImage_tmp);
						}
						else if (action.getEffectType() == "blackAndWhite"){
							BufferedImage blackWhite = new BufferedImage(PREF_W, PREF_H, BufferedImage.TYPE_BYTE_BINARY);
			                Graphics2D g2d = blackWhite.createGraphics();
			                g2d.drawImage(image, 0, 0, this);
			                g2d.dispose();
			                paintImage_tmp = deepCopy(blackWhite);
						    g = (Graphics2D)paintImage_tmp.getGraphics();
						}else if (action.getEffectType() == "grayScale"){
							ColorConvertOp op = new ColorConvertOp(ColorSpace.getInstance(ColorSpace.CS_GRAY), null);
			                op.filter(image, image);
						}else if (action.getEffectType() == "laplacian1"){
							BufferedImage tmp = deepCopy(image);
					        effect.laplacian1().filter(tmp, image);
						}else if (action.getEffectType() == "laplacian2"){
							BufferedImage tmp = deepCopy(image);
					        effect.laplacian2().filter(tmp, image);
						}else if (action.getEffectType() == "laplacian3"){
							BufferedImage tmp = deepCopy(image);
					        effect.laplacian3().filter(tmp, image);
						}else if (action.getEffectType() == "paste"){
							
						}
						
						
					}else if (Action instanceof StringObject){
						StringObject action = (StringObject)Action;
						g.setStroke(new BasicStroke(action.getSize()));
						g.setFont(new Font("TimesRoman", Font.PLAIN, action.getSize())); 
						g.setPaint(action.getColor());
						g.drawString(action.getContent(), action.getStartPoint().x, action.getStartPoint().y);
					}
				}
			}
			//g.drawImage(image, 0, 0, null);
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
				paintImage_tmp = deepCopy(paintImage);
				PREF_H = old_PREF_H;
				PREF_W = old_PREF_W;
				this.setSize(PREF_W, PREF_H);
				graph_tmp = (Graphics2D) paintImage_tmp.getGraphics();
				drawObject(graph_tmp, paintImage_tmp);
				repaint();
				
			}
		}
		public void redo(){
			if (redoList.size() > 0){
				actionList.add(redoList.get(redoList.size()-1));
				redoList.remove(redoList.size()-1);
				paintImage_tmp = deepCopy(paintImage);
				graph_tmp = (Graphics2D) paintImage_tmp.getGraphics();
				drawObject(graph_tmp, paintImage_tmp);
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
			paintImage_tmp = deepCopy(paintImage);
			graph_tmp = (Graphics2D) paintImage_tmp.getGraphics();
		}
		public void saveImage(String path, FileFilter type){
			//BufferedImage tmp_paintImage = new BufferedImage(PREF_W, PREF_H, BufferedImage.TYPE_3BYTE_BGR);
			//Graphics2D tmp_g = tmp_paintImage.createGraphics();
			//drawObject(tmp_g, tmp_paintImage);
			try{
				if (type.getDescription() == "PNG Image"){
					ImageIO.write(paintImage_tmp, "PNG", new File(path+".png"));
				}else if (type.getDescription() == "JPEG Image"){
					ImageIO.write(paintImage_tmp, "JPEG", new File(path+".jpg"));
				}else if (type.getDescription() == "BMP Image"){
					ImageIO.write(paintImage_tmp, "BMP", new File(path+".bmp"));
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
				paintImage_tmp = deepCopy(paintImage);
				graph_tmp = (Graphics2D) paintImage_tmp.getGraphics();
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
			drawObject(graph_tmp, paintImage_tmp);
			repaint();
		}
		public void gaussian(){
			EffectObject effectObject = new EffectObject("gaussian", true, null, null);
			actionList.add(effectObject);
			drawObject(graph_tmp, paintImage_tmp);
			repaint();
		}
		public void sobel(){
			EffectObject effectObject = new EffectObject("sobel", true, null, null);
			actionList.add(effectObject);
			drawObject(graph_tmp, paintImage_tmp);
			repaint();
		}
		public void rotate(){
			AffineTransform tx = AffineTransform.getRotateInstance(Math.toRadians(90), paintImage_tmp.getWidth()/2.0f, paintImage_tmp.getHeight()/2.0f);
			AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
			//AffineTransform trans2 = new AffineTransform();
		    //trans2.translate(0, PREF_W-PREF_H);
			BufferedImage bi = new BufferedImage(PREF_W+Math.abs(PREF_H-PREF_W), PREF_H+Math.abs(PREF_H-PREF_W), BufferedImage.TYPE_INT_ARGB);
			BufferedImage bi2 = new BufferedImage(PREF_H, PREF_W, BufferedImage.TYPE_INT_ARGB);
			//graph_tmp.drawImage(paintImage_tmp, trans2, null);
			Graphics2D graph_bi = (Graphics2D)bi.getGraphics();
			graph_bi.drawImage(paintImage_tmp, Math.abs(PREF_H-PREF_W), Math.abs(PREF_H-PREF_W), null);
			bi2 = op.filter(bi, bi2);
			paintImage_tmp = deepCopy(bi2);
			graph_tmp = (Graphics2D)paintImage_tmp.getGraphics();
			PREF_W = paintImage.getHeight();
    		PREF_H = paintImage.getWidth();
			//old_PREF_H = PREF_H;
			//old_PREF_W = PREF_W;
			this.setSize(PREF_W, PREF_H);
			repaint();
		}
		public void flip(String str){
			if (str == "horizontal"){
				AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
				tx.translate(-paintImage_tmp.getWidth(null), 0);
				AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
				paintImage_tmp = op.filter(paintImage_tmp, null);
			}else{
				AffineTransform tx = AffineTransform.getScaleInstance(1, -1);
				tx.translate(0, -paintImage_tmp.getHeight(null));
				AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
				paintImage_tmp = op.filter(paintImage_tmp, null);
			}
			repaint();
	        
		}
		public void resizeImage(int w, int h){
			ScaleEffect scaleEffect = new ScaleEffect("resize", true, null, null, w, h);
			actionList.add(scaleEffect);
			drawObject(graph_tmp, paintImage_tmp);
			repaint();
		}
		public void resizeCanvas(int w, int h){
			ScaleEffect scaleEffect = new ScaleEffect("resizeCanvas", true, null, null, w, h);
			actionList.add(scaleEffect);
			drawObject(graph_tmp, paintImage_tmp);
			repaint();
		}
		public void invertColor(){ //add invert action
			ScaleEffect scaleEffect = new ScaleEffect("invertColor", true, null, null, 0, 0);
			actionList.add(scaleEffect);
			drawObject(graph_tmp, paintImage_tmp);
			repaint();
		}
		public void addString(String string, Point point){ //add string action
			StringObject stringObject = new StringObject(string, point, strokeColor, stringSize);
			actionList.add(stringObject);
			drawObject(graph_tmp, paintImage_tmp);
			repaint();
		}
		public void blackAndWhite(){
			ScaleEffect scaleEffect = new ScaleEffect("blackAndWhite", true, null, null, 0, 0);
			actionList.add(scaleEffect);
			drawObject(graph_tmp, paintImage_tmp);
			repaint();
		}
		public void grayScale(){
			ScaleEffect scaleEffect = new ScaleEffect("grayScale", true, null, null, 0, 0);
			actionList.add(scaleEffect);
			drawObject(graph_tmp, paintImage_tmp);
			repaint();
		}
		public void laplacian(int type){
			ScaleEffect scaleEffect = null;
			if (type == 1){
				scaleEffect = new ScaleEffect("laplacian1", true, null, null, 0, 0);
			}else if (type == 2){
				scaleEffect = new ScaleEffect("laplacian2", true, null, null, 0, 0);
			}else if (type == 3){
				scaleEffect = new ScaleEffect("laplacian3", true, null, null, 0, 0);
			}
			actionList.add(scaleEffect);
			drawObject(graph_tmp, paintImage_tmp);
			repaint();
		}
		public void paste(){
			BufferedImage ROI = paintImage_tmp.getSubimage((int)roiStartPoint.getX(), (int)roiStartPoint.getY(), (int)(roiEndPoint.getX()-roiStartPoint.getX()), (int)(roiEndPoint.getY()-roiStartPoint.getY()));
			graph_tmp.drawImage(ROI, 0, 0, null);
			repaint();
		}
		public void copy(){
			BufferedImage ROI = paintImage_tmp.getSubimage(500, 500, 100, 100);
			graph_tmp.drawImage(ROI, 0, 0, null);
			repaint();
		}
		public void cut(){
			BufferedImage ROI = paintImage_tmp.getSubimage(500, 500, 100, 100);
			graph_tmp.drawImage(ROI, 0, 0, null);
			repaint();
		}
		public static void invertImage(BufferedImage input) { // invert the image
	        for (int x = 0; x < input.getWidth(); x++) {
	            for (int y = 0; y < input.getHeight(); y++) {
	                int rgba = input.getRGB(x, y);
	                Color col = new Color(rgba, true);
	                col = new Color(255 - col.getRed(),
	                                255 - col.getGreen(),
	                                255 - col.getBlue());
	                input.setRGB(x, y, col.getRGB());
	            }
	        }
	        
	    }
		static BufferedImage deepCopy(BufferedImage bi) { //Make a deep copy for object
			 ColorModel cm = bi.getColorModel();
			 boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
			 WritableRaster raster = bi.copyData(null);
			 return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
		}
		@Override
		public Dimension getPreferredSize() {
			return new Dimension(PREF_W, PREF_H);
		}
}
