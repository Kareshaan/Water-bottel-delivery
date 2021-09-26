package com.example.watersy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class VerifyEmail extends AppCompatActivity implements View.OnClickListener{
    Button okayBtn;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_email);
        okayBtn = findViewById(R.id.okay_btn);
        okayBtn.setOnClickListener(this);
        auth = FirebaseAuth.getInstance();

    }

    @Override
    public void onClick(View view) {
        int id= view.getId();
        if(id == R.id.okay_btn){
            FirebaseUser user = auth.getCurrentUser();
            if(user != null){
                if(user.isEmailVerified()){
                    Intent i = new Intent(this, MainActivity.class);
                    startActivity(i);
                }else{
                    Intent i = new Intent(this, Login.class);
                    startActivity(i);
                }
            }else{
                Intent i = new Intent(this, Login.class);
                startActivity(i);
            }

        }
    }
}