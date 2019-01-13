package ExArb.Networking.Parsers;

import ExArb.Networking.ParserUtils;
import ExArb.Structures.Currency;
import ExArb.Structures.State;
import com.google.gson.stream.JsonReader;

public class GetCurrencyCompanion {
    static public void parse(JsonReader r, State s) throws Exception {
        ParserUtils.parseOffHeader(r);
        r.beginObject();
        r.skipValue();
        int id = Integer.parseInt(r.nextString());
        r.skipValue();
        String name = r.nextString();
        r.skipValue();
        String ticker = r.nextString();
        r.skipValue();
        boolean status = r.nextString().equals("online");
        s.addCurrency(new Currency(id, status, name, ticker));
    }
}
