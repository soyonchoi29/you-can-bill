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
    private static CustomerHolder CustomerHolder;
    public static void imageCrop (CustomerHolder customers, BufferedImage originalImage, int x, int y, int width, int height, String name){
        try{
            CustomerHolder = customers;
            // Creates new image out of the subimage of original image
            BufferedImage cropped = originalImage.getSubimage(x, y, width, height);
            //cropped.createGraphics().drawImage(originalImage.getSubimage(x, y, width, height),null,0,0);

            // Adds the item to the list for user's receipt
            Item toAdd = new Item(cropped);
            Customer.receiptItems.add(toAdd);
            System.out.println("Trying to crop");

            // Save/write cropped image to a file
            File croppedImage = new File ("croppedImage.jpg");
            ImageIO.write(cropped, "jpg", croppedImage);

            // Call cropimage and image stitch from imagepopup file most likely
            
        }catch(IOException error){}
    }

    public static void ImageStitch(String name){
        //Testing
        try{
            Customer currentCustomer = CustomerHolder.getCustomer(0);
            currentCustomer = CustomerHolder.getCustomer(CustomerHolder.getIndex("dummy"));
            int width = 0;
            int height = 0;

            // Finds max of the widths of all cropped images to get the width of finalImage
            // Adds the height of all cropped images to get total height of finalImage
            for (Item item : Customer.receiptItems){
                if (item.getImage().getWidth() > width){
                    width = item.getImage().getWidth();
                }
                height += item.getImage().getHeight();
            }

                // Create finalImage
            BufferedImage finalImage = new BufferedImage(width,height,BufferedImage.TYPE_INT_ARGB);

            // Draw finalImage out of all the cropped images
            int y = 0;

            for (int i = 0; i < CustomerHolder.length() - 1; i++) {
                for (int j = 0; j < Customer.receiptItems.size(); j++) {
                    finalImage.createGraphics().drawImage(Customer.receiptItems.get(j).getImage(), null, 0, y);
                    y += Customer.receiptItems.get(j).getImage().getHeight();
                }
            }

            CustomerHolder.getCustomer(0).addStitchedImage(finalImage);

            // Save/write stitched image to a file
            String currentName = CustomerHolder.getCustomer(0).getName() + "StitchedImage.jpg";
            File stitchedImage = new File(currentName);
            System.out.println(currentName);
            ImageIO.write(CustomerHolder.getCustomer(0).getStitched(), "png", stitchedImage); // Can change maybe later
            
        }catch(IOException error){}
    }
}
