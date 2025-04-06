import javax.swing.JPanel;
import javax.swing.JButton;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class ImageDealer extends JPanel {
	Image image;
	Image originImage;
	List<File> imageShelf;
	Point imagePos = new Point(0, 0);
	int imageIndex = 0;
	JButton btnPrev;
	JButton btnNext;
	protected ImageDealer(List<File> imageShelf) {
		super();
		setBackground(Color.BLACK);
		this.imageShelf = imageShelf;
		setImage(imageShelf.get(imageIndex));
		setLayout(null);
		btnPrev = new JButton("Prev");
		btnPrev.setBounds(0, 0, 70, 30);	
		btnNext = new JButton("Next");
		btnNext.setBounds(0, 0, 70, 30);	
		addComponentListener( componentPositioning() );
		btnNext.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed( ActionEvent e) {
				nextImage();
			}
		});
		btnPrev.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed( ActionEvent e) {
				prevImage();
			}
		});
		add(btnPrev, BorderLayout.WEST);
		add(btnNext, BorderLayout.EAST);
	}
	@Override
	protected void paintComponent(Graphics graphics) {
		super.paintComponent(graphics);
			graphics.drawImage(image, (int)imagePos.getX(), (int)imagePos.getY(), image.getWidth(null), image.getHeight(null), this);		
	}
	private ComponentAdapter componentPositioning () {
		return new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent ev) {
				centerImage();
				setComponentPosition(btnPrev, 0, 0);
				setComponentPosition(btnNext, 1, 0);
			}
		};
	} // end centerImageOnResize

	public void setImage(File newImage) {
		try {
			originImage = ImageIO.read(newImage);
			image = originImage;
		} catch (IOException ex) {
			System.out.println(ex.getMessage());
		}
		
	}
	public void nextImage() {
		if (imageIndex < imageShelf.size() - 1) {
			setImage(imageShelf.get(++imageIndex));			
		} else {
			setImage(imageShelf.get( (imageIndex = 0)));
		}
		centerImage();
	}
	public void prevImage() {
		if (imageIndex > 0) {
			setImage(imageShelf.get(--imageIndex));			
		} else {
			setImage(imageShelf.get( (imageIndex = imageShelf.size() - 1 )));
		}
		centerImage();
	}
	private void centerImage() {
		int scaledWidth;
		int scaledHeight;
		int panelWidth = getWidth();
		int panelHeight = getHeight();
		int imageWidth = originImage.getWidth(null);
		int imageHeight = originImage.getHeight(null);
		
			scaledHeight = panelHeight;
			scaledWidth = imageWidth * panelHeight / imageHeight;
		
		imagePos.setLocation(panelWidth / 2 - scaledWidth / 2, panelHeight / 2 - scaledHeight / 2);
		image = originImage.getScaledInstance(scaledWidth, scaledHeight, Image.SCALE_SMOOTH);
		repaint();
	}
	public void setComponentPosition(Component com, int xPos, int yPos) {
		if (xPos == 0) {
			com.setBounds(0, com.getY(), com.getWidth(), com.getHeight() );
		} else if (xPos == 1) {
			com.setBounds(getWidth() - com.getWidth(), com.getY(), com.getWidth(), com.getHeight() );
		}
		if (yPos == 0) {
			com.setBounds(com.getX(), getHeight() / 2 - com.getHeight() / 2, com.getWidth(), com.getHeight() );
		} else if (yPos == 1) {
			com.setBounds(com.getX(), com.getY(), com.getWidth(), com.getHeight() );
		}
	}
	public void deleteImage() {		
		getCurrentImage().delete();
		imageShelf.remove(imageIndex);
		nextImage();
	}
	public File getCurrentImage() {
		return imageShelf.get(imageIndex);
	}
}
