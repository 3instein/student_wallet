package model;

public class Finance {

    private int finance_id, user_id, amount;
    private String status;

    public Finance() {

    }

    public int getFinance_id() {
        return finance_id;
    }

    public void setFinance_id(int finance_id) {
        this.finance_id = finance_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
