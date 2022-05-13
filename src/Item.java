import java.awt.image.BufferedImage;

public class Item {
    //private final String name;
    //private int quantity;
    private float price;
    private Customer payer;
    private BufferedImage image;

    //public Item(String name, int quantity, float price) {
    //    this.name = name;
    //    this.quantity = quantity;
    //    this.price = price;
    //}

    public Item(BufferedImage image){
        this.image = image;
    }

    public BufferedImage getImage(){
        return this.image;
    }
}