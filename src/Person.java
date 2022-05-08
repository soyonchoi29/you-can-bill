import java.util.ArrayList;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Person {
    private final String name;
    private int bill;

    public static ArrayList<Item> receiptItems = new ArrayList<Item>();
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

    //Method to test if images are saves
    public static void saved() {
        try{
            File savedimage = new File ("savedimage.jpg");
            ImageIO.write(receiptItems.get(0).getImage(), "jpg", savedimage);
        }catch(IOException error){}
    }
}