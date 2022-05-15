import java.awt.image.BufferedImage;

public class Item {
    private float price;
    private Customer payer;
    private BufferedImage image;

    public Item(BufferedImage image){
        this.image = image;
    }

    public BufferedImage getImage(){
        return this.image;
    }
}