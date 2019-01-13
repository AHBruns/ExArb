package ExArb.Networking.Connections;

import ExArb.Networking.ConnectionUtils;
import ExArb.Networking.GenericConnection;
import com.google.gson.stream.JsonReader;

import java.io.IOException;
import java.io.StringReader;

public class GetOrderBook extends GenericConnection {

    private final static String url_base = ConnectionUtils.url_base + "/getorderbook";
    private final static String paramName = "market_id";
    private int market_id;

    public GetOrderBook(int market_id) {
        this.market_id = market_id;
    }

    public JsonReader Execute() throws IOException {
        return new JsonReader(
                new StringReader(
                        ConnectionUtils.stream2String(
                                ConnectionUtils.openStream(
                                        ConnectionUtils.buildUrl(url_base, paramName, String.valueOf(market_id))))));
    }

}
