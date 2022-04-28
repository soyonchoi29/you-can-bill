
import java.awt.image.BufferedImage;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.lang.Math;

/*
 *  Code example to add an image into JPanel
 */
public class ImagePopup {
    //public static void main(String[] args) throws IOException {
    //}

    public static void drawNew(File input) {
        try {
            JFrame frame = new JFrame("Selected receipt"); // change later???
            JPanel panel = new JPanel();
            BufferedImage myPicture = ImageIO.read(input);
            Image resized = myPicture.getScaledInstance(500, 500, 2); // Resizes image to fit entire upload into JPanel

            AffineTransform transform = AffineTransform.getRotateInstance(Math.toRadians (90), 250, 250); // ROTATE BY 90 (CAN CHANGE)
            AffineTransformOp operation = new AffineTransformOp(transform, AffineTransformOp.TYPE_BILINEAR);
            BufferedImage rotated = new BufferedImage(500, 500, BufferedImage.TYPE_INT_ARGB); // Creates new BufferedImage, then fills it in with rotated/scaled version
            Graphics2D redraw = rotated.createGraphics();
            redraw.drawImage(resized, 0, 0, null);
            redraw.dispose();

            JLabel picLabel = new JLabel(new ImageIcon(operation.filter(rotated, null)));
            panel.add(picLabel);
            frame.add(panel);
            frame.setSize(500, 500); // perhaps cropping is drawing new jpanels on top or something
            frame.setVisible(true); // will need to recenter receipt better most likely
        } catch (IOException error) {}
    }
}

//Based on https://www.javaexercise.com/java/add-an-image-to-a-jpanel - citation needed
//https://docs.oracle.com/javase/7/docs/api/java/awt/Image.html#getScaledInstance(int,%20int,%20int)
// https://mkyong.com/java/how-to-resize-an-image-in-java/
//https://stackoverflow.com/questions/13605248/java-converting-image-to-bufferedimage
//https://stackoverflow.com/questions/8639567/java-rotating-images