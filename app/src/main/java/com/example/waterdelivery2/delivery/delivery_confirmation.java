package com.example.waterdelivery2.delivery;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.waterdelivery2.R;
import com.example.waterdelivery2.edit_form;

public class delivery_confirmation extends AppCompatActivity {
    private Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_confirmation);

        button = findViewById(R.id.edit);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openeditform();
            }
        });

    }
    public void openeditform(){
        Intent intent= new Intent(this, edit_form.class);
        startActivity(intent);
    }
}