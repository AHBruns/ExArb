package ExArb;

import ExArb.Execution.ExecutionEndpoints.AddOrder;
import ExArb.Networking.NetworkManager;
import ExArb.Networking.Parsers.GetCurrenciesCompanion;
import ExArb.Networking.Parsers.GetMarketsCompanion;
import ExArb.Pathing.PathingManager;
import ExArb.Structures.State;

public class Core {

    public static void main(String[] args) throws Exception {

        // setup main state & manager objects
        State state = new State();
        NetworkManager nm = new NetworkManager();
        PathingManager pm = new PathingManager(state);
        AddOrder ao = new AddOrder();

        // setup state with basic info
        GetCurrenciesCompanion.parse(nm.ExecuteGetCurrencies(), state);
        GetMarketsCompanion.parse(nm.ExecuteGetMarkets(), state);

        // look for opportunities
        while (true) {
            pm.buildPaths();
            pm.checkPaths(nm, ao);
        }
    }

}
