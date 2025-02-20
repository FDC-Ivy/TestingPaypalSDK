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

    private static final String KEY_PAYMENT_DETAILS = "PaymentDetails";
    private static final String KEY_PAYMENT_AMOUNT = "PaymentAmount";
    private static final String KEY_RESPONSE = "response";
    private static final String KEY_ID = "id";
    private static final String KEY_STATE = "state";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_details);

        initializeViews();
        handleIntentData(getIntent());
    }

    private void initializeViews() {
        txtStatus = findViewById(R.id.txtStatus);
        txtId = findViewById(R.id.txtId);
        txtAmount = findViewById(R.id.txtAmount);
    }

    private void handleIntentData(Intent intent) {
        if (intent == null) {
            showToast("No payment details available");
            return;
        }

        String paymentDetails = intent.getStringExtra(KEY_PAYMENT_DETAILS);
        String paymentAmount = intent.getStringExtra(KEY_PAYMENT_AMOUNT);

        if (paymentDetails == null || paymentAmount == null) {
            showToast("Invalid payment data");
            return;
        }

        try {
            JSONObject jsonObject = new JSONObject(paymentDetails);
            JSONObject response = jsonObject.getJSONObject(KEY_RESPONSE);
            showDetails(response, paymentAmount);
        } catch (JSONException e) {
            showToast("Error parsing payment details: " + e.getMessage());
        }
    }

    private void showDetails(JSONObject response, String paymentAmount) {
        try {
            txtId.setText(response.optString(KEY_ID, "N/A"));
            txtStatus.setText(response.optString(KEY_STATE, "N/A"));
            txtAmount.setText(String.format("$%s", paymentAmount));
        } catch (Exception e) {
            showToast("Error displaying payment details");
        }
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
