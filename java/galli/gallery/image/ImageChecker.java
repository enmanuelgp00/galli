package galli.gallery.image;
import java.io.File;
import java.util.Arrays;
import javax.imageio.stream.FileImageInputStream;

public class ImageChecker {
    public static boolean isImageFile(File file) {
        try{
            FileImageInputStream stream = new FileImageInputStream(file);
            byte[] header = new byte[8];
            if (stream.read(header) != header.length)  {
                return false;
            }
            return isJpeg(header) || isPng(header) || isGif(header) || isBmp(header);
        } catch (Exception e) {
            return false;
        }
    }
    private static boolean isJpeg(byte[] header) {
        return 
            header[0] == (byte) 0xff && 
            header[1] == (byte) 0xd8 &&
            header[2] == (byte) 0xff;
    }
    private static boolean isPng(byte[] header) {
        byte[] pngSignature = {(byte) 0x89, 0x50, 0x4e, 0x47, 0x0d, 0x0a, 0x1a, 0x0a};
        return Arrays.equals(header, pngSignature);

    }
    private static boolean isGif(byte[] header) {
        return 
            header[0] == 'G' &&
            header[1] == 'I' &&
            header[2] == 'F' &&
            header[3] == '8';
    }
    public static boolean isImageGif(File file){
        try {
            byte[] header = new byte[8];
            FileImageInputStream stream = new FileImageInputStream(file);
            if (stream.read(header) != header.length) {
                return false;

            } else {
                return isGif(header);
            }
            
        } catch (Exception e) {
            return false;
        }
    }
    private static boolean isBmp(byte[] header) {
        return 
            header[0] == 'B' &&
            header[1] == 'M';
    }
}