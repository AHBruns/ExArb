package ExArb.Structures;

import java.util.HashMap;

public class State {
    public HashMap<Integer, Currency> currencies;
    public HashMap<Integer, Market> markets;

    public State() {
        this.currencies = new HashMap<>();
        this.markets = new HashMap<>();
    }

    public void addMarket(Market m) {
        assert (currencies.containsValue(m.currency_a));
        assert (currencies.containsValue(m.currency_b));
        if (markets.containsKey(m.id)) {
            updateMarket(m);
        } else {
            markets.put(m.id, m);
            markets.get(m.id).currency_a.markets.put(m.id, m);
            markets.get(m.id).currency_b.markets.put(m.id, m);
        }
    }

    public void addCurrency(Currency c) {
        if (currencies.containsKey(c.id)) {
            updateCurrency(c);
        } else {
            currencies.put(c.id, c);
        }
    }

    public void updateMarket(Market m) {
        assert (currencies.containsValue(m.currency_a));
        assert (currencies.containsValue(m.currency_b));
        markets.get(m.id).update(m);
        markets.get(m.id).currency_a.markets.put(m.id, markets.get(m.id));
        markets.get(m.id).currency_b.markets.put(m.id, markets.get(m.id));
    }

    public void updateCurrency(Currency c) {
        currencies.get(c.id).update(c);
    }
}
