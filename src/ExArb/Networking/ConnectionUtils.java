package ExArb.Networking;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.LinkedHashMap;
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

    public static InputStream openHiddenAPIStream(String url, HashMap<String, String> params) throws IOException {
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setDoOutput(true);
        con.setDoInput(true);
        con.setRequestMethod("POST");
        con.setRequestProperty("cookie", "PHPSESSID=lixLfKXcX2W9AtwUh0Yt7pl0TS7CsrS799La7YUhUIO9pTU9OyOIq5WQv50Ie7Varxiesqdw6uLSgyEqihU%2C32; REMEMBERME=ajhVSmcwVnI5alJST01KZm93ZG43SjhXMWI3NW1VcjF2TjlTVm9nUEFQWFpHMldpWDlxWXQ4enF4b0RRcWRLQkw3Q3hXRlR6cld4VkJ1VFdPTklTOVE9PTpyWHBnQ2J6cFg4TDJ1Sy9vdWFhcFkwWWpXM0VJSDlrR1hqWnNJQnk4aVJlRGVuWGFWSG9zN0hraHc3WEVrbjJaQzdKQUo0MWFJS0t1VDNLWXUveFhWdz09; incap_ses_120_1322663=XUmwWTAsqxvZghbGLFaqAQLeP1wAAAAAR0nv9+8OyMX/vEmrv7o/Tg==;");
        con.setRequestProperty("origin", "https://www.coinexchange.io");
        con.setRequestProperty("accept-encoding", "gzip, deflate, br");
        con.setRequestProperty("accept-language", "en-US,en;q=0.9");
        con.setRequestProperty("user-agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/71.0.3578.98 Safari/537.36");
        con.setRequestProperty("content-type", "application/x-www-form-urlencoded; charset=UTF-8");
        con.setRequestProperty("accept", "application/json, text/javascript, */*; q=0.01");
        con.setRequestProperty("referer", "https://www.coinexchange.io/market/ADST/BTC");
        con.setRequestProperty("authority", "www.coinexchange.io");
        con.setRequestProperty("x-requested-with", "XMLHttpRequest");
        StringBuilder postData = new StringBuilder();
        postData.append(URLEncoder.encode("market_id", "UTF-8"));
        postData.append('=');
        postData.append(URLEncoder.encode(params.get("market_id"), "UTF-8"));
        postData.append("&");
        postData.append(URLEncoder.encode("type", "UTF-8"));
        postData.append('=');
        postData.append(URLEncoder.encode(params.get("type"), "UTF-8"));
        postData.append("&");
        postData.append(URLEncoder.encode("amount", "UTF-8"));
        postData.append('=');
        postData.append(URLEncoder.encode(params.get("amount"), "UTF-8"));
        postData.append("&");
        postData.append(URLEncoder.encode("price", "UTF-8"));
        postData.append('=');
        postData.append(URLEncoder.encode(params.get("price"), "UTF-8"));
        postData.append("&");
        postData.append(URLEncoder.encode("form_token", "UTF-8"));
        postData.append('=');
        postData.append(URLEncoder.encode(params.get("form_token"), "UTF-8"));
//        System.out.println(postData);
        byte[] postDataBytes = postData.toString().getBytes("UTF-8");
        con.getOutputStream().write(postDataBytes);
        return con.getInputStream();
    }

    public static byte[] stream2Array(InputStream s) throws IOException {
        byte[] ar = new byte[s.available()];
        s.read(ar);
        return ar;
    }

    public static String stream2String(InputStream s) throws IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        int nRead;
        byte[] data = new byte[1024];
        while ((nRead = s.read(data, 0, data.length)) != -1) {
            buffer.write(data, 0, nRead);
        }
        buffer.flush();
        byte[] byteArray = buffer.toByteArray();
        return new String(byteArray);
    }

    public static String stream2StringGzip(InputStream s) throws IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        int nRead;
        byte[] data = new byte[1024];
        while ((nRead = s.read(data, 0, data.length)) != -1) {
            buffer.write(data, 0, nRead);
        }
        buffer.flush();
        byte[] byteArray = buffer.toByteArray();

        // source : https://stackoverflow.com/questions/12531579/uncompress-a-gzip-string-in-java
        java.io.ByteArrayInputStream bytein = new java.io.ByteArrayInputStream(byteArray);
        java.util.zip.GZIPInputStream gzin = new java.util.zip.GZIPInputStream(bytein);
        java.io.ByteArrayOutputStream byteout = new java.io.ByteArrayOutputStream();
        int res = 0;
        byte buf[] = new byte[1024];
        while (res >= 0) {
            res = gzin.read(buf, 0, buf.length);
            if (res > 0) {
                byteout.write(buf, 0, res);
            }
        }
        byte uncompressed[] = byteout.toByteArray();
        return new String(uncompressed);
    }

    public static HashMap<Integer, String> getCurrency_id2ticker_code() {
        return currency_id2ticker_code;
    }

    public static HashMap<String, Integer> getTicker_code2currency_id() {
        return ticker_code2currency_id;
    }

}
