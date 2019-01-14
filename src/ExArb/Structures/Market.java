package ExArb.Structures;

import java.util.ArrayList;

public class Market {

    public Integer id;
    public Currency currency_a, currency_b;
    public ArrayList<Order> buy_orders, sell_orders;
    public Boolean active;

    public Market(int id) {
        this.id = id;
    }

    public Market(int id, boolean active) {
        this.id = id;
        this.active = active;
    }


    public Market(int id, boolean active, Currency currency_a, Currency currency_b) {
        this.id = id;
        this.currency_a = currency_a;
        this.currency_b = currency_b;
        this.active = active;
    }

    public Market(int id,
                  boolean active,
                  Currency currency_a,
                  Currency currency_b,
                  ArrayList<Order> buy_orders,
                  ArrayList<Order> sell_orders) {
        this.id = id;
        this.active = active;
        this.currency_a = currency_a;
        this.currency_b = currency_b;
        this.buy_orders = buy_orders;
        this.sell_orders = sell_orders;

    }

    public boolean isShallowComplete() {
        if (id != null &&
                currency_a != null &&
                currency_b != null &&
                active != null) {
            return true;
        }
        return false;
    }

    public boolean isDeepComplete() {
            return isShallowComplete() && buy_orders != null && sell_orders != null;
    }

    public boolean isTradable() {
        if (currency_a != null && currency_b != null && active) {
            return currency_a.isComplete() && currency_a.status && currency_b.isComplete() && currency_b.status;
        }
        return false;
    }

    public void update(Market m) {
        if (m.id != null) { id = m.id; }
        if (m.currency_a != null) { currency_a = m.currency_a; }
        if (m.currency_b != null) { currency_b = m.currency_b; }
        if (m.buy_orders != null) { buy_orders = m.buy_orders; }
        if (m.sell_orders != null) { sell_orders = m.sell_orders; }
        if (m.active != null) { active = m.active; }
    }

    public ArrayList<Market> getAssociatedMarkets() {
        ArrayList<Market> ret = new ArrayList<>();
        ret.addAll(currency_a.markets.values());
        ret.addAll(currency_b.markets.values());
        return ret;
    }

    // TODO | implement reverse update which updates the market data members of Currency objects involved in this market


}
