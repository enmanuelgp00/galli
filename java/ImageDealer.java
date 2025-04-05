import javax.swing.JPanel;
import javax.imageio.ImageIO;
import java.util.List;
import java.awt.Image;
import java.awt.Graphics;
import java.awt.Color;
import java.io.File;

public class ImageDealer extends JPanel {
	Image image;
	protected ImageDealer(List<File> imageShelf) {
		try {
			image = ImageIO.read(imageShelf.get(0));
			image = image.getScaledInstance(500, 500, Image.SCALE_SMOOTH);
		} catch (Exception e) {

		}
		setBackground(Color.BLACK);
	}
	@Override
	protected void paintComponent(Graphics graphics) {
		super.paintComponent(graphics);
		graphics.drawImage(image, 0, 0, image.getWidth(null), image.getHeight(null), this);
	}
}
