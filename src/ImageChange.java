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
    //public static ArrayList<Item> receiptItems = new ArrayList<Item>(); // DUPED??

    public static void imageCrop (BufferedImage originalImage, int x, int y, int width, int height){
        try{
            // Creates new image out of the subimage of original image
            BufferedImage cropped = originalImage.getSubimage(x, y, width, height);
            //cropped.createGraphics().drawImage(originalImage.getSubimage(x, y, width, height),null,0,0);

            // Adds the item to the list for user's receipt
            Item toAdd = new Item(cropped);
            Person.receiptItems.add(toAdd);
            System.out.println("Trying to crop");
            System.out.println("Current size: " + Person.receiptItems.size());

            // Save/write cropped image to a file
            File croppedImage = new File ("croppedImage.jpg");
            ImageIO.write(cropped, "jpg", croppedImage);

            // Call cropimage and image stitch from imagepopup file most likely
            
        }catch(IOException error){}
    }

    public static void ImageStitch(){
        
        int width = 0;
        int height = 0;

        // Finds max of the widths of all cropped images to get the width of finalImage
        // Adds the height of all cropped images to get total height of finalImage
        for (Item item : Person.receiptItems){
            if (item.getImage().getWidth() > width){
                width = item.getImage().getWidth();
            }
            height += item.getImage().getHeight();
        }

        // Create finalImage
        BufferedImage finalImage = new BufferedImage(width,height,BufferedImage.TYPE_INT_ARGB);

        // Draw finalImage out of all the cropped images
        int y = 0;

        //for (Item i : Person.receiptItems){
        for (int i = 0; i < Person.receiptItems.size(); i++) {
            finalImage.createGraphics().drawImage(Person.receiptItems.get(i).getImage(), null, 0, y);
            y += Person.receiptItems.get(i).getImage().getHeight();
            System.out.println("Counter check");
        }

        try {
            File image = new File("finalimage.jpg");
            ImageIO.write(finalImage, "jpg", image);
            System.out.println("Write IMAGECHANGE check");
        } catch(IOException error) {}
    }
}
