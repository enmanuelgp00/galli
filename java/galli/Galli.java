package galli;
import galli.gallery.*;
import java.io.File;
import javax.swing.*;

public class Galli extends JFrame {
	Galli (File dir) {
		Curator curator = new Curator(new Gallery(dir));
		add(curator);		
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
