import javax.swing.JFrame;
import javax.swing.JMenuItem;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.util.ArrayList;
import java.io.File;

public class Galli extends JFrame implements ActionListener{
	final ImageDealer imageDealer;
	private Galli (String dirPath) {
		File file = new File(dirPath);
		if (!file.isDirectory()) {
			System.out.println(file.getName() + " is not a directory");
			System.exit(1);
		}
		ArrayList<File> imageShelf = getImageCollection(file);
		suffle(imageShelf);
		imageDealer = new ImageDealer(imageShelf);
		MenuBar menuBar = new MenuBar(this);
		setJMenuBar(menuBar);		
		add(imageDealer);
		setSize(1100,600);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if (((JMenuItem)e.getSource()).getText().equals("Delete")) {
			imageDealer.deleteImage();
		}
	}
	private ArrayList<File> getImageCollection(File file) {
		ArrayList<File> imageCollection = new ArrayList<File>();
		if (file.listFiles().length > 0) {
			for (File f : file.listFiles()) {
				if (isImage(f)) {
					imageCollection.add(f);
				} else if (f.isDirectory()) {
					imageCollection.addAll(getImageCollection(f));
				}
			}
		}
		return imageCollection;
	}

	public boolean isImage(File file) {
		String[] imageExtensions = {"jpg", "jpeg", "png", "gif"};
		for (String ex : imageExtensions) {
			if (file.getName().contains(ex)){
				return true;
			}
		}
		return false;
	}

	public static void main(String[] args) {
		new Galli(args.length > 0 ? args[0]: ".");
	}
	private void suffle(ArrayList<File> list) {
		int j;
		File f;
		for (int i = 0; i < list.size(); i++) {
			j = (int) Math.floor(Math.random() * (i + 1) );
			f = list.get(i);
			list.set(i, list.get(j));
			list.set(j, f);
		}
	}
}
























