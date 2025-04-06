package galli;

import javax.swing.*;
import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;

public class Galli extends JFrame {
	Galli (ArrayList<File> list) {
		try {
			Image image = ImageIO.read( new File("image.jpg"));		
			JPanel panel = new JPanel() {
				@Override
				public void paintComponent(Graphics g) {
					super.paintComponent(g);
					g.drawImage(image, 0, 0, null);
				}
			};
			panel.setBackground(Color.BLACK);
			add(panel);
			setSize(500, 500);
			setVisible(true);
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}	
	}
	public static void main (String[] args) {
		new Galli(new ArrayList<File>());
	}
}