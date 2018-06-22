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
	JButton fillBtn, strokeBtn, strokeColorBtn, fillColorBtn;
	JComboBox strokeSizeCbb, zoomSizeCbb;
	Color strokeColor = Color.black;
	Color fillColor = Color.white;
	
	JSlider zoomSlider;
	JLabel coordinateLabel = new JLabel();
	JLabel boxSizeLabel = new JLabel();
	//Menu
	JMenuBar menuBar;
	JMenu fileMenu, editMenu, helpMenu, imageMenu, effectMenu, subRotateMenu, subFilpMenu;
	JMenuItem menuItem_save, menuItem_open, menuItem_new, menuItem_saveAs;
	JMenuItem menuItem_undo, menuItem_redo;
	JMenuItem menuItem_rotate90, menuItem_rotatem90, menuItem_rotate180;
	JMenuItem menuItem_flipHorizontal, menuItem_flipVertical;
	JMenuItem menuItem_about;
	JMenuItem menuItem_blur, menuItem_sharpen, menuItem_sobel; 
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
			}else if (arg0.getSource() == menuItem_blur){
				drawArea.blur();
			}else if (arg0.getSource() == menuItem_sharpen){
				drawArea.sharpen();
			}else if (arg0.getSource() == menuItem_sobel){

			}else if (arg0.getSource() == menuItem_about){
				new About();
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
		
		
		JPanel controls = new JPanel();
	    ImageIcon icon = new ImageIcon("icon/pencil_small.jpg");
	    radio_pencil = makeControlButton("Pencil", icon);
		radio_line = makeControlButton("Line", icon);
		radio_rect = makeControlButton("Rect", icon);
		radio_ellipse = makeControlButton("Ellipse", icon);
		radio_brush = makeControlButton("Brush", icon);
		radio_erase = makeControlButton("Erase", icon);
		radio_string = makeControlButton("String", icon);
		radio_curve = makeControlButton("Curve", icon);
		radio_rectSelect = makeControlButton("", icon);
		radio_colorPicker = makeControlButton("Color Picker", icon);
		
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
		
		fillBtn = makeButton("Fill");
		strokeBtn = makeButton("Stroke");
		
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
		menuItem_about = makeJMenuItem("About");
		menuItem_blur = makeJMenuItem("Blur");
		menuItem_sharpen = makeJMenuItem("Sharpen");
		menuItem_sobel = makeJMenuItem("Soble Filter");
		menuItem_rotate180 = makeJMenuItem("180 degree");
		menuItem_rotatem90 = makeJMenuItem("-90 degree");
		menuItem_rotate90 = makeJMenuItem("90 degree");
		menuItem_flipHorizontal = makeJMenuItem("Horizontal");
		menuItem_flipVertical = makeJMenuItem("Vertical");
		fileMenu.add(menuItem_new);
		fileMenu.add(menuItem_open);
		fileMenu.add(menuItem_save);
		fileMenu.add(menuItem_saveAs);
		editMenu.add(menuItem_undo);
		editMenu.add(menuItem_redo);
		imageMenu.add(subRotateMenu);
		imageMenu.add(subFilpMenu);
		helpMenu.add(menuItem_about);
		effectMenu.add(menuItem_blur);
		effectMenu.add(menuItem_sharpen);
		effectMenu.add(menuItem_sobel);
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
		strokeSizeCbb.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				drawArea.setStrokeSize(strokeSizeCbb.getSelectedIndex()+1);
			}
		});
		strokeColorBtn = makeBrowseColorButtons("stroke", 5, true);
		fillColorBtn = makeBrowseColorButtons("fill", 6, false);
		
		JPanel drawPanel = new JPanel();
		drawPanel.add(drawArea, BorderLayout.CENTER);
		scrollpane = new JScrollPane(drawPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		drawPanel.repaint();
		//
		JPanel colorPanel = new JPanel();
		JPanel browserColorPanel = new JPanel();
		
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
		controls.add(radio_line);
		controls.add(radio_curve);
		controls.add(radio_rect);
		controls.add(radio_ellipse);
		controls.add(radio_brush);
		controls.add(radio_erase);
		controls.add(radio_string);
		controls.add(radio_colorPicker);
		controls.add(fillBtn);
		controls.add(strokeBtn);
		controls.add(strokeSizeCbb);		//
		
		JPanel coordinatePanel = new JPanel();
		coordinatePanel.add(coordinateLabel, BorderLayout.WEST);
		coordinatePanel.add(new JPanel());
		coordinatePanel.add(boxSizeLabel, BorderLayout.EAST);
		
		JPanel topPanel = new JPanel();
		JPanel leftPanel = new JPanel();
		JPanel bottomPanel = new JPanel();
		topPanel.setLayout(new GridLayout(1, 1, 10, 10));
		bottomPanel.setLayout(new GridLayout(1, 1, 10, 10));
		
		
		
		bottomPanel.add(browserColorPanel, BorderLayout.WEST);
		bottomPanel.add(colorPanel, BorderLayout.CENTER);
		bottomPanel.add(coordinatePanel, BorderLayout.EAST);
		
		topPanel.add(controls, BorderLayout.NORTH);
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
	public JRadioButton makeControlButton(String name, ImageIcon icon){ //pencil, line, curve, erase,...
		JRadioButton button = new JRadioButton(name, icon);
		button.setBorderPainted(true);
		button.addActionListener(actionListener);
		return button;
		
	}
	public JButton makeButton(String name){
		JButton button = new JButton(name);
		button.setBorderPainted(true);
		button.setFocusPainted(false);
		button.setContentAreaFilled(false);
		button.addActionListener(buttonActionListener);
		return button;
	}
	
	public JMenuItem makeJMenuItem(String name){
		JMenuItem item = new JMenuItem(name);
		item.addActionListener(menuActionListener);
		return item;
	}
	public JButton makeBrowseColorButtons(String type, final int actionNum, final boolean stroke){
		JButton But = new JButton();
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
