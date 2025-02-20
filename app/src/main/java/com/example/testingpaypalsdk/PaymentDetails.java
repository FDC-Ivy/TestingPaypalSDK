package com.example.testingpaypalsdk;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import org.json.JSONException;
import org.json.JSONObject;

public class PaymentDetails extends AppCompatActivity {

    private TextView txtId, txtAmount, txtStatus;

    private static final String PAYMENT_DETAILS = "PaymentDetails";
    private static final String PAYMENT_AMOUNT = "PaymentAmount";
    private static final String RESPONSE = "response";
    private static final String ID = "id";
    private static final String STATE = "state";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_details);

        initializeViews();
        processPaymentData(getIntent());
    }

    private void initializeViews() {
        txtStatus = findViewById(R.id.txtStatus);
        txtId = findViewById(R.id.txtId);
        txtAmount = findViewById(R.id.txtAmount);
    }

    private void processPaymentData(Intent intent) {
        if (intent == null) {
            displayToast("No payment details found");
            return;
        }

        String details = intent.getStringExtra(PAYMENT_DETAILS);
        String amount = intent.getStringExtra(PAYMENT_AMOUNT);

        if (details == null || amount == null) {
            displayToast("Payment data is missing");
            return;
        }

        try {
            JSONObject jsonObject = new JSONObject(details);
            JSONObject response = jsonObject.getJSONObject(RESPONSE);
            displayPaymentDetails(response, amount);
        } catch (JSONException e) {
            displayToast("Failed to parse payment details: " + e.getMessage());
        }
    }

    private void displayPaymentDetails(JSONObject response, String amount) {
        try {
            txtId.setText(response.optString(ID, "N/A"));
            txtStatus.setText(response.optString(STATE, "N/A"));
            txtAmount.setText(String.format("$%s", amount));
        } catch (Exception e) {
            displayToast("Error displaying payment information");
        }
    }

    private void displayToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
