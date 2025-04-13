package galli.gallery;
import galli.gallery.image.ImageChecker;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

public abstract class Curator extends JPanel {
	private final Gallery gallery;
	private int index = 0;
	private double scale;
	private BufferedImage image;
	private Dimension lastSize = new Dimension();

	public Curator(Gallery gallery) {
		super(new FlowLayout(FlowLayout.LEFT));
		setFocusable(true);
		requestFocusInWindow();
		this.gallery = gallery;
		gallery.shuffle();		
		setBackground(Color.BLACK);	
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

		addKeyListener( new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e){
				switch (e.getKeyCode()) {
					case KeyEvent.VK_RIGHT:
							next();
						break;
					case KeyEvent.VK_LEFT:
							previous();
						break;
				}
			}
		});

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
		if (ImageChecker.isImageGif(file)) {
			System.out.println("This is a gif");
		}
			if (index % 10 == 0) {
				System.gc();
			}
		try {
			image = ImageIO.read(file);
			OnImageExibition(file);
			repaint();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	
	public abstract void OnImageExibition(File image);
	
	public void delete() {
		try {

		File file = gallery.get(index);
		gallery.remove(index);
		System.out.println(file.delete());
		} catch (Exception x) {

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
		g2d.dispose();
	}
}
