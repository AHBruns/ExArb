package ExArb.Structures;

public class Order {
    public String type;
    public double price, quantity;

    public Order(String t, double p, double q) {
        type = t;
        price = p;
        quantity = q;
    }
}
