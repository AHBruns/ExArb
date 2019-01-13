package ExArb;

import ExArb.Networking.NetworkManager;
import com.google.gson.stream.JsonReader;

public class Core {

    public static void main(String[] args) {
        try {
            JsonReader r = new NetworkManager().ExecuteGetMarkets();
            r.beginObject();
            System.out.println("\n\n-----\n\n");
            System.out.println(r.nextName());
            r.skipValue();
            System.out.println(r.nextName());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
