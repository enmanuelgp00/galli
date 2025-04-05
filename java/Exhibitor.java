
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Exhibitor extends JFrame {

    Panel panel;
    int t = 7000;
    Thread thread;
    boolean exposing = false;
    final int MARGIN = 40;

    Exhibitor(List<String> listPathTree) {
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        panel = new Panel(listPathTree);
        panel.setBackground(Color.BLACK);
        add(panel);
        ajustOverflowImage();
        centrateImagePosition();
        setVisible(true);
		addKeyListener(new KeyAdapter () {
			@Override
			public void keyPressed(KeyEvent e){
				switch (e.getKeyCode()) {
					case KeyEvent.VK_LEFT:
						panel.preparePrev();
						showImage();
						break;
					case KeyEvent.VK_RIGHT:
						panel.prepareNext();
						showImage();
						break;
				}
			}
		});
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent event) {
                if (event.getPoint().getX() > (getWidth() / 3) * 2) {
                    panel.prepareNext();
                    showImage();
                } else if (event.getPoint().getX() < (getWidth() / 3)) {
                    panel.preparePrev();
                    showImage();
                } else {
                    if (exposing) {
                        thread.interrupt();
                        exposing = false;
                    } else {
                        expose();
                        exposing = true;
                    }
                }
            }
        });
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent event) {
                showImage();
            }
        });
    }

    void ajustSize() {
        setSize(panel.getImageWidth(), panel.getImageHeight());
    }

    public int getActualWidth() {
        return getWidth();
    }

    public int getActualHeight() {
        return getHeight() - 41;
        //return getContentPane().getHeight();
    }

    public void expose() {
        thread = new Thread() {
            @Override
            public void run() {
                try {
                    Thread.sleep(t);
                    panel.prepareNext();
                    showImage();
                    expose();
                } catch (Exception e) {
                }
            }
        };
        thread.start();
    }

    void showImage() {
        ajustOverflowImage();
        centrateImagePosition();
        setTitle(panel.getImageName());
        panel.repaint();
    }

    void centerImage() {
        ajustOverflowImage();
        centrateImagePosition();
    }

    void ajustOverflowImage() {
        if (panel.getImageWidth() < getActualWidth() && panel.getImageHeight() < getActualHeight()) {
            if (panel.getImageWidth() > panel.getImageHeight()) {
                panel.setImageSize(getActualWidth(), (getActualWidth() * panel.getOriginalImageHeight() / panel.getOriginalImageWidth()));
            } else {
                panel.setImageSize((getActualHeight() * panel.getOriginalImageWidth() / panel.getOriginalImageHeight()), getActualHeight());
            }
        } 
        if (panel.getImageWidth() > getActualWidth()) {
            panel.setImageSize(getActualWidth(), (getActualWidth() * panel.getImageHeight() / panel.getImageWidth()));
        }
        if (panel.getImageHeight() > getActualHeight()) {
            panel.setImageSize((getActualHeight() * panel.getImageWidth() / panel.getImageHeight()), getActualHeight());
        }
        
    }

    void centrateImagePosition() {
        panel.setImagePosition(
                (getWidth() / 2) - (panel.getImageWidth() / 2),
                (getActualHeight() / 2) - (panel.getImageHeight() / 2)
        );
    }

    private class Panel extends JPanel {

        ImageIcon image;
        ImageIcon original;
        int xPos = 0;
        int yPos = 0;
        int index = 1;

        List<String> listPathTree;

        Panel(List<String> listPathTree) {
            this.listPathTree = listPathTree;
            image = new ImageIcon(listPathTree.get(0));
            original = new ImageIcon(listPathTree.get(0));
        }

        int getImageWidth() {
            return image.getIconWidth();
        }
        int getOriginalImageWidth() {
            return original.getIconWidth();
        }

        int getImageHeight() {
            return image.getIconHeight();
        }
        int getOriginalImageHeight() {
            return original.getIconHeight();
        }

        @Override
        protected void paintComponent(Graphics graphics) {
            super.paintComponent(graphics);
            image.paintIcon(this, graphics, xPos, yPos);
        }

        void setImage(ImageIcon image) {
            this.image = image;
            this.original = image;
        }

        void prepareNext() {
            if (++index > listPathTree.size() - 1) {
                index = 0;
            }
            setImage(new ImageIcon(listPathTree.get(index)));
        }

        void preparePrev() {
            if (--index < 0) {
                index = listPathTree.size() - 1;
            }
            setImage(new ImageIcon(listPathTree.get(index)));
        }

        void setImagePosition(int x, int y) {
            xPos = x;
            yPos = y;
        }

        void setImageSize(int width, int height) {
            Image img = original.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
            image = new ImageIcon(img);
        }

        String getImageName() {
            return listPathTree.get(index);
        }
    }
}
