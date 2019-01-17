package ExArb.Structures;

public class OrderResponse {

    public int status;
    public String message, old_form_token, new_form_token;
    public double p_balance, s_balance;

    public OrderResponse(int status, String message, String old_form_token, String new_form_token, double p_balance, double s_balance) {
        this.status = status;
        this.message = message;
        this.old_form_token = old_form_token;
        this.new_form_token = new_form_token;
        this.p_balance = p_balance;
        this.s_balance = s_balance;
    }
}
