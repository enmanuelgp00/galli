package galli.gallery;
import galli.gallery.image.ImageChecker;
import java.io.File;
import java.util.ArrayList;

public class Gallery extends ArrayList<File> {
	public Gallery (File dir) {
		addEveryImageIn(dir);
	}

	private void addEveryImageIn(File file) {
		if (file.isDirectory()) {
			for (File f : file.listFiles()) {
				addEveryImageIn(f);
			}
		} else {
			if (ImageChecker.isImageFile(file)) add(file);
		}
	}
	public void shuffle() {
		for (int i = size() - 1; i > 0; i--) {
			int f = (int) (Math.random() * i);
			File s = get(i);
			set(i, get(f));
			set(f, s);
		}
	}
}
