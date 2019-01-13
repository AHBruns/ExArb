package ExArb.Structures;

import java.util.ArrayList;

public class Currency {

    public Integer currency_id;
    public String name, ticker, status;
    public ArrayList<Market> markets;

    public Currency(int currency_id) {
        this.currency_id = currency_id;
    }

    public Currency(int currency_id, String name, String ticker, String status, ArrayList<Market> markets) {
        this.currency_id = currency_id;
        this.name = name;
        this.ticker = ticker;
        this.status = status;
    }

    public Currency(int currency_id, String name, String ticker, String status) {
        this.currency_id = currency_id;
        this.name = name;
        this.ticker = ticker;
        this.status = status;
    }

}
