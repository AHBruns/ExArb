package ExArb.Structures;

public class Order {
    public String type;
    public double price, amount;

    public Order(String t, double p, double q) {
        type = t;
        price = p;
        amount = q;
    }

    @Override
    public String toString() {
        return "(" + type + "|" + price + "|" + amount + ")";
    }
}
