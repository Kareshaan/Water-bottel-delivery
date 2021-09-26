package com.example.watersy;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.watersy.validations.UserValidation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity implements View.OnClickListener{
    EditText emailField, passwordField;
    Button login, register;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailField = findViewById(R.id.loginEmail);
        passwordField = findViewById(R.id.loginPassword);

        login = findViewById(R.id.login_btn);
        register = findViewById(R.id.register_btn_on_login);

        login.setOnClickListener(this);
        register.setOnClickListener(this);

        auth = FirebaseAuth.getInstance();
        if(auth.getCurrentUser() != null){
            Intent home = new Intent(this, MainActivity.class);
            startActivity(home);
        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if(id == R.id.login_btn){
            loginUser();
        }else{
            Intent regIntent = new Intent(this, Register.class);
            startActivity(regIntent);
        }
    }

    private void loginUser() {
        String email = emailField.getText().toString();
        String password = passwordField.getText().toString();

        if(UserValidation.isEmailValid(email)){
            if(UserValidation.isPasswordValid(password)){
                auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful() ){
                            FirebaseUser user = auth.getCurrentUser();
                            if(user != null){
                                if(user.isEmailVerified()){
                                    Intent home = new Intent(Login.this, MainActivity.class);
                                    startActivity(home);
                                }else{
                                    user.sendEmailVerification();
                                    auth.signOut();
                                    Intent verifyEmail = new Intent(Login.this, VerifyEmail.class);
                                    startActivity(verifyEmail);
                                }
                            }else{
                                Toast.makeText(Login.this, "Please check your email and password",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            Toast.makeText(Login.this, "Please check your email and password",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }else{
                Toast.makeText(Login.this, "Valid password should contain at least 8 characters with at least one number and one character.",
                        Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(Login.this, "Email invalid",
                    Toast.LENGTH_SHORT).show();
        }
    }
}