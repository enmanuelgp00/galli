package galli;
import galli.gallery.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
		JMenuBar menuBar = new JMenuBar();
		JMenu menu = new JMenu("File");
		menuBar.add(menu);
		JMenuItem item = new JMenuItem("Delete");
		item.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				curator.delete();
			}
		});
		menu.add(item);
		add(curator);
		setJMenuBar(menuBar);
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
