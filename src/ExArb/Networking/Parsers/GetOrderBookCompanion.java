package ExArb.Networking.Parsers;

import ExArb.Networking.ParserUtils;
import ExArb.Structures.Market;
import ExArb.Structures.Order;
import ExArb.Structures.State;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;

import java.util.ArrayList;

public class GetOrderBookCompanion {
    static public void parse(String url, JsonReader r, State s) throws Exception { // this one is special because it requires the url to give context about which order book it is returning
        Market m = s.markets.get(Integer.parseInt(url.split("=")[1]));
        ParserUtils.parseOffHeader(r);
        r.beginObject();
        r.skipValue(); // sell orders
        r.beginArray();
        ArrayList<Order> sells = new ArrayList<>();
        while (r.peek() != JsonToken.END_ARRAY) {
            r.beginObject();
            r.skipValue();
            String type = r.nextString();
            r.skipValue();
            double price = Double.parseDouble(r.nextString());
            r.skipValue();
            r.skipValue();
            r.skipValue();
            double quantity = Double.parseDouble(r.nextString());
            sells.add(new Order(type, price, quantity));
            r.endObject();
        }
        r.endArray();
        r.skipValue(); // buy orders
        r.beginArray();
        ArrayList<Order> buys = new ArrayList<>();
        while (r.peek() != JsonToken.END_ARRAY) {
            r.beginObject();
            r.skipValue();
            String type = r.nextString();
            r.skipValue();
            double price = Double.parseDouble(r.nextString());
            r.skipValue();
            r.skipValue();
            r.skipValue();
            double quantity = Double.parseDouble(r.nextString());
            buys.add(new Order(type, price, quantity));
            r.endObject();
        }
        m.sell_orders = sells;
        m.buy_orders = buys;
    }

    static public void parse(Market m, JsonReader r, State s) throws Exception { // this one is special because it requires the url to give context about which order book it is returning
        ParserUtils.parseOffHeader(r);
        r.beginObject();
        r.skipValue(); // sell orders
        r.beginArray();
        ArrayList<Order> sells = new ArrayList<>();
        while (r.peek() != JsonToken.END_ARRAY) {
            r.beginObject();
            r.skipValue();
            String type = r.nextString();
            r.skipValue();
            double price = Double.parseDouble(r.nextString());
            r.skipValue();
            r.skipValue();
            r.skipValue();
            double quantity = Double.parseDouble(r.nextString());
            sells.add(new Order(type, price, quantity));
            r.endObject();
        }
        r.endArray();
        r.skipValue(); // buy orders
        r.beginArray();
        ArrayList<Order> buys = new ArrayList<>();
        while (r.peek() != JsonToken.END_ARRAY) {
            r.beginObject();
            r.skipValue();
            String type = r.nextString();
            r.skipValue();
            double price = Double.parseDouble(r.nextString());
            r.skipValue();
            r.skipValue();
            r.skipValue();
            double quantity = Double.parseDouble(r.nextString());
            buys.add(new Order(type, price, quantity));
            r.endObject();
        }
        m.sell_orders = sells;
        m.buy_orders = buys;
    }

    static public void parse(Integer id, JsonReader r, State s) throws Exception { // this one is special because it requires the url to give context about which order book it is returning
        Market m = s.markets.get(id);
        ParserUtils.parseOffHeader(r);
        r.beginObject();
        r.skipValue(); // sell orders
        r.beginArray();
        ArrayList<Order> sells = new ArrayList<>();
        while (r.peek() != JsonToken.END_ARRAY) {
            r.beginObject();
            r.skipValue();
            String type = r.nextString();
            r.skipValue();
            double price = Double.parseDouble(r.nextString());
            r.skipValue();
            r.skipValue();
            r.skipValue();
            double quantity = Double.parseDouble(r.nextString());
            sells.add(new Order(type, price, quantity));
            r.endObject();
        }
        r.endArray();
        r.skipValue(); // buy orders
        r.beginArray();
        ArrayList<Order> buys = new ArrayList<>();
        while (r.peek() != JsonToken.END_ARRAY) {
            r.beginObject();
            r.skipValue();
            String type = r.nextString();
            r.skipValue();
            double price = Double.parseDouble(r.nextString());
            r.skipValue();
            r.skipValue();
            r.skipValue();
            double quantity = Double.parseDouble(r.nextString());
            buys.add(new Order(type, price, quantity));
            r.endObject();
        }
        m.sell_orders = sells;
        m.buy_orders = buys;
    }

}
