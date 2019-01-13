package ExArb.Networking.Parsers;

import ExArb.Structures.State;
import com.google.gson.stream.JsonReader;

public class GetMarketSummaryCompanion {
    static public void parse(JsonReader r, State s) throws Exception {
        GetMarketSummariesCompanion.parse(r, s); // the json returned is identical in structure
    }
}
