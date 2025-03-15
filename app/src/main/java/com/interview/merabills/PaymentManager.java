package com.interview.merabills;

import android.content.Context;
import java.util.ArrayList;
import java.util.List;

public class PaymentManager {
    private List<Payment> payments;
    private Context context;

    public PaymentManager(Context context) {
        this.context = context;
        this.payments = new ArrayList<>();
    }

    public boolean addPayment(Payment payment) {
        for (Payment p : payments) {
            if (p.getType() == payment.getType()) return false;
        }
        payments.add(payment);
        return true;
    }

    public void removePayment(Payment payment) {
        payments.remove(payment);
    }

    public List<Payment> getPayments() {
        return payments;
    }

    public List<PaymentType> getAvailablePaymentTypes() {
        List<PaymentType> available = new ArrayList<>(List.of(PaymentType.values()));
        for (Payment payment : payments) {
            available.remove(payment.getType());
        }
        return available;
    }

    public void savePayments() {
        FileHelper.saveToFile(context, payments);
    }

    public void loadPayments() {
        payments = FileHelper.loadFromFile(context);
    }
}
