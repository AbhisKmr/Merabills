package com.interview.merabills;


public class Payment {
    private int amount;
    private PaymentType type;
    private String provider;
    private String transactionReference;
    private String paymentSourceName;
    private int paymentSourceNumber;

    public Payment(int amount, PaymentType type, String provider, String transactionReference, String paymentSourceName, int paymentSourceNumber) {
        this.amount = amount;
        this.type = type;
        this.provider = provider;
        this.transactionReference = transactionReference;
        this.paymentSourceName = paymentSourceName;
        this.paymentSourceNumber = paymentSourceNumber;
    }

    public PaymentType getType() {
        return type;
    }

    public int getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return type + " - Rs. " + amount;
    }
}