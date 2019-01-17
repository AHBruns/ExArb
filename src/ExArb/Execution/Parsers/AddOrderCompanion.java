package ExArb.Execution.Parsers;

import ExArb.Structures.OrderResponse;
import com.google.gson.stream.JsonReader;


public class AddOrderCompanion {
    static public OrderResponse parse(JsonReader r) throws Exception {
        r.beginObject();
        r.skipValue();
        int order_status = Integer.parseInt(r.nextString());
        r.skipValue();
        String message = r.nextString();
        r.skipValue();
        String old_form_token = r.nextString();
        r.skipValue();
        String new_form_token = r.nextString();
        r.skipValue();
        double p_balance = Double.parseDouble(r.nextString());
        r.skipValue();
        double s_balance =  Double.parseDouble(r.nextString());
        r.endObject();
        return new OrderResponse(order_status, message, old_form_token, new_form_token, p_balance, s_balance);
    }
}
