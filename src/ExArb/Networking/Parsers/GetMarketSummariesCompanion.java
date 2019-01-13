package ExArb.Networking.Parsers;

import ExArb.Networking.ParserUtils;
import ExArb.Structures.Market;
import ExArb.Structures.State;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;

public class GetMarketSummariesCompanion {
    static public void parse(JsonReader r, State s) throws Exception {
        ParserUtils.parseOffHeader(r);
        r.beginArray();
        while (r.peek() != JsonToken.END_ARRAY) {
            r.beginObject();
            r.skipValue();
            int id =  Integer.parseInt(r.nextString());
            s.addMarket(new Market(id));
            r.skipValue(); // TODO | enhance the market object to hold this sort of data
            r.skipValue();
            r.skipValue();
            r.skipValue();
            r.skipValue();
            r.skipValue();
            r.skipValue();
            r.skipValue();
            r.skipValue();
            r.skipValue();
            r.skipValue();
            r.skipValue();
            r.skipValue();
            r.skipValue();
            r.skipValue();
            r.skipValue();
            r.skipValue();
            r.skipValue();
            r.skipValue();
            r.skipValue();
            r.skipValue();
            r.skipValue();
            r.endObject();
        }
    }
}
