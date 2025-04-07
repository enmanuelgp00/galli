package galli;

import javax.swing.*;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.event.*;
import java.io.File;
import java.util.ArrayList;
import galli.image.ImageDealer;

public class Galli extends JFrame {
	Galli (ArrayList<File> list) {
		/*
		try {
			BufferedImage image = ImageIO.read(list.get(0));
			ImageResizingPanel panel = new ImageResizingPanel(image);
			add(panel);
		} catch (Exception e) {
		}
		*/
		ImageDealer imageDealer = new ImageDealer(list);
		add(imageDealer);
		/*
		try {
			final BufferedImage original = ImageIO.read( new File("image.jpg")); //.getScaledInstance(500, 500, Image.SCALE_SMOOTH);	
			BufferedImage scaled = new BufferedImage( 500, 500, BufferedImage.TYPE_INT_ARGB);
			Graphics2D g2d = scaled.createGraphics();			
			JPanel panel = new JPanel() {
				@Override
				public void paintComponent(Graphics g) {
					super.paintComponent(g);
					g.drawImage(scaled, 0, 0, null);
				}
			};
			panel.setBackground(Color.BLACK);
			g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
			g2d.drawImage(original, 0, 0, 500, 500, null);
			g2d.dispose();
			panel.addComponentListener( new ComponentAdapter() {
				@Override
				public void componentResized(ComponentEvent e) {
					
				}
			});
			add(panel);
			setSize(500, 500);
			setVisible(true);
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}	
		*/
		setSize(500, 500);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	public static void main (String[] args) {
		ArrayList<File> imageList = new ArrayList<File>();
		imageList.add( new File (args[0]) );
		new Galli(imageList);
	}
}
