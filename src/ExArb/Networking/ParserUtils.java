package ExArb.Networking;

import com.google.gson.stream.JsonReader;

public class ParserUtils {
    static public void parseOffHeader(JsonReader r) throws Exception {
        r.beginObject();
        r.skipValue(); // "success"
        if (!r.nextString().equals("1")) { throw new Exception("Response success flag wasn't true"); }
        r.skipValue(); // "request"
        r.skipValue();
        r.skipValue(); // "message"
        String msg = r.nextString();
        if (!msg.equals("")) { System.out.println("Response contained a message: " + msg); }
        r.skipValue(); // "result"
    }
}
