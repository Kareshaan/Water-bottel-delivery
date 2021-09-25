package com.example.wartersy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Promos extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promos);
        Context context = this;

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        findViewById(R.id.vouchers).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context, MainActivity.class));
            }
        });

        findViewById(R.id.buy1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showConfirmation(1);
            }
        });

        findViewById(R.id.buy2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showConfirmation(2);
            }
        });

        findViewById(R.id.buy3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showConfirmation(3);
            }
        });
    }

    private void showConfirmation(int item) {
        Intent intent = new Intent(this, PromoConfirm.class);
        intent.putExtra("itemNo", item);
        startActivity(intent);
    }
}