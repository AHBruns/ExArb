package ExArb.Networking.Parsers;

import ExArb.Networking.ParserUtils;
import ExArb.Structures.Currency;
import ExArb.Structures.Market;
import ExArb.Structures.State;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;

public class GetMarketsCompanion {
    static public void parse(JsonReader r, State s) throws Exception {
        ParserUtils.parseOffHeader(r);
        r.beginArray();
        while (r.peek() != JsonToken.END_ARRAY) {
            r.beginObject();
            r.skipValue();
            int id = Integer.parseInt(r.nextString());
            r.skipValue();
            String currency_a_name = r.nextString();
            r.skipValue();
            String curency_a_ticker = r.nextString();
            r.skipValue();
            int currency_a_id = Integer.parseInt(r.nextString());
            r.skipValue();
            r.skipValue();
            r.skipValue();
            String currency_b_name = r.nextString();
            r.skipValue();
            String currency_b_ticker = r.nextString();
            r.skipValue();
            int currency_b_id = Integer.parseInt(r.nextString());
            r.skipValue();
            boolean active = r.nextBoolean();
            if (!s.currencies.containsKey(currency_a_id) || !s.currencies.containsKey(currency_b_id)) {
                s.addCurrency(new Currency(currency_a_id));
                s.addCurrency(new Currency(currency_b_id));
            }
            s.addMarket(new Market(id, active, s.currencies.get(currency_a_id), s.currencies.get(currency_b_id)));
            r.endObject();
        }
    }
}
