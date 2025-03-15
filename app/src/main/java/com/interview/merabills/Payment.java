package com.interview.merabills;


public class Payment {
    private int amount;
    private PaymentType type;
    private String provider;
    private String transactionReference;

    public Payment(int amount, PaymentType type, String provider, String transactionReference) {
        this.amount = amount;
        this.type = type;
        this.provider = provider;
        this.transactionReference = transactionReference;
    }

    public PaymentType getType() {
        return type;
    }

    public int getAmount() { return amount; }

    @Override
    public String toString() {
        return type + " - Rs. " + amount;
    }
}