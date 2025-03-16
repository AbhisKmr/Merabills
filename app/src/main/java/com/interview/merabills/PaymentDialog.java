package com.interview.merabills;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SpinnerAdapter;

import com.interview.merabills.databinding.DialogAddPaymentBinding;

import java.util.List;

public class PaymentDialog extends Dialog {

    private DialogAddPaymentBinding binding;

    private final PaymentManager paymentManager;
    private final PaymentDialogListener listener;

    public interface PaymentDialogListener {
        void onPaymentAdded(Payment payment);
    }

    public PaymentDialog(Context context, PaymentManager paymentManager, PaymentDialogListener listener) {
        super(context);
        this.listener = listener;
        this.paymentManager = paymentManager;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DialogAddPaymentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        List<PaymentType> availableTypes = paymentManager.getAvailablePaymentTypes();
        SpinnerAdapter spinnerAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, availableTypes);
        binding.paymentTypeSpinner.setAdapter(spinnerAdapter);

        binding.paymentTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                PaymentType type = (PaymentType) parent.getItemAtPosition(position);
                binding.additionalDetails1.setVisibility(type != PaymentType.CASH ? VISIBLE : GONE);
                binding.additionalDetails2.setVisibility(type != PaymentType.CASH ? VISIBLE : GONE);

                if (type == PaymentType.BANK_TRANSFER) {
                    binding.additionalDetails1.setHint("Account holder name");
                    binding.additionalDetails2.setHint("Bank Acc No.");
                } else if (type == PaymentType.CREDIT_CARD) {
                    binding.additionalDetails1.setHint("Card holder name");
                    binding.additionalDetails2.setHint("Card No.");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        binding.saveButton.setOnClickListener(v -> {
            if (binding.amountInput.getText().toString().isEmpty()) {
                binding.amountInput.setError("Amount can't be empty!");
                return;
            } else if (Integer.parseInt(binding.amountInput.getText().toString()) == 0) {
                return;
            }

            int amount = Integer.parseInt(binding.amountInput.getText().toString());
            PaymentType type = (PaymentType) binding.paymentTypeSpinner.getSelectedItem();
            String provider = binding.providerInput.getText().toString();
            String reference = binding.referenceInput.getText().toString();

            Payment payment = new Payment(amount, type, provider, reference);
            listener.onPaymentAdded(payment);
            dismiss();
        });
    }
}