package galli.gallery;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class Curator extends JPanel {
	private final Gallery gallery;
	private int index = 0;
	private double scale;
	private BufferedImage image;
	private Dimension lastSize = new Dimension();
	public Curator(Gallery gallery) {
		this.gallery = gallery;
		gallery.shuffle();
		setSize(500, 500);
		setBackground(Color.BLACK);
		setBorder(null);
		exhibit(gallery.get(index));

		addComponentListener( new ComponentAdapter() {
			@Override
			public void componentResized( ComponentEvent e) {
				if (!lastSize.equals( getSize() ) ) {
					lastSize.setSize( getSize() );
					repaint();
				}
			}
		} );

		addMouseListener( new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if (getWidth() / 2 < e.getX()) {
					next();					
				} else {
					previous();
				}
			}
		} );
	}
	public void next() {
		if ((++index) > gallery.size() - 1) {
			index = 0;
		}
		exhibit(gallery.get(index));		
	}
	public void previous() {
		if ((--index) < 0) {
			index = gallery.size() - 1;
		}
		exhibit(gallery.get(index));	

	}
	public void exhibit(File file) {
		try {
			image = ImageIO.read(file);
			System.out.println(file.getName());
			repaint();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
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
		scale = Math.min(widthRadio, heightRadio);
		
		int scaledWidth = (int)(image.getWidth() * scale);
		int scaledHeight = (int)(image.getHeight() * scale);
		
		int x = (panelWidth - scaledWidth) / 2;
		int y = (panelHeight - scaledHeight) / 2;
		
		g2d.drawImage(image, 
			x, y, x + scaledWidth, y + scaledHeight,
			0, 0, image.getWidth(), image.getHeight(),
			null );
	}
}
