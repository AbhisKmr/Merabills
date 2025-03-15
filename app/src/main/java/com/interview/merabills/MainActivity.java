package com.interview.merabills;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import android.app.AlertDialog;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.chip.Chip;
import com.interview.merabills.databinding.ActivityMainBinding;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }

    private PaymentManager paymentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        paymentManager = new PaymentManager(this);

        paymentManager.loadPayments();
        updateUI();

        binding.addPaymentButton.setOnClickListener(v -> showAddPaymentDialog());

        binding.saveButton.setOnClickListener(v -> {
            paymentManager.savePayments();
            showMessage("Payments saved successfully.");
        });
    }

    private void showAddPaymentDialog() {
        PaymentDialog dialog = new PaymentDialog(this, payment -> {
            if (paymentManager.addPayment(payment)) {
                updateUI();
            } else {
                showMessage("This payment type is already added.");
            }
        });
        dialog.show();
    }

    private void updateUI() {
        binding.paymentChipGroup.removeAllViews();
        List<Payment> payments = paymentManager.getPayments();
        binding.paymentTxt.setVisibility(payments.isEmpty() ? GONE : VISIBLE);
        int total = 0;
        for (Payment payment : payments) {
            Chip chip = new Chip(this);
            chip.setText(payment.toString());
            chip.setCloseIconVisible(true);
            chip.setOnCloseIconClickListener(v -> {
                paymentManager.removePayment(payment);
                updateUI();
            });
            binding.paymentChipGroup.addView(chip);
            total += payment.getAmount();
        }
        binding.totalAmount.setText("Total amount = ₹ " + total);
    }

    private void showMessage(String message) {
        new AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton("OK", null)
                .show();
    }
}