package ExArb.Structures;

import ExArb.Execution.ExecutionEndpoints.AddOrder;

import java.util.ArrayList;

public class PathExecution {
    public ArrayList<Integer> ids = new ArrayList<>();
    public ArrayList<String> currencyATickers = new ArrayList<>();
    public ArrayList<String> currencyBTickers = new ArrayList<>();
    public ArrayList<Double> quants = new ArrayList<>();
    public ArrayList<Double> prices = new ArrayList<>();
    public AddOrder ao;

    public PathExecution(ArrayList<Integer> ids, ArrayList<Double> quants, ArrayList<Double> prices, ArrayList<String> currencyATickers, ArrayList<String> currencyBTickers, AddOrder ao) {
        this.ids = ids;
        this.quants = quants;
        this.prices = prices;
        this.currencyATickers = currencyATickers;
        this.currencyBTickers = currencyBTickers;
        this.ao = ao;
    }
}
