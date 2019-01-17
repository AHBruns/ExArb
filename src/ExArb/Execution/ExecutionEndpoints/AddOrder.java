package ExArb.Execution.ExecutionEndpoints;

import ExArb.Networking.ConnectionUtils;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;

import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;

public class AddOrder {

    private String url = "https://www.coinexchange.io/add/market/order";
    private HashMap<Integer, String> form_token = new HashMap<>();

    public JsonReader Execute(int id, int type, double amount, double price) throws IOException {
        if (form_token.containsKey(id)) {
            HashMap<String, String> data = new HashMap<>();
            data.put("market_id", String.valueOf(id));
            data.put("type", String.valueOf(type));
            data.put("amount", String.valueOf(amount));
            data.put("price", String.valueOf(price));
            data.put("form_token", form_token.get(id));
            return new JsonReader(new StringReader(ConnectionUtils.stream2StringGzip(ConnectionUtils.openHiddenAPIStream(url, data))));
        }
        HashMap<String, String> data = new HashMap<>();
        data.put("market_id", String.valueOf(id));
        data.put("type", String.valueOf(type));
        data.put("amount", String.valueOf(amount));
        data.put("price", String.valueOf(price));
        data.put("form_token", "no_id");
        JsonReader r1 = new JsonReader(new StringReader(ConnectionUtils.stream2StringGzip(ConnectionUtils.openHiddenAPIStream(url, data))));
        r1.beginObject();
        while (r1.peek() != JsonToken.END_OBJECT) {
            String name = r1.nextName();
            if (name.equals("form_token")) {
                String newFormToken = r1.nextString();
                this.form_token.put(id, newFormToken);
            } else {
                r1.nextString();
            }
        }
        data.put("form_token", form_token.get(id));
        return new JsonReader(new StringReader(ConnectionUtils.stream2StringGzip(ConnectionUtils.openHiddenAPIStream(url, data))));
    }
}
