
public class Item {
    private final String name;
    private int quantity;
    private float price;
    private Person payer;

    public Item(String name, int quantity, float price) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }
}