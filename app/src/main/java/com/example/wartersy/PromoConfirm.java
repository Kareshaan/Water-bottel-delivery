package com.example.wartersy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.drm.ProcessedData;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;

public class PromoConfirm extends AppCompatActivity {
    private Context context;
    private PromoRequest promoRequest;
    private FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promo_confirm);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        context = this;
        database = FirebaseDatabase.getInstance();

        setData();

        findViewById(R.id.btnEdit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish(); //go back
            }
        });

        findViewById(R.id.btnBuy).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addToFirebase();
            }
        });

    }

    private void setData() {
        int resourceId = 0;
        String promoName = null;
        String bottleSize = null;
        String quantity = null;
        String price = null;
        int itemNo = getIntent().getIntExtra("itemNo", 0);
        switch (itemNo) {
            case 1: {
                resourceId = R.drawable.img1l;
                promoName = "1L X 10 FOR 600/=";
                bottleSize = "1L";
                quantity = "10";
                price = "600";
                break;
            }
            case 2: {
                resourceId = R.drawable.img5l;
                promoName = "5L X 8 FOR 600/=";
                bottleSize = "5L";
                quantity = "8";
                price = "600";
                break;
            }
            case 3: {
                resourceId = R.drawable.img19l;
                promoName = "19L X 3 FOR 700/=";
                bottleSize = "19L";
                quantity = "3";
                price = "700";
                break;
            }
        }

        //show to user
        ImageView ivPromoImage = findViewById(R.id.promoImage);
        TextView tvPromoName = findViewById(R.id.promoName);
        ivPromoImage.setImageResource(resourceId);
        tvPromoName.setText(promoName);

        //set request to add to firebase
        promoRequest = new PromoRequest(promoName, bottleSize, quantity, price);
    }

    private void addToFirebase() {
        //show progress dialog
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Please wait...");
        progressDialog.show();

        //generate unique id for the request
        String reqId = "PR" + (String.valueOf(System.currentTimeMillis()).substring(6)); //remove first 6 digits of current time
                                                                                           //in milli seconds to shorten the id
        database.getReference("promo-requests").child(reqId).setValue(promoRequest)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(context, "Request sent", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context, "Failed\n" + e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                })
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        progressDialog.dismiss();
                    }
                });
    }

}