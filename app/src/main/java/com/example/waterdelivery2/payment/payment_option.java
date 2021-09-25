package com.example.waterdelivery2.payment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.waterdelivery2.R;

public class payment_option extends AppCompatActivity {
    private Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_option);

        button = findViewById(R.id.card);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCardPayment();
            }
        });
    }
    public void openCardPayment(){
        Intent intent= new Intent(this,payment.class);
        startActivity(intent);
    }
}