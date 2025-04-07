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
	private int index = 0;
	private BufferedImage image;
	private ArrayList<File> imageList;
	private Dimension lastSize = new Dimension();
	public ImageDealer(ArrayList<File> imageList) {
		this.imageList = imageList;
		setSize(500, 500);
		setBackground(Color.BLACK);
		setImage( imageList.get(index) );
		addComponentListener( new ComponentAdapter() {
			@Override
			public void componentResized( ComponentEvent e) {
				if (!lastSize.equals( getSize() ) ) {
					lastSize.setSize( getSize() );
					repaint();
				}
			}
		} );
	}
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (image == null) return ;
		Graphics2D g2d = (Graphics2D) g;
		
		g2d.setRenderingHint (
			RenderingHints.KEY_INTERPOLATION,
			RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g2d.setRenderingHint (
			RenderingHints.KEY_ANTIALIASING,
			RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setRenderingHint (
			RenderingHints.KEY_RENDERING,
			RenderingHints.VALUE_RENDER_QUALITY);
		
		int panelWidth = getWidth();
		int panelHeight = getHeight();
		
		double widthRadio = (double) panelWidth / image.getWidth();
		double heightRadio = (double) panelHeight / image.getHeight();
		double scale = Math.min(widthRadio, heightRadio);
		
		int scaledWidth = (int)(image.getWidth() * scale);
		int scaledHeight = (int)(image.getHeight() * scale);
		
		int x = (panelWidth - scaledWidth) / 2;
		int y = (panelHeight - scaledHeight) / 2;
		
		g2d.drawImage(image, 
			x, y, x + scaledWidth, y + scaledHeight,
			0, 0, image.getWidth(), image.getHeight(),
			null );
		
	}
	public void setImage(File file) {
		try {
			image = ImageIO.read( file );
		} catch (IOException ex) {
			System.out.println(ex.getMessage());
		}
	}
}
