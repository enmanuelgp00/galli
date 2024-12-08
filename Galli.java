
import java.util.List;
import java.util.ArrayList;
import java.io.File;

public class Galli {
   List<String> listPathTree = new ArrayList<String>();
   final String[] IMAGE_EXTENSIONS = {".jpg", ".gif", ".jpeg", ".png"};
   File root;

   Galli (String[] args) {
      handleArguments(args);
      loadPaths(root);
      if (listPathTree.size() == 0) {
         System.out.println("There is no picture found.");
         System.exit(1);
      }
      shuffle(listPathTree);
      Exhibitor exhibitor = new Exhibitor(listPathTree);
//      exhibitor.expose();   
   }
   public static void main(String[] args) {
      new Galli(args);
   }
   void loadPaths(File dir) {
      if (dir.listFiles().length > 0) {
         for (File f : dir.listFiles()) {
            if (f.isDirectory()) {
               loadPaths(f);
            } else {
               String fullPath = f.getAbsolutePath();
               if(isImage(fullPath)) {
                    listPathTree.add(fullPath);
                  } 
            }
         }
      }      
   }

   boolean isImage (String path) {
      for (String x : IMAGE_EXTENSIONS) {
         if (path.contains(x)) {
            return true;
         }
      }
      return false;
   } 
   
   void handleArguments(String[] args) {
      if (args.length > 0) {
         root = new File(args[0]);
      } else {
         root = new File(".");
      }
      if (!(root.isDirectory())) {
         throw new IllegalArgumentException("\"" + args[0] + "\" should be a directory");
      }
   }
   void shuffle(List<String> list) {
      int j;
      String s;
      int length = list.size();
      for (int i = 0; i < length; i++) {
         j = (int) Math.floor(Math.random() * (i + 1));
         s = list.get(i);
         list.set(i, list.get(j));
         list.set(j, s);
      } 
      
   }
}
