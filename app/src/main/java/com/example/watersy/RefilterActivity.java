package com.example.watersy;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.watersy.model.OrderConfirmation;
import com.example.watersy.model.WatersyOrderItem;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RefilterActivity extends AppCompatActivity implements View.OnClickListener{

    OrderConfirmation order;
    OrderConfirmation editPrevOrder;
    CheckBox l19Checkbox, l15CheckBox;
    EditText l19EditText, l15EditText, dateEditText;
    Button storeBtn, orderBtn;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refilter);


        l19Checkbox = findViewById(R.id.checkbox19);
        l15CheckBox = findViewById(R.id.checkbox15);

        l19EditText = findViewById(R.id.qty_19l);
        l15EditText = findViewById(R.id.qty_15l);
        dateEditText = findViewById(R.id.refill_date);

        storeBtn = findViewById(R.id.refill_store_btn);
        orderBtn = findViewById(R.id.refill_order_btn);
        auth = FirebaseAuth.getInstance();

        editPrevOrder = (OrderConfirmation) getIntent().getSerializableExtra("editOrder");

        FirebaseUser user = auth.getCurrentUser();
        if(user == null){
            Intent newLoginIntent = new Intent(this, Login.class);
            startActivity(newLoginIntent);
        }else{
            order = new OrderConfirmation(user.getEmail());
        }
        storeBtn.setOnClickListener(this);
        orderBtn.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(editPrevOrder != null){
            updateUI(editPrevOrder);
        }
    }

    private void updateUI(OrderConfirmation editOrder) {
        Log.d("editOrder", "updateUI: "+editOrder);
        if(editOrder.isItemAvailableInOrder("l19")){
            l19EditText.setText(String.valueOf(editOrder.getItem("l19").qty));
            l19Checkbox.setChecked(true);
        }

        if(editOrder.isItemAvailableInOrder("l15")){
            l15EditText.setText(String.valueOf(editOrder.getItem("l15").qty));
            l15CheckBox.setChecked(true);
        }

        if(!editOrder.date.isEmpty()){
            dateEditText.setText(editOrder.date);
        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if(id == R.id.refill_order_btn){
            setupOrder();
        }else if(id == R.id.refill_store_btn){
            showToast("STORE BUTTON CLICKED");
        }
    }

    private void setupOrder() {
        this.order.items.clear();
        if(!l15CheckBox.isChecked() && !l19Checkbox.isChecked()){
            showToast("Select at least one item to refill");
            return;
        }else {
            if (l19Checkbox.isChecked()) {
                String qty =  l19EditText.getText().toString();
                if (!qty.isEmpty()) {
                    WatersyOrderItem l19 = new WatersyOrderItem("l19", "19 Liters", 150.00, Integer.parseInt(qty));
                    this.order.addItem(l19);
                } else {
                    showToast("Select valid quantity for 19 liter refill");
                    return;
                }
            }
            if(l15CheckBox.isChecked()){
                String qty = l15EditText.getText().toString();
                if (!qty.isEmpty()) {
                    WatersyOrderItem l15 = new WatersyOrderItem("l15", "15 Liters", 120.00, Integer.parseInt(qty));
                    this.order.addItem(l15);
                } else {

                    showToast("Select valid quantity for 15 liter refill");
                    return;
                }
            }
            String orderDate = dateEditText.getText().toString();
            if(orderDate.isEmpty()){
                showToast("Select a date for deliver");
                return;
            }else{
                this.order.setDate(orderDate);
                if(this.order.isValid()){
                    Intent orderConf = new Intent(this, OrderConfirmationActivity.class);
                    orderConf.putExtra("order",this.order);
                    startActivity(orderConf);
                    this.finish();
                }
            }

        }

    }

    private void showToast(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}