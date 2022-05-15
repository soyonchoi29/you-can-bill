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
        CustomerHolder = customers;
        // Creates new image out of the subimage of original image
        BufferedImage cropped = originalImage.getSubimage(x, y, width, height);

        // Adds the item to the list for user's receipt
        Item toAdd = new Item(cropped);
        Customer.receiptItems.add(toAdd);
        System.out.println("Trying to crop");
    }

    public static void ImageStitch(String name){
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

        // Create BufferedImage to hold finalImage
        BufferedImage finalImage = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);

        // Draw finalImage out of all the cropped images
        int y = 0;

        for (int i = 0; i < CustomerHolder.length() - 1; i++) {
            for (int j = 0; j < Customer.receiptItems.size(); j++) {
                finalImage.createGraphics().drawImage(Customer.receiptItems.get(j).getImage(), null, 0, y);
                y += Customer.receiptItems.get(j).getImage().getHeight();
            }
        }

        CustomerHolder.getCustomer(0).addStitchedImage(finalImage);
        try{
            // Save stitched image to a file and clear list of items
            String currentName = name + "StitchedImage.jpg";
            File stitchedImage = new File(currentName);
            ImageIO.write(CustomerHolder.getCustomer(0).getStitched(), "jpg", stitchedImage);
            Customer.receiptItems.clear();
        }catch(IOException error){}

        // Display to user
        JFrame userFrame = new JFrame("Selected Items");
        JPanel userPanel = new JPanel();
        JLabel userLabel = new JLabel();

        int scaledImageWidth = width;
        int scaledImageHeight = height;
        Image scaledImage = finalImage;

        // Scale to fit screen
        if (width > 800 || height > 800) {
            float WoverH = width/height;
            if (WoverH >= 1){
                while (scaledImageWidth > 800) {
                    scaledImageWidth *= 0.9;
                    scaledImageHeight *= 0.9;
                }
            }
            if (WoverH < 1){
                while (scaledImageHeight > 800){
                    scaledImageWidth *= 0.9;
                    scaledImageHeight *= 0.9;
                }
            }
            scaledImage = finalImage.getScaledInstance(scaledImageWidth, scaledImageHeight, Image.SCALE_SMOOTH);
        }

        // Creates panel that will display stitched image
        userPanel.setSize(scaledImageWidth, scaledImageHeight);
        userFrame.setSize(scaledImageWidth + 50, scaledImageHeight + 50);
        userLabel.setIcon(new ImageIcon(scaledImage));
        userPanel.add(userLabel);
        userFrame.add(userPanel);
        userFrame.setVisible(true);
    }
}
