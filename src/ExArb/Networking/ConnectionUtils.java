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

    public static InputStream openWSStream(String url) throws IOException {
        URL obj = new URL(url);
        URLConnection con = obj.openConnection();
        con.setRequestProperty("cookie", "_ga=GA1.2.639496306.1547326004; _gid=GA1.2.565105322.1547626827; incap_ses_120_1322663=hlIgWPB2FXzXF3/NLFaqAX72Q1wAAAAAGJlW7jNsaOyjJBYOLkV20g==; incap_ses_277_1322663=aawATCEEDEvn8lXcbhvYA8X2Q1wAAAAAKiJ6h3L3l0UYKJb1S96v/g==; visid_incap_1322663=AGiziRHZRl64R/kSgGVatjJSOlwAAAAATEIPAAAAAACAhqqJAcfxfVBJttSnWafLPNNrLQZFQH76; incap_ses_114_1322663=+GIWCUV7qVVn6BufSQOVAfboRFwAAAAAOA1q7ZipcqpfGQxFfMx/yA==; PHPSESSID=6Dz2%2CS4vkZtaJeLsn3k-1LN1dl-bneCDFLjffr%2CZUTLX47eJPXEh4tU%2CFyZ0FgmhY9HkduXZ2snUn17rQmRZT2; REMEMBERME=WXNQT3k3R29lY1VUbHJwMU01NkVkQmxyTXJoakxUVnllYVc2a2h2bkFtSERCWlZWWUpKdnNUcDEwMFBXV2NFMjFIZk4zcmhEbWMvZ3cvb0FMWlNsTnc9PTp5blVjK0VUMkUvTVZrbTM1Z1pjeVNLZlBQUTh0Q2F0UmFNU2JpU21ZdkwwNWVJdU5nNlhyUHBVZlJRNS9GWGM0NEphM0ZxcDZvV0xMaDdtcE5PbUZZZz09; _gat=1");
        con.setRequestProperty("origin", "https://www.coinexchange.io");
        con.setRequestProperty("accept-encoding", "gzip, deflate, br");
        con.setRequestProperty("accept-language", "en-US,en;q=0.9");
        con.setRequestProperty("user-agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/71.0.3578.98 Safari/537.36");
        con.setRequestProperty("content-type", "application/x-www-form-urlencoded; charset=UTF-8");
        con.setRequestProperty("accept", "application/json, text/javascript, text/html, application/xhtml+xml, application/xml, */*; q=0.01; q=0.9,image/webp,image/apng,*/*; q=0.8");
        con.setRequestProperty("referer", "https://www.coinexchange.io/market/LTC/BTC");
        con.setRequestProperty("authority", "www.coinexchange.io");
        con.setRequestProperty("cache-control", "max-age=0");
        con.setRequestProperty("upgrade-insecure-requests", "1");
        con.setRequestProperty("x-requested-with", "XMLHttpRequest");
        return con.getInputStream();
    }

    public static InputStream openStream(String url) throws IOException {
        URL obj = new URL(url);
        URLConnection con = obj.openConnection();
        con.setRequestProperty("cookie", "_ga=GA1.2.639496306.1547326004; _gid=GA1.2.565105322.1547626827; incap_ses_120_1322663=hlIgWPB2FXzXF3/NLFaqAX72Q1wAAAAAGJlW7jNsaOyjJBYOLkV20g==; incap_ses_277_1322663=aawATCEEDEvn8lXcbhvYA8X2Q1wAAAAAKiJ6h3L3l0UYKJb1S96v/g==; visid_incap_1322663=AGiziRHZRl64R/kSgGVatjJSOlwAAAAATEIPAAAAAACAhqqJAcfxfVBJttSnWafLPNNrLQZFQH76; incap_ses_114_1322663=+GIWCUV7qVVn6BufSQOVAfboRFwAAAAAOA1q7ZipcqpfGQxFfMx/yA==; PHPSESSID=6Dz2%2CS4vkZtaJeLsn3k-1LN1dl-bneCDFLjffr%2CZUTLX47eJPXEh4tU%2CFyZ0FgmhY9HkduXZ2snUn17rQmRZT2; REMEMBERME=WXNQT3k3R29lY1VUbHJwMU01NkVkQmxyTXJoakxUVnllYVc2a2h2bkFtSERCWlZWWUpKdnNUcDEwMFBXV2NFMjFIZk4zcmhEbWMvZ3cvb0FMWlNsTnc9PTp5blVjK0VUMkUvTVZrbTM1Z1pjeVNLZlBQUTh0Q2F0UmFNU2JpU21ZdkwwNWVJdU5nNlhyUHBVZlJRNS9GWGM0NEphM0ZxcDZvV0xMaDdtcE5PbUZZZz09; _gat=1");
        con.setRequestProperty("origin", "https://www.coinexchange.io");
        con.setRequestProperty("accept-encoding", "gzip, deflate, br");
        con.setRequestProperty("accept-language", "en-US,en;q=0.9");
        con.setRequestProperty("user-agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/71.0.3578.98 Safari/537.36");
        con.setRequestProperty("content-type", "application/x-www-form-urlencoded; charset=UTF-8");
        con.setRequestProperty("accept", "application/json, text/javascript, */*; q=0.01");
        con.setRequestProperty("referer", "https://www.coinexchange.io/market/ADST/BTC");
        con.setRequestProperty("authority", "www.coinexchange.io");
        con.setRequestProperty("x-requested-with", "XMLHttpRequest");
        return con.getInputStream();
    }

    public static InputStream openHiddenAPIStream(String url, HashMap<String, String> params, String curA, String curB) throws IOException {
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setDoOutput(true);
        con.setDoInput(true);
        con.setRequestMethod("POST");
        con.setRequestProperty("cookie", "_ga=GA1.2.639496306.1547326004; _gid=GA1.2.565105322.1547626827; incap_ses_120_1322663=hlIgWPB2FXzXF3/NLFaqAX72Q1wAAAAAGJlW7jNsaOyjJBYOLkV20g==; incap_ses_277_1322663=aawATCEEDEvn8lXcbhvYA8X2Q1wAAAAAKiJ6h3L3l0UYKJb1S96v/g==; visid_incap_1322663=AGiziRHZRl64R/kSgGVatjJSOlwAAAAATEIPAAAAAACAhqqJAcfxfVBJttSnWafLPNNrLQZFQH76; incap_ses_114_1322663=+GIWCUV7qVVn6BufSQOVAfboRFwAAAAAOA1q7ZipcqpfGQxFfMx/yA==; PHPSESSID=6Dz2%2CS4vkZtaJeLsn3k-1LN1dl-bneCDFLjffr%2CZUTLX47eJPXEh4tU%2CFyZ0FgmhY9HkduXZ2snUn17rQmRZT2; REMEMBERME=WXNQT3k3R29lY1VUbHJwMU01NkVkQmxyTXJoakxUVnllYVc2a2h2bkFtSERCWlZWWUpKdnNUcDEwMFBXV2NFMjFIZk4zcmhEbWMvZ3cvb0FMWlNsTnc9PTp5blVjK0VUMkUvTVZrbTM1Z1pjeVNLZlBQUTh0Q2F0UmFNU2JpU21ZdkwwNWVJdU5nNlhyUHBVZlJRNS9GWGM0NEphM0ZxcDZvV0xMaDdtcE5PbUZZZz09; _gat=1");
        con.setRequestProperty("origin", "https://www.coinexchange.io");
        con.setRequestProperty("accept-encoding", "gzip, deflate, br");
        con.setRequestProperty("accept-language", "en-US,en;q=0.9");
        con.setRequestProperty("user-agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/71.0.3578.98 Safari/537.36");
        con.setRequestProperty("content-type", "application/x-www-form-urlencoded; charset=UTF-8");
        con.setRequestProperty("accept", "application/json, text/javascript, */*; q=0.01");
        con.setRequestProperty("referer", "https://www.coinexchange.io/market/" + curB + "/" + curA);
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

        /*** source : https://stackoverflow.com/questions/12531579/uncompress-a-gzip-string-in-java ***/
        try {
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
        } catch (Exception e) { e.printStackTrace(); System.out.println(new String(byteArray)); }
        /*** -------------------------------------------------------------------------------------- ***/

        return new String(byteArray);
    }

    public static HashMap<Integer, String> getCurrency_id2ticker_code() {
        return currency_id2ticker_code;
    }

    public static HashMap<String, Integer> getTicker_code2currency_id() {
        return ticker_code2currency_id;
    }

}
