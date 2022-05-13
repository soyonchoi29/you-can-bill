import java.util.ArrayList;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public interface Customer {
    public static ArrayList<Item> receiptItems = new ArrayList<Item>();
    public float getBiller();
    public float addToBill();
    public String getName();
    public void addStitchedImage(BufferedImage img);
    public BufferedImage getStitched();
    public boolean isCredit();
}

class CreditUser implements Customer {
    private final String name;
    private int bill;
    private BufferedImage stitched;

    public CreditUser(String name) {
        this.name = name;
    }

    public float getBiller() {
        return bill;
    }

    public float addToBill() {
        return bill;
    }

    public String getName() {
        return name;
    }

    public void addStitchedImage(BufferedImage img) {
        this.stitched = img;
    }

    public BufferedImage getStitched() {
        return this.stitched;
    }

    public boolean isCredit() {return true;}
}

class CashPayer implements Customer {
    private final String name;
    private int bill;
    private BufferedImage stitched;

    public CashPayer(String name) {
        this.name = name;
    }

    public float getBiller() {
        return bill;
    }
    
    public float addToBill() {
        return bill;
    }

    public String getName() {
        return name;
    }

    public void addStitchedImage(BufferedImage img) {
        this.stitched = img;
    }

    public BufferedImage getStitched() {
        return this.stitched;
    }

    public boolean isCredit() {return false;}
}