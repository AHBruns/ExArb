package ExArb.Structures;

import java.util.ArrayList;

public class Market {

    public Integer market_id, currency_a, currency_b, trade_count;
    public Double last_price, change, high_price, low_price, volume, btc_volume, bid_price, ask_price;
    public ArrayList<Order> buy_orders, sell_orders;

    public Market(int market_id) {
        this.market_id = market_id;
    }

    public Market(int market_id, int currency_a, int currency_b) {
        this.market_id = market_id;
        this.currency_a = currency_a;
        this.currency_b = currency_b;
    }

    public Market(int market_id, int currency_a, int currency_b, double bid_price, double ask_price) {
        this.market_id = market_id;
        this.currency_a = currency_a;
        this.currency_b = currency_b;
        this.bid_price = bid_price;
        this.ask_price = ask_price;
    }

    public Market(int market_id, int currency_a, int currency_b, double bid_price, double ask_price,
                  ArrayList<Order> buy_orders, ArrayList<Order> sell_orders) {
        this.market_id = market_id;
        this.currency_a = currency_a;
        this.currency_b = currency_b;
        this.bid_price = bid_price;
        this.ask_price = ask_price;
        this.buy_orders = buy_orders;
        this.sell_orders = sell_orders;
    }

    public Market(int market_id, int currency_a, int currency_b, double bid_price, double ask_price,
                  ArrayList<Order> buy_orders, ArrayList<Order> sell_orders, int trade_count, double last_price,
                  double change, double high_price, double low_price, double volume, double btc_volume) {
        this.market_id = market_id;
        this.currency_a = currency_a;
        this.currency_b = currency_b;
        this.bid_price = bid_price;
        this.ask_price = ask_price;
        this.buy_orders = buy_orders;
        this.sell_orders = sell_orders;
        this.trade_count = trade_count;
        this.last_price = last_price;
        this.change = change;
        this.high_price = high_price;
        this.low_price = low_price;
        this.volume = volume;
        this.btc_volume = btc_volume;
    }

    public boolean isShallowComplete() {
        if (market_id != null &&
                currency_a != null &&
                currency_b != null &&
                bid_price != null &&
                ask_price != null) {
            return true;
        }
        return false;
    }

    public boolean isDeepComplete() {
            return isShallowComplete() && buy_orders != null && sell_orders != null;
    }

    public boolean isComplete() {
        return isDeepComplete() && trade_count != null && last_price != null && change != null && high_price != null &&
                low_price != null && volume != null && btc_volume != null;
    }

}
