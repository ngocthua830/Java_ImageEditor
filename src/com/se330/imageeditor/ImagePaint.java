package com.se330.imageeditor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;

public class ImagePaint{
	JRadioButton radio_pencil, radio_line, radio_rect, radio_ellipse, radio_brush, radio_erase, radio_string, radio_curve, radio_rectSelect, radio_colorPicker;
	JRadioButton fillBtn, strokeBtn;
	JButton strokeColorBtn, fillColorBtn;
	JComboBox strokeSizeCbb, zoomSizeCbb;
	Color strokeColor = Color.black;
	Color fillColor = Color.white;
	
	JSlider zoomSlider;
	JLabel coordinateLabel = new JLabel();
	JLabel boxSizeLabel = new JLabel();
	JLabel zoomLabel = new JLabel();
	//Menu
	JMenuBar menuBar;
	JMenu fileMenu, editMenu, helpMenu, imageMenu, effectMenu, subRotateMenu, subFilpMenu;
	JMenuItem menuItem_save, menuItem_open, menuItem_new, menuItem_saveAs;
	JMenuItem menuItem_undo, menuItem_redo, menuItem_copy, menuItem_cut, menuItem_paste;
	JMenuItem menuItem_rotate90, menuItem_rotatem90, menuItem_rotate180;
	JMenuItem menuItem_flipHorizontal, menuItem_flipVertical;
	JMenuItem menuItem_resize, menuItem_resizeCanvas, menuItem_invertColor, menuItem_blackWhite, menuItem_grayScale;
	JMenuItem menuItem_about;
	JMenuItem menuItem_gaussian, menuItem_sharpen, menuItem_sobel, menuItem_laplacian1, menuItem_laplacian2, menuItem_laplacian3; 
	//
	DrawArea drawArea = new DrawArea(this);
	JScrollPane scrollpane;
	Container content;
	
	ActionListener actionListener = new ActionListener(){
		public void actionPerformed(ActionEvent arg0) {
			if (arg0.getSource() == radio_pencil){
				drawArea.setControl(5);
			}else if (arg0.getSource() == radio_line){
				drawArea.setControl(2);
			}else if (arg0.getSource() == radio_rect){
				drawArea.setControl(3);
			}else if (arg0.getSource() == radio_ellipse){
				drawArea.setControl(4);
			}else if (arg0.getSource() == radio_brush){
				drawArea.setControl(1);
			}else if (arg0.getSource() == radio_erase){
				drawArea.setControl(6);
			}else if (arg0.getSource() == radio_string){
				drawArea.setControl(7);
			}else if (arg0.getSource() == radio_curve){
				drawArea.setControl(8);
			}else if (arg0.getSource() == radio_rectSelect){
				drawArea.setControl(9);
			}else if (arg0.getSource() == radio_colorPicker){
				drawArea.setControl(10);
			}
			
		};
	};
	ActionListener buttonActionListener = new ActionListener(){
		public void actionPerformed(ActionEvent arg0){
			if (arg0.getSource() == fillBtn){
				drawArea.setFillMode(true);
			}else  if (arg0.getSource() == strokeBtn){
				drawArea.setFillMode(false);
			}else {
				
			}
		};
	};
	
	
	ActionListener menuActionListener = new ActionListener(){
		public void actionPerformed(ActionEvent arg0){
			if (arg0.getSource() == menuItem_save){
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setFileFilter(new FileNameExtensionFilter("PNG Image", "png"));
				fileChooser.setFileFilter(new FileNameExtensionFilter("JPEG Image", "jpg"));
				fileChooser.setFileFilter(new FileNameExtensionFilter("BMP Image", "bmp"));
				int result = fileChooser.showSaveDialog(null);
				File file = fileChooser.getSelectedFile();
				
				if (file != null) {
				  drawArea.saveImage(fileChooser.getSelectedFile().toPath().toString(), fileChooser.getFileFilter());
				}
			}else if (arg0.getSource() == menuItem_new){
				JTextField xField = new JTextField(10);
				JTextField yField = new JTextField(10);
				JPanel panel = new JPanel();
				panel.add(new JLabel("width:"));
				panel.add(xField);
				panel.add(new JLabel("height:"));
				panel.add(yField);
				int result = JOptionPane.showConfirmDialog(null, panel, "Image Size", JOptionPane.OK_CANCEL_OPTION);
				if (result == JOptionPane.OK_OPTION){
					drawArea.newImage(Integer.parseInt(xField.getText()), Integer.parseInt(yField.getText()));
					scrollpane.revalidate();
					scrollpane.repaint();
				}
			}else if (arg0.getSource() == menuItem_open){
				FileNameExtensionFilter filter = new FileNameExtensionFilter("Image File", "png", "jpg", "gif", "bmp");
		        JFileChooser openFile = new JFileChooser();
		        openFile.setFileFilter(filter);
		        openFile.showOpenDialog(null); 
		        if (openFile.getSelectedFile().toPath() != null){
		            try{
		                drawArea.openImage(openFile.getSelectedFile().toPath().toString());
		                scrollpane.revalidate();
						scrollpane.repaint();
		            }catch (Exception ex){
		                System.out.print("Load error");
		            }
		        }
			}else if (arg0.getSource() == menuItem_undo){
				drawArea.undo();
			}else if (arg0.getSource() == menuItem_redo){
				drawArea.redo();
			}else if (arg0.getSource() == menuItem_gaussian){
				drawArea.gaussian();
			}else if (arg0.getSource() == menuItem_sharpen){
				drawArea.sharpen();
			}else if (arg0.getSource() == menuItem_sobel){
				drawArea.sobel();
			}else if (arg0.getSource() == menuItem_about){
				new About();
			}else if (arg0.getSource() == menuItem_rotate90){
				drawArea.rotate();
			}else if (arg0.getSource() == menuItem_flipHorizontal){
				drawArea.flip("horizontal");
			}else if (arg0.getSource() == menuItem_flipVertical){
				drawArea.flip("vertical");
			}else if (arg0.getSource() == menuItem_resize){
				JTextField xField = new JTextField(10);
				JTextField yField = new JTextField(10);
				JPanel panel = new JPanel();
				panel.add(new JLabel("width:"));
				panel.add(xField);
				panel.add(new JLabel("height:"));
				panel.add(yField);
				int result = JOptionPane.showConfirmDialog(null, panel, "Image Size", JOptionPane.OK_CANCEL_OPTION);
				if (result == JOptionPane.OK_OPTION){
					drawArea.resizeImage(Integer.parseInt(xField.getText()), Integer.parseInt(yField.getText()));
					scrollpane.revalidate();
					scrollpane.repaint();
				}
			}else if (arg0.getSource() == menuItem_resizeCanvas){
				JTextField xField = new JTextField(10);
				JTextField yField = new JTextField(10);
				JPanel panel = new JPanel();
				panel.add(new JLabel("width:"));
				panel.add(xField);
				panel.add(new JLabel("height:"));
				panel.add(yField);
				int result = JOptionPane.showConfirmDialog(null, panel, "Image Size", JOptionPane.OK_CANCEL_OPTION);
				if (result == JOptionPane.OK_OPTION){
					drawArea.resizeCanvas(Integer.parseInt(xField.getText()), Integer.parseInt(yField.getText()));
					scrollpane.revalidate();
					scrollpane.repaint();
				}
			}else if (arg0.getSource() == menuItem_invertColor){
				drawArea.invertColor();
			}else if (arg0.getSource() == menuItem_blackWhite){
				drawArea.blackAndWhite();
			}else if (arg0.getSource() == menuItem_grayScale){
				drawArea.grayScale();
			}else if (arg0.getSource() == menuItem_laplacian1){
				drawArea.laplacian(1);
			}else if (arg0.getSource() == menuItem_laplacian2){
				drawArea.laplacian(2);
			}else if (arg0.getSource() == menuItem_laplacian3){
				drawArea.laplacian(3);
			}else if (arg0.getSource() == menuItem_paste){
				drawArea.paste();
			}else if (arg0.getSource() == menuItem_copy){
				drawArea.cut();
			}else if (arg0.getSource() == menuItem_cut){
				drawArea.copy();
			}
			
			
		};
	};
	
	public static void main(String[] args) {
		new ImagePaint().show();
	}
	public void show() {
		JFrame frame = new JFrame("Image Editor 1.3");
		content = frame.getContentPane();
		content.setLayout(new BorderLayout());
		
		coordinateLabel.setToolTipText("mouse position");
		boxSizeLabel.setToolTipText("size of shape");
		
		JPanel controls = new JPanel();
	    
	    radio_pencil = makeControlButton("", "pencil_small.png", "pencil");
		radio_line = makeControlButton("", "line_small.png", "line");
		radio_rect = makeControlButton("", "rectangle_small.png", "rectangle");
		radio_ellipse = makeControlButton("", "eclipse_small.png", "ellipse");
		radio_brush = makeControlButton("", "paint-brush_small.png", "brush");
		radio_erase = makeControlButton("", "eraser_small.png", "erase");
		radio_string = makeControlButton("", "string_small.png", "string");
		radio_curve = makeControlButton("", "curve_small.png", "curve");
		radio_rectSelect = makeControlButton("", "rectangleSelect.png", "rectangle select");
		radio_colorPicker = makeControlButton("", "color-picker_small.png", "color picker");
		
		ButtonGroup controlBG = new ButtonGroup();
		controlBG.add(radio_pencil);
		controlBG.add(radio_line);
		controlBG.add(radio_brush);
		controlBG.add(radio_colorPicker);
		controlBG.add(radio_curve);
		controlBG.add(radio_ellipse);
		controlBG.add(radio_erase);
		controlBG.add(radio_rect);
		controlBG.add(radio_rectSelect);
		controlBG.add(radio_string);
		
		fillBtn = makeButton("", "fill_small.png", "fill mode");
		strokeBtn = makeButton("", "stroke_small.png", "stroke mode");
		
		ButtonGroup strokeFillBG = new ButtonGroup();
		strokeFillBG.add(fillBtn);
		strokeFillBG.add(strokeBtn);
		
		//CREATE MENU//
		menuBar = new JMenuBar();
		fileMenu = new JMenu("File");
		editMenu = new JMenu("Edit");
		imageMenu = new JMenu("Image");
		helpMenu = new JMenu("Help");
		effectMenu = new JMenu("Effect");
		subRotateMenu = new JMenu("Rotate");
		subFilpMenu = new JMenu("Flip");
		menuItem_save = makeJMenuItem("Save");
		menuItem_save.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.Event.CTRL_MASK));
		menuItem_saveAs = makeJMenuItem("Save As");
		menuItem_open = makeJMenuItem("Open");
		menuItem_open.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.Event.CTRL_MASK));
		menuItem_new = makeJMenuItem("New");
		menuItem_new.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.Event.CTRL_MASK));
		menuItem_undo = makeJMenuItem("Undo");
		menuItem_undo.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Z, java.awt.Event.CTRL_MASK));
		menuItem_redo = makeJMenuItem("Redo");
		menuItem_redo.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_R, java.awt.Event.CTRL_MASK));
		menuItem_copy = makeJMenuItem("Copy");
		menuItem_copy.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C, java.awt.Event.CTRL_MASK));
		menuItem_cut = makeJMenuItem("Cut");
		menuItem_cut.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_X, java.awt.Event.CTRL_MASK));
		menuItem_paste = makeJMenuItem("Paste");
		menuItem_paste.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_V, java.awt.Event.CTRL_MASK));
		menuItem_about = makeJMenuItem("About");
		menuItem_gaussian = makeJMenuItem("Gaussian Blur");
		menuItem_sharpen = makeJMenuItem("Sharpen");
		menuItem_sobel = makeJMenuItem("Sobel Filter");
		menuItem_rotate180 = makeJMenuItem("180 degree");
		menuItem_rotatem90 = makeJMenuItem("-90 degree");
		menuItem_rotate90 = makeJMenuItem("90 degree");
		menuItem_flipHorizontal = makeJMenuItem("Horizontal");
		menuItem_flipVertical = makeJMenuItem("Vertical");
		menuItem_resize = makeJMenuItem("Resize Image");
		menuItem_resizeCanvas = makeJMenuItem("Resize Canvas");
		menuItem_invertColor = makeJMenuItem("Invert Color");
		menuItem_blackWhite = makeJMenuItem("Black and White");
		menuItem_grayScale = makeJMenuItem("GrayScale Image");
		menuItem_laplacian1 = makeJMenuItem("Laplacian 1");
		menuItem_laplacian2 = makeJMenuItem("Laplacian 2");
		menuItem_laplacian3 = makeJMenuItem("Laplacian 3");
		fileMenu.add(menuItem_new);
		fileMenu.add(menuItem_open);
		fileMenu.add(menuItem_save);
		fileMenu.add(menuItem_saveAs);
		editMenu.add(menuItem_undo);
		editMenu.add(menuItem_redo);
		editMenu.add(menuItem_copy);
		editMenu.add(menuItem_cut);
		editMenu.add(menuItem_paste);
		imageMenu.add(subRotateMenu);
		imageMenu.add(subFilpMenu);
		imageMenu.add(menuItem_resize);
		imageMenu.add(menuItem_resizeCanvas);
		imageMenu.add(menuItem_invertColor);
		imageMenu.add(menuItem_blackWhite);
		imageMenu.add(menuItem_grayScale);
		helpMenu.add(menuItem_about);
		effectMenu.add(menuItem_gaussian);
		effectMenu.add(menuItem_sharpen);
		effectMenu.add(menuItem_sobel);
		effectMenu.add(menuItem_laplacian1);
		effectMenu.add(menuItem_laplacian2);
		effectMenu.add(menuItem_laplacian3);
		subRotateMenu.add(menuItem_rotate90);
		subRotateMenu.add(menuItem_rotatem90);
		subRotateMenu.add(menuItem_rotate180);
		subFilpMenu.add(menuItem_flipVertical);
		subFilpMenu.add(menuItem_flipHorizontal);
		menuBar.add(fileMenu);
		menuBar.add(editMenu);
		menuBar.add(imageMenu);
		menuBar.add(effectMenu);
		menuBar.add(helpMenu);
		//
		Object[] items =
	        {
				makeStrokeSizeImage(1),
				makeStrokeSizeImage(2),
				makeStrokeSizeImage(3),
				makeStrokeSizeImage(4),
				makeStrokeSizeImage(5),
				makeStrokeSizeImage(6),
				makeStrokeSizeImage(7),
				makeStrokeSizeImage(8),
				makeStrokeSizeImage(9),
				makeStrokeSizeImage(10),
				makeStrokeSizeImage(11),
				makeStrokeSizeImage(12),
				makeStrokeSizeImage(13),
				makeStrokeSizeImage(14),
				makeStrokeSizeImage(15),
				makeStrokeSizeImage(16)
			};
		strokeSizeCbb = new JComboBox( items );
		strokeSizeCbb.setToolTipText("stroke size");
		strokeSizeCbb.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				drawArea.setStrokeSize(strokeSizeCbb.getSelectedIndex()+1);
			}
		});
		strokeColorBtn = makeBrowseColorButtons("stroke", 5, true, "Click to browse color");
		fillColorBtn = makeBrowseColorButtons("fill", 6, false, "click to browse color");
		
		zoomSlider = new JSlider(JSlider.HORIZONTAL, -5, 20, 0);  
		zoomSlider.setMinorTickSpacing(2);  
		zoomSlider.setMajorTickSpacing(10); 
		zoomSlider.setToolTipText("Zoom Slider");
		zoomLabel.setText(Integer.toString(zoomSlider.getValue()+1*100)+"%");
		zoomSlider.addChangeListener(new ChangeListener() {
			
			public void stateChanged(ChangeEvent arg0) {
				
				if (zoomSlider.getValue() >= 0){
					drawArea.setZoomSize(zoomSlider.getValue()+1);
					zoomLabel.setText(Integer.toString((zoomSlider.getValue()+1)*100)+"%");
				}else if (zoomSlider.getValue() < 0){
					drawArea.setZoomSize(1+((float)(zoomSlider.getValue()/10.0f)));
					zoomLabel.setText(Integer.toString(100-(zoomSlider.getValue()*-10))+"%");
				}
				
				drawArea.setPREF_W((int)(drawArea.getOld_PREF_W()*drawArea.getZoomSize()));
				drawArea.setPREF_H((int)(drawArea.getOld_PREF_H()*drawArea.getZoomSize()));
				drawArea.setSize(drawArea.getPREF_W(), drawArea.getPREF_H());
				scrollpane.revalidate();
				scrollpane.repaint();
			}
		});
		
		JPanel drawPanel = new JPanel();
		drawPanel.add(drawArea, BorderLayout.CENTER);
		scrollpane = new JScrollPane(drawPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		drawPanel.repaint();
		//
		JPanel colorPanel = new JPanel();
		JPanel browserColorPanel = new JPanel();
		JPanel settingPanel = new JPanel();
		
		colorPanel.setSize(200, 70);
		colorPanel.add(makeColorAreaButton(255, 255, 255));
		colorPanel.add(makeColorAreaButton(0, 0, 0));
		colorPanel.add(makeColorAreaButton(150, 150, 150));
		colorPanel.add(makeColorAreaButton(100, 20, 20));
		colorPanel.add(makeColorAreaButton(255, 0, 0));
		colorPanel.add(makeColorAreaButton(0, 255, 0));
		colorPanel.add(makeColorAreaButton(0, 0, 255));
		colorPanel.add(makeColorAreaButton(170, 0, 255));
		colorPanel.add(makeColorAreaButton(255, 255, 0));
		colorPanel.add(makeColorAreaButton(0, 255, 255));
		colorPanel.add(makeColorAreaButton(255, 0, 255));
		colorPanel.add(makeColorAreaButton(150, 255, 0));
		colorPanel.add(makeColorAreaButton(255, 100, 0));
		colorPanel.add(makeColorAreaButton(100, 255, 0));
		colorPanel.add(makeColorAreaButton(0, 255, 100));
		
		browserColorPanel.add(strokeColorBtn);
		browserColorPanel.add(fillColorBtn);
		
		//
		
		//
		controls.add(radio_rectSelect);
		controls.add(radio_pencil);
		controls.add(radio_brush);
		controls.add(radio_erase);
		controls.add(radio_string);
		controls.add(radio_colorPicker);
		
		controls.add(radio_line);
		controls.add(radio_rect);
		controls.add(radio_ellipse);
		controls.add(radio_curve);
		settingPanel.add(fillBtn);
		settingPanel.add(strokeBtn);
		settingPanel.add(strokeSizeCbb);		//
		
		JPanel coordinatePanel = new JPanel();
		coordinatePanel.add(coordinateLabel, BorderLayout.WEST);
		coordinatePanel.add(new JPanel());
		coordinatePanel.add(boxSizeLabel, BorderLayout.EAST);
		
		JPanel topPanel = new JPanel();
		JPanel leftPanel = new JPanel();
		JPanel bottomPanel = new JPanel();
		
		topPanel.setLayout(new GridLayout(1, 1, 10, 10));
		topPanel.add(controls, BorderLayout.NORTH);
		topPanel.add(settingPanel);
		
		bottomPanel.setLayout(new GridLayout(1, 1, 10, 10));
		bottomPanel.add(browserColorPanel, BorderLayout.WEST);
		bottomPanel.add(colorPanel, BorderLayout.CENTER);
		bottomPanel.add(coordinatePanel, BorderLayout.EAST);
		bottomPanel.add(zoomSlider);
		bottomPanel.add(zoomLabel);
		
		//
		content.add(topPanel, BorderLayout.NORTH);
		content.add(scrollpane, BorderLayout.CENTER);
		content.add(leftPanel, BorderLayout.WEST);
		content.add(bottomPanel, BorderLayout.SOUTH);
		frame.setJMenuBar(menuBar);
		frame.setSize(600, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
		
	}
	public JRadioButton makeControlButton(String name, String iconName, String tip){ //pencil, line, curve, erase,...
		ImageIcon icon = new ImageIcon("icon/"+iconName);
		JRadioButton button = new JRadioButton(name, icon);
		button.setToolTipText(tip);
		button.setBorderPainted(true);
		button.addActionListener(actionListener);
		return button;
		
	}
	public JRadioButton makeButton(String name,String iconName, String tip){ //fillbtn, strokebtn
		ImageIcon icon = new ImageIcon("icon/"+iconName);
		JRadioButton button = new JRadioButton(name, icon);
		button.setToolTipText(tip);
		button.setBorderPainted(true);
		button.addActionListener(buttonActionListener);
		return button;
	}
	
	public JMenuItem makeJMenuItem(String name){
		JMenuItem item = new JMenuItem(name);
		item.addActionListener(menuActionListener);
		return item;
	}
	public JButton makeBrowseColorButtons(String type, final int actionNum, final boolean stroke, String tip){
		JButton But = new JButton();
		But.setToolTipText(tip);
		But.setContentAreaFilled(false);
		But.setBorderPainted(true);
		BufferedImage b_img = new BufferedImage(30, 30, BufferedImage.TYPE_3BYTE_BGR);
		Graphics2D graphics = b_img.createGraphics();
		if (type == "stroke"){
			graphics.setPaint ( strokeColor );
		}else{
			graphics.setPaint ( fillColor );
		}
		graphics.fillRect ( 0, 0, b_img.getWidth(), b_img.getHeight() );
		Icon icon = new ImageIcon(b_img);
		But.setIcon(icon);
		But.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(stroke){
					strokeColor = JColorChooser.showDialog(null, "Pick a Stroke", Color.BLACK);
					drawArea.setStrokeColor(strokeColor);
					setBtnColor(strokeColorBtn, strokeColor);
				}else{
					fillColor = JColorChooser.showDialog(null, "Pick a Fill", Color.BLACK);
					drawArea.setFillColor(fillColor);
					setBtnColor(fillColorBtn, fillColor);
				}
			}
		});
		return But;
	}
	public JRadioButton makeColorAreaButton(int r, int g, int b){
		JRadioButton but = new JRadioButton();
		BufferedImage b_img = new BufferedImage(30, 30, BufferedImage.TYPE_3BYTE_BGR);
		Graphics2D graphics = b_img.createGraphics();
		graphics.setPaint ( new Color ( r, g, b ) );
		graphics.fillRect ( 0, 0, b_img.getWidth(), b_img.getHeight() );
		Icon icon = new ImageIcon(b_img);
		but.setIcon(icon);
		final Color color = new Color ( r, g, b );
		but.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent arg0) {
				if (SwingUtilities.isLeftMouseButton(arg0)) {
					drawArea.setStrokeColor(color);
					setBtnColor(strokeColorBtn, color);
			    }else if (SwingUtilities.isRightMouseButton(arg0)){
			    	drawArea.setFillColor(color);
			    	setBtnColor(fillColorBtn, color);
			    }
				
				super.mouseClicked(arg0);
			}
			
		});
		return but;
	}
	private ImageIcon makeStrokeSizeImage(int size){
		BufferedImage b_img = new BufferedImage(50, size, BufferedImage.TYPE_3BYTE_BGR);
		Graphics2D graphics = b_img.createGraphics();
			graphics.setPaint ( Color.black );
		graphics.fillRect ( 0, 0, b_img.getWidth(), b_img.getHeight() );
		return new ImageIcon(b_img);
	}
	
	public void setStrokeBtnColor(Color color){
		BufferedImage b_img = new BufferedImage(30, 30, BufferedImage.TYPE_3BYTE_BGR);
		Graphics2D graphics = b_img.createGraphics();
			graphics.setPaint ( color );
		graphics.fillRect ( 0, 0, b_img.getWidth(), b_img.getHeight() );
		Icon icon = new ImageIcon(b_img);
		strokeColorBtn.setIcon(icon);
	}
	public void setBtnColor(Object object, Color color){
		BufferedImage b_img = new BufferedImage(30, 30, BufferedImage.TYPE_3BYTE_BGR);
		Graphics2D graphics = b_img.createGraphics();
			graphics.setPaint ( color );
		graphics.fillRect ( 0, 0, b_img.getWidth(), b_img.getHeight() );
		Icon icon = new ImageIcon(b_img);
		((JButton)object).setIcon(icon);
	}
	public void setLabelCoordinate(Point point){
		coordinateLabel.setText(point.x+" : "+point.y);
	}
	public void setboxSizeLabel(Point point){
		boxSizeLabel.setText(point.x+" : "+point.y);
	}
}
