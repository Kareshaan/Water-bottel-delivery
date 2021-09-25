package com.example.wartersy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Context context;
    private Spinner spPlatinum;
    private Spinner spGold;
    private Spinner spSilver;
    private Spinner spBronze;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        spPlatinum = findViewById(R.id.spPlatinum);
        spGold = findViewById(R.id.spGold);
        spSilver = findViewById(R.id.spSilver);
        spBronze = findViewById(R.id.spBronze);

        //back button not working
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        //onClick
        findViewById(R.id.btnPromos).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context, Promos.class));
            }
        });
        findViewById(R.id.btnClear).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                spPlatinum.setSelection(0);
                spGold.setSelection(0);
                spSilver.setSelection(0);
                spBronze.setSelection(0);
                Toast.makeText(context, "Cleared", Toast.LENGTH_SHORT).show();
            }
        });
        findViewById(R.id.btnProceed).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getData();
            }
        });
    }

    private void getData() {
        String platinumCount = spPlatinum.getSelectedItem().toString();
        String goldCount = spGold.getSelectedItem().toString();
        String silverCount = spSilver.getSelectedItem().toString();
        String bronzeCount = spBronze.getSelectedItem().toString();
        VoucherRequest voucherRequest = new VoucherRequest(platinumCount, goldCount, silverCount, bronzeCount);

        //open and pass data to next activity
        Intent intent = new Intent(this, VouchersProceed.class);
        intent.putExtra("voucherRequest", voucherRequest);
        startActivity(intent);
    }
}