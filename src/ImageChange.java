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
import java.awt.Graphics2D;

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
            //System.out.println("Trying to crop");
            System.out.println("There are currently " + Person.receiptItems.size() + " items selected");

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
        //BufferedImage finalImage = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR); // Had to change from ARGB to be writeable
        BufferedImage finalImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB); // works with png down below, higher quality but larger size?

        // Draw finalImage out of all the cropped images
        int y = 0;

        //for (Item i : Person.receiptItems){
        Graphics2D g2 = finalImage.createGraphics();
        for (int i = 0; i < Person.receiptItems.size(); i++) {
            g2.drawImage(Person.receiptItems.get(i).getImage(), null, 0, y);
            //finalImage.createGraphics().drawImage(Person.receiptItems.get(i).getImage(), null, 0, y);
            y += Person.receiptItems.get(i).getImage().getHeight();
            //System.out.println("Height check: " + y);
        }
        g2.dispose(); // https://stackoverflow.com/questions/20826216/copy-two-bufferedimages-into-one-image-side-by-side

        try {
            //boolean success = ImageIO.write(finalImage, "jpg", new File ("finalimage.jpg"));
            boolean success = ImageIO.write(finalImage, "png", new File ("finalimage.jpg"));
            //System.out.println("Write IMAGECHANGE check " + success);
            if (success) {
                System.out.println("Success! Full image located at finalimage.jpg");
            }
        } catch(IOException error) {}
    }
}
