package ExArb.Execution.ExecutionEndpoints;

import ExArb.Networking.ConnectionUtils;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;

import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;

public class AddOrder {

    private String url = "https://www.coinexchange.io/add/market/order";
    private String form_token = "xxx";

    public AddOrder() {
        try {
            Execute(1019, 1, 100000, 0.00000001);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public JsonReader Execute(int id, int type, int amount, double price) throws IOException {
        HashMap<String, String> data = new HashMap<>();
        data.put("market_id", String.valueOf(id));
        data.put("type", String.valueOf(type));
        data.put("amount", String.valueOf(amount));
        data.put("price", String.valueOf(price));
        data.put("form_token", this.form_token);
//        System.out.println("data: " + data);
        String out = ConnectionUtils.stream2StringGzip(ConnectionUtils.openHiddenAPIStream(url, data));
//        System.out.println(out);
        JsonReader r1 = new JsonReader(new StringReader(out));
        r1.beginObject();
        while (r1.peek() != JsonToken.END_OBJECT) {
            String name = r1.nextName();
            if (name.equals("form_token")) {
                String newFormToken = r1.nextString();
//                System.out.println("updating the form token: " + newFormToken);
                this.form_token = newFormToken;
            } else {
                r1.nextString();
            }
        }
        JsonReader r2 = new JsonReader(new StringReader(out));
        return r2;
    }
}
