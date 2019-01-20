package ExArb.Networking.Connections;

import ExArb.Networking.ConnectionUtils;
import ExArb.Networking.GenericConnection;
import com.google.gson.stream.JsonReader;

import java.io.IOException;
import java.io.StringReader;

public class GetCurrencies extends GenericConnection {

    private final static String url_base = ConnectionUtils.url_base + "/getcurrencies";

    public JsonReader Execute() throws IOException {
        String s = ConnectionUtils.stream2StringGzip(ConnectionUtils.openStream(url_base));
        return new JsonReader(new StringReader(s));
    }

}
