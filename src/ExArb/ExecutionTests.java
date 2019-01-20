package ExArb;

import ExArb.Execution.ExecutionEndpoints.AddOrder;
import ExArb.Execution.Parsers.AddOrderCompanion;
import ExArb.Structures.OrderResponse;

public class ExecutionTests {

    public static void main(String[] args) throws Exception {
        AddOrder ao = new AddOrder();
        OrderResponse or = AddOrderCompanion.parse(ao.Execute("LTC", "BTC", 18, 1, 10001, 0.00000001));
        System.out.println(or.status);
        System.out.println(or.message);
        System.out.println(or.old_form_token);
        System.out.println(or.new_form_token);
        System.out.println(or.p_balance);
        System.out.println(or.s_balance);
    }

}
