package ExArb.Pathing;

import ExArb.Networking.NetworkManager;
import ExArb.Networking.Parsers.GetOrderBookCompanion;
import ExArb.Structures.Market;
import ExArb.Structures.Order;
import ExArb.Structures.State;

import java.util.ArrayList;
import java.time.Clock;

public class PathingManager {

    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_RESET = "\u001B[0m";

    private State state;
    private ArrayList<ArrayList<Market>> bbs_paths = new ArrayList<>();
    private ArrayList<ArrayList<Market>> bss_paths = new ArrayList<>();

    public PathingManager(State s) {
        this.state = s;
    }

    public void buildPaths() {
        System.out.printf("%sStarting Paths Build%s\n", ANSI_YELLOW, ANSI_RESET);
        for (Market m1 : state.markets.values()) {
            for (Market m2 : m1.getAssociatedMarkets()) {
                for (Market m3 : m2.getAssociatedMarkets()) {
                    if (m1.currency_a == m2.currency_b && m2.currency_a == m3.currency_a &&
                            m3.currency_b == m1.currency_b && m1 != m2 && m2 != m3 && m3 != m1 &&
                            m1.isTradable() && m2.isTradable() && m3.isTradable()) {
                        ArrayList<Market> x = new ArrayList<>();
                        x.add(m1);
                        x.add(m2);
                        x.add(m3);
                        bbs_paths.add(x);
                    } else if (m1.currency_a == m2.currency_a && m2.currency_b == m3.currency_a &&
                            m3.currency_b == m1.currency_b && m1 != m2 && m2 != m3 && m3 != m1 &&
                            m1.isTradable() && m2.isTradable() && m3.isTradable()) {
                        ArrayList<Market> x = new ArrayList<>();
                        x.add(m1);
                        x.add(m2);
                        x.add(m3);
                        bss_paths.add(x);
                    }
                }
            }
        }
    }

    public void checkPaths(NetworkManager nm) throws Exception {
        System.out.printf("%sStarting BBS Path Checks%s\n", ANSI_YELLOW, ANSI_RESET);
        for (ArrayList<Market> path : bbs_paths) {
            GetOrderBookCompanion.parse(path.get(0).id, nm.ExecuteGetOrderBook(path.get(0).id), state);
            GetOrderBookCompanion.parse(path.get(1).id, nm.ExecuteGetOrderBook(path.get(1).id), state);
            GetOrderBookCompanion.parse(path.get(2).id, nm.ExecuteGetOrderBook(path.get(2).id), state);
            double simPercentReturn = simBBSPath(path);
            if (simPercentReturn > 0.0 && simPercentReturn < 1) {
                System.out.printf("%24s | return: %.5f%% | %8s/%-8s  ->  %8s/%-8s  ->  %8s/%-8s |\n",
                        String.valueOf(Clock.systemUTC().instant()),
                        simPercentReturn,
                        path.get(0).currency_b.ticker,
                        path.get(0).currency_a.ticker,
                        path.get(1).currency_b.ticker,
                        path.get(1).currency_a.ticker,
                        path.get(2).currency_b.ticker,
                        path.get(2).currency_a.ticker);
            } else if (simPercentReturn >= 1) {
                do {
                    System.out.printf("\n%24s%s | return: %.5f%% | %8s/%-8s  ->  %8s/%-8s  ->  %8s/%-8s | opportunity!%s\n\n\u0007",
                            ANSI_GREEN,
                            String.valueOf(Clock.systemUTC().instant()),
                            simPercentReturn,
                            path.get(0).currency_b.ticker,
                            path.get(0).currency_a.ticker,
                            path.get(1).currency_b.ticker,
                            path.get(1).currency_a.ticker,
                            path.get(2).currency_b.ticker,
                            path.get(2).currency_a.ticker,
                            ANSI_RESET);
                    GetOrderBookCompanion.parse(path.get(0).id, nm.ExecuteGetOrderBook(path.get(0).id), state);
                    GetOrderBookCompanion.parse(path.get(1).id, nm.ExecuteGetOrderBook(path.get(1).id), state);
                    GetOrderBookCompanion.parse(path.get(2).id, nm.ExecuteGetOrderBook(path.get(2).id), state);
                } while (simBBSPath(path) >= 1);
            } else {
                System.out.printf("%24s | empty order book | %8s/%-8s  ->  %8s/%-8s  ->  %8s/%-8s |\n",
                        String.valueOf(Clock.systemUTC().instant()),
                        path.get(0).currency_b.ticker,
                        path.get(0).currency_a.ticker,
                        path.get(1).currency_b.ticker,
                        path.get(1).currency_a.ticker,
                        path.get(2).currency_b.ticker,
                        path.get(2).currency_a.ticker);
            }
        }
    }

    public Double simBBSPath(ArrayList<Market> path) {
        try {
            double initialValue = 0.0001;
            double minValue = 0.0001;
            Double m1price = flattenOrderBookToPrice(path.get(0).sell_orders, minValue);
            if (m1price == null) { return 0.0; }
            double m1quant = initialValue / m1price;
            Double m2price = flattenOrderBookToPrice(path.get(1).sell_orders, minValue);
            if (m2price == null) { return 0.0; }
            double m2quant = m1quant / m2price;
            Double m3price =  flattenOrderBookToPrice(path.get(2).buy_orders, minValue);
            if (m3price == null) { return 0.0; }
            double m3quant = m2quant * m3price;
            return (m3quant * 0.99550674662) / initialValue;
        } catch (IndexOutOfBoundsException e) { }
        return 0.0;
    }

    public Double flattenOrderBookToPrice(ArrayList<Order> orderBook, double targetQuant) {
        double price = 0.0;
        double curQuant = 0.0;
        double quantDelta = targetQuant - curQuant;
        for (Order o : orderBook) {
            if (o.quantity < quantDelta) {
                curQuant += o.quantity;
                price += o.price * (o.quantity / targetQuant);
            } else if (o.quantity >= quantDelta) {
                curQuant = targetQuant;
                price += o.price * (quantDelta / targetQuant);
            }
            quantDelta = targetQuant - curQuant;
        }
        if (quantDelta > 0) {
            return null;
        }
        return price;
    }
}
