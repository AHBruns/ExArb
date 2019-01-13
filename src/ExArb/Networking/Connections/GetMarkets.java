package ExArb.Networking.Connections;

import ExArb.Networking.ConnectionUtils;
import ExArb.Networking.GenericConnection;
import com.google.gson.stream.JsonReader;

import java.io.IOException;
import java.io.StringReader;

public class GetMarkets extends GenericConnection {

    private final static String url_base = ConnectionUtils.url_base + "/getmarkets";

    public JsonReader Execute() throws IOException {
        return new JsonReader(new StringReader(ConnectionUtils.stream2String(ConnectionUtils.openStream(url_base))));
    }

}
