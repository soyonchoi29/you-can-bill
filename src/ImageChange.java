import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.lang.Math;
import java.lang.ProcessBuilder.Redirect.Type;
import java.util.ArrayList;
import java.awt.Image;
import java.awt.image.BufferedImage;

public class ImageChange {
    public static ArrayList<Item> receiptItems;

    public static void cropImage (File input, int x, int y, int width, int height){
        try{
            BufferedImage originalImage = ImageIO.read(input);
            BufferedImage croppedImage = originalImage.getSubimage(x, y, width, height);
            Item toAdd = new Item(croppedImage);
            receiptItems.add(toAdd);
            // Call cropimage and image stitch from imagepopup file most likely
        }catch(IOException error){}
    }

    public static void ImageStitch(){
        
        int width = 0;
        int height = 0;

        //Finds max of the widths of all cropped images to get the width of finalImage
        //Adds the height of all cropped images to get total height of finalImage
        for (Item item : receiptItems){
            if (item.getImage().getWidth() > width){
                width = item.getImage().getWidth();
            }
            height += item.getImage().getHeight();
        }

        //Create finalImage
        BufferedImage finalImage = new BufferedImage(width,height,BufferedImage.TYPE_INT_ARGB);

        //Draw finalImage out of all the cropped images
        int y = 0;

        for (int i = 0 ; i < receiptItems.size() ; i++){
            finalImage.createGraphics().drawImage(receiptItems.get(i).getImage(), null, 0, y);
            y += receiptItems.get(i).getImage().getHeight();
        }
    }
}
