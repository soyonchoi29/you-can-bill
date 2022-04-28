import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.lang.Math;
import java.awt.image.BufferedImage;

public class ImageChange {
    
    public static BufferedImage cropImage (File input, int x, int y, int width, int height){
        try{
            BufferedImage originalImage = ImageIO.read(input);
            BufferedImage croppedImage = originalImage.getSubimage(x, y, width, height);
            return croppedImage; // Call cropimage and image stitch from imagepopup file most likely
        }catch(IOException error){return null;}
    }

    public static void ImageStitch(){

    }
}
