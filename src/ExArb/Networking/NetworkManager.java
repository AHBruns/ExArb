package ExArb.Networking;

import ExArb.Networking.Connections.*;
import com.google.gson.stream.JsonReader;

import java.util.HashMap;

public class NetworkManager {

    private HashMap<String, GenericConnection> noarg_endpoints = new HashMap<>();
    private HashMap<String, HashMap<Integer, GenericConnection>> arg_endpoints =  new HashMap<>();

    public JsonReader GenericExecute(String endpoint) throws Exception {
        return noarg_endpoints.get(endpoint).Execute();
    }

    public JsonReader GenericExecute(String endpoint, Integer id) throws Exception {
        return arg_endpoints.get(endpoint).get(id).Execute();
    }

    // no arg endpoints
    public JsonReader ExecuteGetCurrencies() {
        try {
            if (noarg_endpoints.containsKey("GetCurrencies")) {
                return noarg_endpoints.get("GetCurrencies").Execute();
            } else {
                noarg_endpoints.put("GetCurrencies", new GetCurrencies());
                return noarg_endpoints.get("GetCurrencies").Execute();
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
        return null;
    }

    public JsonReader ExecuteGetMarkets() {
        try {
            if (noarg_endpoints.containsKey("GetMarkets")) {
                return noarg_endpoints.get("GetMarkets").Execute();
            } else {
                noarg_endpoints.put("GetMarkets", new GetMarkets());
                return noarg_endpoints.get("GetMarkets").Execute();
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
        return null;
    }

    public JsonReader ExecuteGetMarketSummaries() {
        try {
            if (noarg_endpoints.containsKey("GetMarketSummaries")) {
                return noarg_endpoints.get("GetMarketSummaries").Execute();
            } else {
                noarg_endpoints.put("GetMarketSummaries", new GetMarketSummaries());
                return noarg_endpoints.get("GetMarketSummaries").Execute();
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
        return null;
    }

    // arg endpoints
    public JsonReader ExecuteGetCurrency(int currency_id) {
        try {
            if (arg_endpoints.containsKey("GetCurrency")) {
                if (arg_endpoints.get("GetCurrency").containsKey(currency_id)) {
                    return arg_endpoints.get("GetCurrency").get(currency_id).Execute();
                } else {
                    arg_endpoints.get("GetCurrency").put(currency_id, new GetCurrency(currency_id));
                    return  arg_endpoints.get("GetCurrency").get(currency_id).Execute();
                }
            } else {
                arg_endpoints.put("GetCurrency", new HashMap<>());
                arg_endpoints.get("GetCurrency").put(currency_id, new GetCurrency(currency_id));
                return  arg_endpoints.get("GetCurrency").get(currency_id).Execute();
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
        return null;
    }

    public JsonReader ExecuteGetMarketSummary(int market_id) {
        try {
            if (arg_endpoints.containsKey("GetMarketSummary")) {
                if (arg_endpoints.get("GetMarketSummary").containsKey(market_id)) {
                    return arg_endpoints.get("GetMarketSummary").get(market_id).Execute();
                } else {
                    arg_endpoints.get("GetMarketSummary").put(market_id, new GetMarketSummary(market_id));
                    return  arg_endpoints.get("GetMarketSummary").get(market_id).Execute();
                }
            } else {
                arg_endpoints.put("GetMarketSummary", new HashMap<>());
                arg_endpoints.get("GetMarketSummary").put(market_id, new GetMarketSummary(market_id));
                return  arg_endpoints.get("GetMarketSummary").get(market_id).Execute();
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
        return null;
    }

    public JsonReader ExecuteGetOrderBook(int market_id) {
        try {
            if (arg_endpoints.containsKey("GetOrderBook")) {
                if (arg_endpoints.get("GetOrderBook").containsKey(market_id)) {
                    return arg_endpoints.get("GetOrderBook").get(market_id).Execute();
                } else {
                    arg_endpoints.get("GetOrderBook").put(market_id, new GetOrderBook(market_id));
                    return  arg_endpoints.get("GetOrderBook").get(market_id).Execute();
                }
            } else {
                arg_endpoints.put("GetOrderBook", new HashMap<>());
                arg_endpoints.get("GetOrderBook").put(market_id, new GetOrderBook(market_id));
                return  arg_endpoints.get("GetOrderBook").get(market_id).Execute();
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
        return null;
    }

}
