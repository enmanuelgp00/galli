package galli;
import galli.gallery.*;
import java.awt.event.*;
import java.io.File;
import javax.swing.*;

public class Galli extends JFrame {
	Galli (File dir) {
		final Curator curator = new Curator(new Gallery(dir)){
			@Override
			public void newImageSelected(File image){
				setTitle("Loading image ... ");
			}
			@Override
			public void newImageLoaded(File image) {
				setTitle(image.getName());
			}
		};
		JPopupMenu popupMenu = new JPopupMenu();
		JMenuItem itemDel = new JMenuItem("Delete");
		itemDel.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e){
				curator.deleteCurrentImage();
			}
		});
		popupMenu.add(itemDel);
		add(curator);
		curator.addMouseListener( new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (SwingUtilities.isRightMouseButton(e)) {
					popupMenu.show(curator, e.getX(), e.getY());
				} else {
					if (getWidth() - getWidth() / 3 < e.getX()) {
						curator.next();
					} else if (getWidth() / 3 > e.getX() ) {
						curator.previous();
					}
				}
			}
		} );
		setSize(500, 500);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//pack();
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
