package ExArb.Execution;

import ExArb.Execution.Parsers.AddOrderCompanion;
import ExArb.Structures.OrderResponse;
import ExArb.Structures.PathExecution;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.Clock;


public class ExecutionManager {

    public static boolean ExecuteBBSPath(PathExecution pe) throws Exception {
        System.out.println("starting path exec");
        try {
//            Thread.sleep(250);
            System.out.println(pe.ids.get(0) + " " +  1 + " " + pe.quants.get(0) + " " + pe.prices.get(0));
            OrderResponse r = AddOrderCompanion.parse(pe.ao.Execute(pe.currencyATickers.get(0), pe.currencyBTickers.get(0), pe.ids.get(0), 1, pe.quants.get(0), pe.prices.get(0)));
            System.out.println(r.status + " " + r.message + " " + r.s_balance + " " + r.p_balance);
            assert r.status == 1;
//            while (bws.alteredFlag != null) {
//                Thread.sleep(50);
//                System.out.println("\tWaiting for balance update 1");
//            }
//            bws = new BalanceWatchSocket(pe.ids.get(1));
            Thread.sleep(2500);
            System.out.println(pe.ids.get(1) + " " +  1 + " " + pe.quants.get(1) + " " + pe.prices.get(1));
            r = AddOrderCompanion.parse(pe.ao.Execute(pe.currencyATickers.get(1), pe.currencyBTickers.get(1), pe.ids.get(1), 1, pe.quants.get(1), pe.prices.get(1)));
            System.out.println(r.status + " " + r.message + " " + r.s_balance + " " + r.p_balance);
            assert r.status == 1;
//            while (bws.alteredFlag == null) {
//                Thread.sleep(50);
//                System.out.println("\tWaiting for balance update 2");
//            }
//            bws = new BalanceWatchSocket(pe.ids.get(2));
            Thread.sleep(2500);
            System.out.println(pe.ids.get(2) + " " +  0 + " " + pe.quants.get(2) + " " + pe.prices.get(2));
            r = AddOrderCompanion.parse(pe.ao.Execute(pe.currencyATickers.get(2), pe.currencyBTickers.get(2), pe.ids.get(2), 0, pe.quants.get(2), pe.prices.get(2)));
            System.out.println(r.status + " " + r.message + " " + r.s_balance + " " + r.p_balance);
            assert r.status == 1;
//            while (bws.alteredFlag == null) {
//                Thread.sleep(50);
//                System.out.println("\tWaiting for balance update 3");
//            }
            System.out.print("\tOrder successfully executed...");
            Files.write(Paths.get("/Users/alex.bruns/ExArb/src/ExArb/executionLog.txt"), ("\n" + String.valueOf(Clock.systemUTC().instant()) + pe.ids + pe.quants + pe.prices).getBytes(), StandardOpenOption.APPEND); // log order executions
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
        return false;
    }
}
