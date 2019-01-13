package ExArb.Structures;

import java.util.ArrayList;

public class Currency {

    public Integer id;
    public String name, ticker;
    public ArrayList<Market> markets;
    public Boolean status;

    public Currency(int id) {
        this.id = id;
    }

    public Currency(int id, boolean status) {
        this.id = id;
        this.status = status;
    }

    public Currency(int id, boolean status, String name, String ticker) {
        this.id = id;
        this.status = status;
        this.name = name;
        this.ticker = ticker;
    }

    public Currency(int id, boolean status, String name, String ticker, ArrayList<Market> markets) {
        this.id = id;
        this.status = status;
        this.name = name;
        this.ticker = ticker;
        this.markets = markets;
    }

    public boolean isComplete() {
        return id != null && name != null && ticker != null && markets != null && status != null;
    }

    public void update(Currency c) {
        if (c.id != null) { id = c.id; }
        if (c.status != null) { status = c.status; }
        if (c.name != null) { name = c.name; }
        if (c.ticker != null) { ticker = c.ticker; }
        if (c.markets != null) { markets = c.markets; }
    }

}
