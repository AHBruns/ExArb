package ExArb;

import ExArb.Networking.Connections.BalanceWatchSocket.BalanceWatchSocket;

public class WebSocketTests {
    public static void main(String[] args) throws Exception {
        BalanceWatchSocket bws = new BalanceWatchSocket(18); // BTC/LTC market
        while (bws.alteredFlag == null) {
            Thread.sleep(500);
        }
        System.out.println("Balance updated!");
    }
}
