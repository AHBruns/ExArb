package ExArb.Networking.Parsers;

import ExArb.Networking.ParserUtils;
import ExArb.Structures.Currency;

import ExArb.Structures.State;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;

public class GetCurrenciesCompanion{
    static public void parse(JsonReader r, State s) throws Exception {
        ParserUtils.parseOffHeader(r);
        r.beginArray();
        while (r.peek() != JsonToken.END_ARRAY) {
            r.beginObject();
            r.skipValue();
            int id = Integer.parseInt(r.nextString());
            r.skipValue();
            String name = r.nextString();
            r.skipValue();
            String ticker = r.nextString();
            r.skipValue();
            boolean status = r.nextString().equals("online");
            r.skipValue();
            r.skipValue();
            s.addCurrency(new Currency(id, status, name, ticker)); // add this currency to state
            r.endObject();
        }
    }
}
