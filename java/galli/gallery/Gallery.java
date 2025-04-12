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
		
	}
	private boolean isImage(File file) {
		for (String extension : new String[] { "jpg", "png", "jpeg", "gif" } )
		if (file.getName().contains(extension)) {
			return true;
		}
		return false;
	}
}
