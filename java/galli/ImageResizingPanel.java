package galli;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;

public class ImageResizingPanel extends JPanel {
    private BufferedImage originalImage;
    private Dimension lastSize = new Dimension();

    public ImageResizingPanel(BufferedImage image) {
        this.originalImage = image;
        
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                // Only repaint if size actually changed
                if (!lastSize.equals(getSize())) {
                    lastSize.setSize(getSize());
                    repaint();
                }
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        if (originalImage == null) return;


	//Use the same Graphics
	Graphics2D g2d = (Graphics2D) g;
        
        // Set high-quality rendering hints
        g2d.setRenderingHint(
            RenderingHints.KEY_INTERPOLATION,
            RenderingHints.VALUE_INTERPOLATION_BILINEAR
        );
        g2d.setRenderingHint(
            RenderingHints.KEY_RENDERING,
            RenderingHints.VALUE_RENDER_QUALITY
        );
        g2d.setRenderingHint(
            RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON
        );

        // Calculate scaled dimensions while keeping aspect ratio
        int panelWidth = getWidth();
        int panelHeight = getHeight();
        
        double widthRatio = (double) panelWidth / originalImage.getWidth();
        double heightRatio = (double) panelHeight / originalImage.getHeight();
        double scale = Math.min(widthRatio, heightRatio);
        
        int scaledWidth = (int) (originalImage.getWidth() * scale);
        int scaledHeight = (int) (originalImage.getHeight() * scale);
        
        // Center the image
        int x = (panelWidth - scaledWidth) / 2;
        int y = (panelHeight - scaledHeight) / 2;
        
        // Draw the image directly with scaling
        g2d.drawImage(
            originalImage,
            x, y, x + scaledWidth, y + scaledHeight,
            0, 0, originalImage.getWidth(), originalImage.getHeight(),
            null
        );
    }
}