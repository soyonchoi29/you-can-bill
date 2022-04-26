
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

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
            JLabel picLabel = new JLabel(new ImageIcon(myPicture));
            panel.add(picLabel);
            frame.add(panel);
            frame.setSize(500, 500); // perhaps cropping is drawing new jpanels on top or something
            frame.setVisible(true); // will need to recenter receipt better most likely
        } catch (IOException error) {}
    }
}

//Based on https://www.javaexercise.com/java/add-an-image-to-a-jpanel - citation needed