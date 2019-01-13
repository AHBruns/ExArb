package ExArb.Networking.Parsers;

import ExArb.Structures.Currency;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;

import java.util.HashMap;

public class GetCurrenciesCompanion{
    static public void parse(JsonReader r, HashMap<Integer, Currency> currencies) throws Exception {
        r.beginObject();
        r.skipValue(); // "success"
        if (!r.nextString().equals("1")) { throw new Exception("Response success flag wasn't true"); }
        r.skipValue(); // "request"
        r.skipValue();
        r.skipValue(); // "message"
        String msg = r.nextString();
        if (!msg.equals("")) { System.out.println("Response contained a message: " + msg); }
        r.skipValue(); // "result"
        r.beginArray();
        while (r.peek() != JsonToken.END_ARRAY) {
            r.beginObject();
            r.skipValue();
            int id = Integer.parseInt(r.nextString());
            if (!currencies.containsKey(id)) {
                currencies.put(id, new Currency(id));
            }
            r.skipValue();
            currencies.get(id).name = r.nextString();
            r.skipValue();
            currencies.get(id).ticker = r.nextString();
            r.skipValue();
            currencies.get(id).status = r.nextString();
            r.skipValue();
            r.skipValue();
            r.endObject();
        }
        r.endArray();
        r.endObject();
    }
}
