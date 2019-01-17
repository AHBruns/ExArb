package ExArb.Execution;

import ExArb.Execution.Parsers.AddOrderCompanion;
import ExArb.Networking.Connections.BalanceWatchSocket.BalanceWatchSocket;
import ExArb.Structures.OrderResponse;
import ExArb.Structures.PathExecution;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;


public class ExecutionManager {

    public static boolean ExecuteBBSPath(PathExecution pe) throws Exception {
        BalanceWatchSocket bws1 =  new BalanceWatchSocket(pe.ids.get(0));
        BalanceWatchSocket bws2 =  new BalanceWatchSocket(pe.ids.get(1));
        BalanceWatchSocket bws3 =  new BalanceWatchSocket(pe.ids.get(2));
        OrderResponse r = AddOrderCompanion.parse(pe.ao.Execute(pe.ids.get(0), 1, pe.quants.get(0), pe.prices.get(0)));
        assert r.status == 1;
        while (!bws1.alteredFlag) {
            Thread.sleep(5);
            System.out.println("\tWaiting for balance update 1");
        }
        r = AddOrderCompanion.parse(pe.ao.Execute(pe.ids.get(1), 1, pe.quants.get(1), pe.prices.get(1)));
        assert r.status == 1;
        while (!bws2.alteredFlag) {
            Thread.sleep(5);
            System.out.println("\tWaiting for balance update 2");
        }
        r = AddOrderCompanion.parse(pe.ao.Execute(pe.ids.get(2), 1, pe.quants.get(2), pe.prices.get(2)));
        assert r.status == 1;
        while (!bws3.alteredFlag) {
            Thread.sleep(5);
            System.out.println("\tWaiting for balance update 3");
        }
        System.out.print("\tOrder successfully executed...");
        Files.write(Paths.get("/Users/alex.bruns/ExArb/src/ExArb/executionLog.txt"), ("\n" + pe.ids + pe.quants + pe.prices).getBytes(), StandardOpenOption.APPEND); // log order executions
        return true;
    }
}
