package galli.gallery;
import galli.gallery.image.ImageChecker;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;
import javax.imageio.ImageReader;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


public abstract class Curator extends JPanel {
	private Gallery gallery;
	private int index = 0;
	private double scale;
	private BufferedImage image;
	private Dimension lastSize = new Dimension();
	private JLabel label;
	private String FILE_DATA_GALLERY =  "saved_gallery.data";
	public Curator(Gallery gallery) {
		//super(new FlowLayout(FlowLayout.LEFT));
		super(new BorderLayout());
		setFocusable(true);
		requestFocusInWindow();
		if (getSavedGallery() != null && isSameGalleryRootDir(gallery, getSavedGallery())) {
			setGallery(getSavedGallery());
		} else {
			if (gallery.isEmpty()) {
				System.out.println("There are no pictures in : " + gallery.getRootDir().getAbsolutePath());
				System.exit(0);
			}
			setGallery(gallery);
			saveGallery(gallery);
		}
		gallery.shuffle();
		setBackground(Color.BLACK);
		label = new JLabel();
		add(label, BorderLayout.CENTER);
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
	public void setGallery(Gallery gallery) {
		this.gallery = gallery;
	}
	public Gallery getSavedGallery() {
		Gallery savedGallery = null;
		try (ObjectInputStream fileReader = new ObjectInputStream( new FileInputStream(FILE_DATA_GALLERY))) {
			savedGallery = (Gallery) fileReader.readObject();
			fileReader.close();
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
		return savedGallery;
	}
	public boolean isSameGalleryRootDir(Gallery gall0, Gallery gall1) {
		boolean value = false;
		try {
			value = Files.mismatch(	Path.of(gall1.getRootDir().getAbsolutePath()),	Path.of(gall0.getRootDir().getAbsolutePath())) == -1 ;
		} catch (IOException ex) {
			System.out.println(ex.getMessage());
		}

		return value;
	}
	private void saveGallery(Gallery gallery) {
		try (ObjectOutputStream fileWriter = new ObjectOutputStream( new FileOutputStream(FILE_DATA_GALLERY, true) ) ) {
			fileWriter.writeObject(gallery);
			fileWriter.close();
		} catch (IOException ex) {
			System.out.println(ex.getMessage());
		}
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
		newImageSelected(file);
		if (index % 10 == 0) {
			System.gc();
		}
		if (ImageChecker.isImageGif(file)) {
			image = null;
			label.setIcon( new ImageIcon(file.getPath()));
			System.out.println("Width of label :" + label.getWidth());
		} else {
			try {
				label.setIcon(null);
				image = ImageIO.read(file);
				repaint();
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
		newImageLoaded(file);
	}

	public abstract void newImageSelected(File image);
	public abstract void newImageLoaded(File image);


	public void deleteCurrentImage() {
		try {
			if (label.getIcon() != null) {
				ImageIcon icon = (ImageIcon) label.getIcon();
				icon.getImage().flush();
				label.setIcon(null);
			}
			Path path = Paths.get( gallery.get(index).getPath());
			Files.delete(path);
			gallery.remove(index);
			next();
		} catch (Exception x) {
			System.out.println(x.getMessage());
		}

	}
	public void setImage(BufferedImage image) {
		this.image = image;
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
