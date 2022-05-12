import java.util.ArrayList;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Person {

    public ArrayList<Item> receiptItems = new ArrayList<Item>();
    private final String name;
    private int bill;
    private BufferedImage stitched;
    public Person(String name) {
        this.name = name;
    }

    public float getBill() {
        return (float) 0.0;
    }

    public float addToBill() {
        return (float) 0.0;
    }

    public String getName() {
        return this.name;
    }

    public void addStitchedImage(BufferedImage img) {
        this.stitched = img;
    }

    public BufferedImage getStitched() {
        return this.stitched;
    }

}