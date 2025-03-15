package com.interview.merabills;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import com.interview.merabills.databinding.DialogAddPaymentBinding;
import java.util.List;

public class PaymentDialog extends Dialog {

    private DialogAddPaymentBinding binding;

    private final PaymentManager paymentManager;
    private final PaymentDialogListener listener;

    public interface PaymentDialogListener {
        void onPaymentAdded(Payment payment);
    }

    public PaymentDialog(Context context, PaymentDialogListener listener) {
        super(context);
        this.listener = listener;
        paymentManager = new PaymentManager(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DialogAddPaymentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        List<PaymentType> availableTypes = paymentManager.getAvailablePaymentTypes();
        binding.paymentTypeSpinner.setAdapter(new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, availableTypes));

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