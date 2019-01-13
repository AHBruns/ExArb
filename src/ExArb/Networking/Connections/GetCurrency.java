package ExArb.Networking.Connections;

import ExArb.Networking.ConnectionUtils;
import ExArb.Networking.GenericConnection;
import com.google.gson.stream.JsonReader;

import java.io.IOException;
import java.io.StringReader;
import java.lang.String;

public class GetCurrency extends GenericConnection {

    private final static String url_base = ConnectionUtils.url_base + "/getcurrency";
    private final static String paramName = "currency_id";
    private int currency_id;

    public GetCurrency(int currency_id) {
        this.currency_id = currency_id;
    }

    public JsonReader Execute() throws IOException {
        return new JsonReader(
                new StringReader(
                        ConnectionUtils.stream2String(
                                ConnectionUtils.openStream(
                                        ConnectionUtils.buildUrl(url_base, paramName, String.valueOf(currency_id))))));
    }

}
