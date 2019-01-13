package ExArb.Networking;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class ConnectionUtils {

    public static String url_base = "https://www.coinexchange.io/api/v1";
    public static HashMap<Integer, String> currency_id2ticker_code = new HashMap<>();
    public static HashMap<String, Integer> ticker_code2currency_id = new HashMap<>();

    public static void addPair(int currency_id, String ticker_code) {
        currency_id2ticker_code.put(currency_id, ticker_code);
        ticker_code2currency_id.put(ticker_code, currency_id);
    }

    public static void addPairsInt2String(HashMap<Integer, String> pairs) {
        currency_id2ticker_code.putAll(pairs);
        ticker_code2currency_id.putAll(
                pairs.entrySet()
                        .stream()
                        .collect(Collectors.toMap(Map.Entry::getValue, Map.Entry::getKey)));
    }

    public static void addPairsString2Int(HashMap<String, Integer> pairs) {
        ticker_code2currency_id.putAll(pairs);
        currency_id2ticker_code.putAll(
                pairs.entrySet()
                        .stream()
                        .collect(Collectors.toMap(Map.Entry::getValue, Map.Entry::getKey)));
    }

    public static String buildUrl(String url, HashMap<String, String> params) {
        StringBuilder sb = new StringBuilder();
        sb.append(url);
        for (Map.Entry<String, String> param: params.entrySet()) {
             sb.append("?").append(param.getKey()).append("=").append(param.getValue());
        }
        return sb.toString();
    }

    public static String buildUrl(String url, String paramName, String paramValue) {
        return new StringBuilder().append(url).append("?").append(paramName).append("=").append(paramValue).toString();
    }

    public static InputStream openStream(String url) throws IOException {
        URL obj = new URL(url);
        URLConnection con = obj.openConnection();
        return con.getInputStream();
    }

    public static byte[] stream2Array(InputStream s) throws IOException {
        byte[] ar = new byte[s.available()];
        s.read(ar);
        return ar;
    }

    public static String stream2String(InputStream s) throws IOException {
        byte[] ar = new byte[s.available()];
        s.read(ar);
        return new String(ar);
    }

    public static HashMap<Integer, String> getCurrency_id2ticker_code() {
        return currency_id2ticker_code;
    }

    public static HashMap<String, Integer> getTicker_code2currency_id() {
        return ticker_code2currency_id;
    }

}
