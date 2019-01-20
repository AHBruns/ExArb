package ExArb.Networking.Connections;

import ExArb.Networking.ConnectionUtils;
import ExArb.Networking.GenericConnection;
import com.google.gson.stream.JsonReader;

import java.io.IOException;
import java.io.StringReader;

public class GetMarketSummary extends GenericConnection {

    private final static String url_base = ConnectionUtils.url_base + "/getmarketsummary";
    private final static String paramName = "market_id";
    private int market_id;

    public GetMarketSummary(int market_id) {
        this.market_id = market_id;
    }

    public JsonReader Execute() throws IOException {
        return new JsonReader(
                new StringReader(
                        ConnectionUtils.stream2StringGzip(
                                ConnectionUtils.openStream(
                                        ConnectionUtils.buildUrl(url_base, paramName, String.valueOf(market_id))))));
    }

}
