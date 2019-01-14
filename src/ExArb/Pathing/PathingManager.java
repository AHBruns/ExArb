package ExArb.Pathing;

import ExArb.Networking.NetworkManager;
import ExArb.Networking.Parsers.GetOrderBookCompanion;
import ExArb.Structures.Market;
import ExArb.Structures.State;

import java.io.IOException;
import java.util.ArrayList;

public class PathingManager {

    private State state;
    private ArrayList<ArrayList<Market>> bbs_paths = new ArrayList<>();
    private ArrayList<ArrayList<Market>> bss_paths = new ArrayList<>();

    public PathingManager(State s) {
        this.state = s;
    }

    public void buildPaths() {
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
        System.out.println("Starting BBS Path Checks");
        for (ArrayList<Market> path : bbs_paths) {
            GetOrderBookCompanion.parse(path.get(0).id, nm.ExecuteGetOrderBook(path.get(0).id), state);
            GetOrderBookCompanion.parse(path.get(1).id, nm.ExecuteGetOrderBook(path.get(1).id), state);
            GetOrderBookCompanion.parse(path.get(2).id, nm.ExecuteGetOrderBook(path.get(2).id), state);
            double simPercentReturn = simBBSPath(path);
            if (simPercentReturn >= .5) {
                System.out.println("return: " + simPercentReturn + " | " +
                        path.get(0).currency_b.ticker + "/" + path.get(0).currency_a.ticker + " -> " +
                        path.get(1).currency_b.ticker + "/" + path.get(1).currency_a.ticker + " -> " +
                        path.get(2).currency_b.ticker + "/" + path.get(2).currency_a.ticker);
            }
        }
    }

    public Double simBBSPath(ArrayList<Market> path) {
        // TODO | flatten order books and sim fees
        try {
            double initialValue = 1;
            double m1quant = initialValue / path.get(0).sell_orders.get(0).price;
            double m2quant = m1quant / path.get(1).sell_orders.get(0).price;
            double m3quant = m2quant * path.get(2).buy_orders.get(0).price;
            return m3quant / initialValue;
        } catch (IndexOutOfBoundsException e) { }
        return 0.0;
    }

//    public Double simBSSPath(ArrayList<Market> path) {
//        try {
//            // TODO | flatten order books and sim fees
//            double initialValue = 1;
//            double m1quant = initialValue / path.get(0).sell_orders.get(0).price;
//            double m2quant = m1quant * path.get(1).buy_orders.get(0).price;
//            double m3quant = m2quant * path.get(2).buy_orders.get(0).price;
//            return m3quant / initialValue;
//        } catch (IndexOutOfBoundsException e) { System.out.print("empty order book! "); }
//        return 0.0;
//    }
}
