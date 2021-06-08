package model;

public class History {
    private String transaction_type, timestamp, destination, sender, nim;
    private int amount;

    public History() {
    }

    public History(String transaction_type, String timestamp, int amount) {
        this.transaction_type = transaction_type;
        this.timestamp = timestamp;
        this.amount = amount;
    }

    public History(String transaction_type, String timestamp, String destination, String sender, String nim, int amount) {
        this.transaction_type = transaction_type;
        this.timestamp = timestamp;
        this.destination = destination;
        this.sender = sender;
        this.nim = nim;
        this.amount = amount;
    }

    public String getTransaction_type() {
        return transaction_type;
    }

    public void setTransaction_type(String transaction_type) {
        this.transaction_type = transaction_type;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getNim() {
        return nim;
    }

    public void setNim(String nim) {
        this.nim = nim;
    }
}
