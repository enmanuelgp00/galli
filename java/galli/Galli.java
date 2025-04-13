package galli;
import galli.gallery.*;
import java.awt.event.*;
import java.io.File;
import javax.swing.*;

public class Galli extends JFrame {
	Galli (File dir) {
		final Curator curator = new Curator(new Gallery(dir)){
			@Override
			public void OnImageExibition(File image){
				setTitle(image.getName());
			}
		};
		JPopupMenu popupMenu = new JPopupMenu();
		popupMenu.add(new JMenuItem("Delete"));	
		add(curator);
		
		curator.addMouseListener( new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (SwingUtilities.isRightMouseButton(e)) {
					popupMenu.show(curator, e.getX(), e.getY());
				} else {
					if (getWidth() / 2 < e.getX()) {
						curator.next();
					} else {
						curator.previous();
					}
				}
			}
		} );
		setSize(500, 500);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}
	public static void main (String[] args) {
		String name;
		if (args.length > 0) {
			name = args[0];
		} else {
			name = ".";
		}
		new Galli( new File(name) );
	}
}
