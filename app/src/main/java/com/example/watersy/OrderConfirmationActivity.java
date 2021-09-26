package com.example.watersy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.watersy.model.OrderConfirmation;
import com.example.watersy.model.WatersyOrderItem;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;


public class OrderConfirmationActivity extends AppCompatActivity implements View.OnClickListener{

    private RecyclerView ordersRecyclerView;
    private RecyclerView.Adapter oAdapter;
    private RecyclerView.LayoutManager oLayoutManager;
    public Button orderConfirmBtn, editOrderBtn, cancelBtn;
    TextView totalPriceTV;
    OrderConfirmation orderConfirmation;
    FirebaseDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_confirmation);
        ordersRecyclerView = findViewById(R.id.oc_recyclerview);
        ordersRecyclerView.setHasFixedSize(true);
        oLayoutManager = new LinearLayoutManager(this);

        orderConfirmBtn = findViewById(R.id.order_continue_btn);
        cancelBtn = findViewById(R.id.cancel_order_btn);
        editOrderBtn = findViewById(R.id.edit_order_btn);

        totalPriceTV = findViewById(R.id.oc_total_tv);

        orderConfirmBtn.setOnClickListener(this);
        cancelBtn.setOnClickListener(this);
        editOrderBtn.setOnClickListener(this);
        db = FirebaseDatabase.getInstance();
        orderConfirmation = (OrderConfirmation) getIntent().getSerializableExtra("order");
        if(orderConfirmation == null){
            Intent orderIntent = new Intent(this, RefilterActivity.class);
            startActivity(orderIntent);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        oAdapter = new OrderConfirmationAdapter(orderConfirmation.items);
        ordersRecyclerView.setLayoutManager(oLayoutManager);
        ordersRecyclerView.setAdapter(oAdapter);

        double totalPrice = 0;
        for (WatersyOrderItem item : orderConfirmation.items){
            totalPrice+= (item.qty*item.itemPrice);
        }

        totalPriceTV.setText(String.valueOf(totalPrice));

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        if(id == R.id.order_continue_btn){
            saveOrder();
        }else if(id == R.id.cancel_order_btn){
            Intent mainAct = new Intent(this, NavDrawerActivity.class);
            this.finish();
            startActivity(mainAct);
        }else if(id == R.id.edit_order_btn){
            Intent orderIntent = new Intent(this, RefilterActivity.class);
            orderIntent.putExtra("editOrder", orderConfirmation);
            startActivity(orderIntent);
            this.finish();
        }
    }

    private void saveOrder() {
        DatabaseReference dbRef = db.getReference("orders");
        dbRef.child(this.orderConfirmation.id).setValue(this.orderConfirmation).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                     if(task.isSuccessful()){
                         Toast.makeText(OrderConfirmationActivity.this, "Order placed", Toast.LENGTH_LONG).show();

                     }else{
                         Toast.makeText(OrderConfirmationActivity.this, "Something went wrong while placing the order", Toast.LENGTH_LONG).show();
                     }
            }
        });
    }
}