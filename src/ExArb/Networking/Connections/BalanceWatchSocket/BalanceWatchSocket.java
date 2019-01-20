package ExArb.Networking.Connections.BalanceWatchSocket;

import ExArb.Networking.ConnectionUtils;
import java.net.URI;
import java.net.URISyntaxException;

public class BalanceWatchSocket {

    public String alteredFlag = null;

    public String isSafeToProceed() {
        return alteredFlag;
    }

    public void flipFlag() {
        alteredFlag = null;
    }

    public BalanceWatchSocket(int id) {
        String ws_general_token = "";
        String ws_auth_token = "";
        // get token
        try {
            String source = ConnectionUtils.stream2StringGzip(ConnectionUtils.openWSStream("https://www.coinexchange.io/market/LTC/BTC"));
            String subSource = source.split("var ws_general_token = '")[1];
            ws_general_token = subSource.split("';")[0];
            subSource = source.split("var ws_auth_token = '")[1];
            ws_auth_token = subSource.split("';")[0];
        } catch (Exception e) { e.printStackTrace(); System.exit(-1); }

        // log the token
        System.out.println(ws_auth_token);
        System.out.println(ws_general_token);

        // open channel
        try {
            // open websocket
            final WebsocketClientEndpoint clientEndPoint = new WebsocketClientEndpoint(new URI("wss://ws.coinexchange3.com:3001/marketdata"));

            // add listener
            clientEndPoint.addMessageHandler(new WebsocketClientEndpoint.MessageHandler() {
                public void handleMessage(String message) {
                    System.out.println(message);
                    if (message.contains("recent_market_trade")) {
                        alteredFlag = message;
                    }
                }
            });

            // send message to websocket
            clientEndPoint.sendMessage("type=join_channel&market_id=" + id + "&ws_auth_token=" + ws_auth_token);

        } catch (URISyntaxException ex) {
            System.err.println("URISyntaxException exception: " + ex.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }
}
