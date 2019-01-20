package ExArb.Pathing;

import ExArb.Execution.ExecutionEndpoints.AddOrder;
import ExArb.Execution.ExecutionManager;
import ExArb.Networking.NetworkManager;
import ExArb.Networking.Parsers.GetOrderBookCompanion;
import ExArb.Structures.Market;
import ExArb.Structures.Order;
import ExArb.Structures.PathExecution;
import ExArb.Structures.State;

import java.util.ArrayList;
import java.time.Clock;

public class PathingManager {

    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_UNDERLINE = "\u001B[4m";



    private State state;
    private ArrayList<ArrayList<Market>> bbs_paths = new ArrayList<>();
    private ArrayList<ArrayList<Market>> bss_paths = new ArrayList<>();

    public PathingManager(State s) {
        this.state = s;
    }

    public void buildPaths() {
        System.out.printf("%sStarting Paths Build%s\n", ANSI_YELLOW, ANSI_RESET);
        bbs_paths = new ArrayList<>();
        for (Market m1 : state.markets.values()) {
            for (Market m2 : m1.getAssociatedMarkets()) {
                for (Market m3 : m2.getAssociatedMarkets()) {
                    if (m1.currency_b.ticker.equals("BTC") && m1.currency_a == m2.currency_b && m2.currency_a == m3.currency_a &&
                            m3.currency_b == m1.currency_b && m1 != m2 && m2 != m3 && m3 != m1 &&
                            m1.isTradable() && m2.isTradable() && m3.isTradable()) {
                        ArrayList<Market> x = new ArrayList<>();
                        x.add(m1);
                        x.add(m2);
                        x.add(m3);
                        bbs_paths.add(x);
                    } else if (m1.currency_b.ticker.equals("BTC") && m1.currency_a == m2.currency_a && m2.currency_b == m3.currency_a &&
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

    public void checkPaths(NetworkManager nm, AddOrder ao) throws Exception {
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
            } else if (simPercentReturn > 1) {
                System.out.printf("\n%s%24s | return: %.5f%% | %8s/%-8s  ->  %8s/%-8s  ->  %8s/%-8s | opportunity!%s\n",
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
                System.out.printf("%sExecuting BBS Path. Standby.%s\n\n", ANSI_GREEN, ANSI_RESET);
                try {
                    System.out.println(String.valueOf(Clock.systemUTC().instant()));
                    PathExecution pe = executionSimBSSPath(path, ao);
                    ExecutionManager.ExecuteBBSPath(pe);
                } catch (Exception e) {
                    e.printStackTrace();
                    System.exit(-1);
                }
//                System.exit(1);
            } else {
                System.out.printf("%24s | %sempty order book%s | %8s/%-8s  ->  %8s/%-8s  ->  %8s/%-8s |\n",
                        String.valueOf(Clock.systemUTC().instant()),
                        ANSI_UNDERLINE,
                        ANSI_RESET,
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
//            System.out.println(path.get(0).sell_orders);
            Double m1price = flattenOrderBookToPriceBuyBook(path.get(0).sell_orders, minValue);
            if (m1price == null) { return 0.0; }
            double m1quant = initialValue / m1price;
//            System.out.println(path.get(1).sell_orders);
            Double m2price = flattenOrderBookToPriceBuyBook(path.get(1).sell_orders, m1quant/1.0015);
            if (m2price == null) { return 0.0; }
            double m2quant = m1quant / m2price;
//            System.out.println(path.get(2).
// buy_orders);
            Double m3price =  flattenOrderBookToPriceSellBook(path.get(2).buy_orders, m2quant);
            if (m3price == null) { return 0.0; }
            double m3quant = m2quant * m3price;
            return (m3quant * 0.99550674662) / initialValue;
        } catch (IndexOutOfBoundsException e) { e.printStackTrace(); }
        return 0.0;
    }

    public PathExecution executionSimBSSPath(ArrayList<Market> path, AddOrder ao) {
        ArrayList<Integer> ids = new ArrayList<>();
        ArrayList<Double> amounts = new ArrayList<>();
        ArrayList<Double> prices = new ArrayList<>();
        ArrayList<String> currencyATickers = new ArrayList<>();
        ArrayList<String> currencyBTickers = new ArrayList<>();
        ids.add(path.get(0).id);
        ids.add(path.get(1).id);
        ids.add(path.get(2).id);
        currencyATickers.add(path.get(0).currency_a.ticker);
        currencyATickers.add(path.get(1).currency_a.ticker);
        currencyATickers.add(path.get(2).currency_a.ticker);
        currencyBTickers.add(path.get(0).currency_b.ticker);
        currencyBTickers.add(path.get(1).currency_b.ticker);
        currencyBTickers.add(path.get(2).currency_b.ticker);
        System.out.println(path.get(0).sell_orders);
        System.out.println(path.get(1).sell_orders);
        System.out.println(path.get(2).buy_orders);
        Double price1 = orderBookToBuyPrice(path.get(0).sell_orders, 0.00010002);
        if (price1 == null) { return null; }
        Double avgPrice1 = flattenOrderBookToPriceBuyBook(path.get(0).sell_orders, 0.00010002);
        if (avgPrice1 == null) { return null; }
        double amount1 = 0.00010002 / avgPrice1;
        System.out.println(price1 + " " + avgPrice1 + " " + amount1);
        amounts.add(amount1);
        prices.add(price1);
        Double price2 = orderBookToBuyPrice(path.get(1).sell_orders, (amount1 / 1.0015));
        if (price2 == null) { return null; }
        Double avgPrice2 = flattenOrderBookToPriceBuyBook(path.get(1).sell_orders, (amount1 / 1.0015));
        if (avgPrice2 == null) { return null; }
        double amount2 = (amount1 / 1.0015) / avgPrice2;
        System.out.println(price2 + " " + avgPrice2 + " " + amount2);
        amounts.add(amount2);
        prices.add(price2);
        double amount3 = amount2;
        Double price3 = orderBookToSellPrice(path.get(2).buy_orders, amount3);
        if (price3 == null) { return null; }
        System.out.println(price3);
        amounts.add(amount3);
        prices.add(price3);
        assert (amount3 * price3 * 0.9985) > (0.00010002 * 1.0015); // this must be a profitable execution
        return new PathExecution(ids, amounts, prices, currencyATickers, currencyBTickers, ao);
    }

    public Double orderBookToBuyPrice(ArrayList<Order> orderBook, double targetQuant) {
        double curQuant = 0.0;
        double quantDelta = targetQuant - curQuant;
        for (Order o : orderBook) {
            if (o.amount * o.price < quantDelta) {
                curQuant += (o.amount * o.price);
            } else if (o.amount * o.price >= quantDelta) {
                return o.price;
            }
            quantDelta = targetQuant - curQuant;
        }
        return null;
    }

    public Double orderBookToSellPrice(ArrayList<Order> orderBook, double targetQuant) {
        double curQuant = 0.0;
        double quantDelta = targetQuant - curQuant;
        for (Order o : orderBook) {
            if (o.amount < quantDelta) {
                curQuant += o.amount;
            } else if (o.amount >= quantDelta) {
                return o.price;
            }
            quantDelta = targetQuant - curQuant;
        }
        return null;
    }

    public Double flattenOrderBookToPriceBuyBook(ArrayList<Order> orderBook, double targetQuant) {
//        System.out.println("initial target quant: " + targetQuant);

        double price = 0.0;
        double curQuant = 0.0;
        double quantDelta = targetQuant - curQuant;

        for (Order o : orderBook) {
            if ((o.amount * o.price) < quantDelta) {
//                System.out.println("not enough");
                curQuant += (o.amount * o.price);
                price += (o.price * ((o.amount * o.price) / targetQuant));
            } else if (o.amount * o.price >= quantDelta) {
//                System.out.println("enough");
                curQuant = targetQuant;
                price += (o.price * (quantDelta / targetQuant));
                quantDelta = targetQuant - curQuant;
                break;
            }
            quantDelta = targetQuant - curQuant;
        }
        if (quantDelta > 0) {
//            System.out.println(quantDelta);
            return null;
        }
        return price;
    }

    public Double flattenOrderBookToPriceSellBook(ArrayList<Order> orderBook, double targetQuant) {
//        System.out.println("initial target quant: " + targetQuant);

        double price = 0.0;
        double curQuant = 0.0;
        double quantDelta = targetQuant - curQuant;

        for (Order o : orderBook) {
            if (o.amount < quantDelta) {
//                System.out.println("not enough");
                curQuant += o.amount;
                price += (o.price * (o.amount / targetQuant));
            } else if (o.amount >= quantDelta) {
//                System.out.println("enough");
                curQuant = targetQuant;
                price += (o.price * (quantDelta / targetQuant));
                quantDelta = targetQuant - curQuant;
                break;
            }
            quantDelta = targetQuant - curQuant;
        }
        if (quantDelta > 0) {
//            System.out.println(quantDelta);
            return null;
        }
        return price;
    }
}
