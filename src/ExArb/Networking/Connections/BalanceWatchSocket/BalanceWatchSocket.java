package ExArb.Networking.Connections.BalanceWatchSocket;

import java.net.URI;
import java.net.URISyntaxException;

public class BalanceWatchSocket {

    public boolean alteredFlag = false;

    public boolean isSafeToProceed() {
        return alteredFlag;
    }

    public BalanceWatchSocket(int market_id) {
        try {
            // open websocket
            final WebsocketClientEndpoint clientEndPoint = new WebsocketClientEndpoint(new URI("wss://ws.coinexchange3.com:3001/marketdata"));

            // add listener
            clientEndPoint.addMessageHandler(new WebsocketClientEndpoint.MessageHandler() {
                public void handleMessage(String message) {
                    System.out.println(message);
                    alteredFlag = true;
                }
            });

            // send message to websocket
            clientEndPoint.sendMessage("type=join_balance_channel&market_id=" + market_id + "&ws_auth_token=HJQX6IbMm5iJPhuTGYgjRjVZx%2FhAhea%2FJf%2FijpUvP");

            // wait 5 seconds for messages from websocket
            while (true) {
                Thread.sleep(5000);
            }

        } catch (InterruptedException ex) {
            System.err.println("InterruptedException exception: " + ex.getMessage());
        } catch (URISyntaxException ex) {
            System.err.println("URISyntaxException exception: " + ex.getMessage());
        }
    }
}
