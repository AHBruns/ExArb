package ExArb;

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

        // setup state with basic info
        GetCurrenciesCompanion.parse(nm.ExecuteGetCurrencies(), state);
        GetMarketsCompanion.parse(nm.ExecuteGetMarkets(), state);


    }

}
