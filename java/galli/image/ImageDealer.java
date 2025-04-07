package galli.image;

import javax.swing.JPanel;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ImageDealer extends JPanel {
	int index = 0;
	BufferedImage image;
	ArrayList<File> imageList;
	
	public ImageDealer(ArrayList<File> imageList) {
		this.imageList = imageList;
		setSize(500, 500);
		setBackground(Color.BLACK);
		setImage( imageList.get(index) );
		addComponentListener( new ComponentAdapter() {
			@Override
			public void componentResized( ComponentEvent e) {
			}
		} );
	}
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (image == null) return ;
		Graphics2D g2d = (Graphics2D) g;
		g2d.drawImage(image, 0, 0, getWidth(), getHeight(), null );
	}
	public void setImage(File file) {
		try {
			image = ImageIO.read( file );
		} catch (IOException ex) {
			System.out.println(ex.getMessage());
		}
	}
}
