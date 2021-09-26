package com.example.watersy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.watersy.model.UserModel;
import com.example.watersy.validations.UserValidation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener{
    Spinner titleSpinner, provinceSpinner;
    UserModel newUser;
    Button createBtn, loginBtn;
    EditText fnameField, lnameField, phoneField, emailField, passwordField, confirmPasswordField;
    FirebaseAuth auth;
    FirebaseDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        newUser = new UserModel();

        titleSpinner = findViewById(R.id.title_spinner);
        provinceSpinner = findViewById(R.id.province_spinner);

        createBtn = findViewById(R.id.reg_btn);
        loginBtn = findViewById(R.id.login_btn_on_reg);

        fnameField = findViewById(R.id.reg_firstname);
        lnameField = findViewById(R.id.reg_lastname);
        phoneField = findViewById(R.id.reg_phone);
        emailField = findViewById(R.id.reg_email);
        passwordField = findViewById(R.id.reg_password);
        confirmPasswordField = findViewById(R.id.reg_confirm_password);

        auth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();

        ArrayAdapter<CharSequence> titleAdapter = ArrayAdapter.createFromResource(this, R.array.title_array, android.R.layout.simple_spinner_item);
        titleAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        titleSpinner.setAdapter(titleAdapter);


        ArrayAdapter<CharSequence> provinceAdapter = ArrayAdapter.createFromResource(this, R.array.province_array, android.R.layout.simple_spinner_item);
        provinceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        provinceSpinner.setAdapter(provinceAdapter);

        titleSpinner.setOnItemSelectedListener(this);
        provinceSpinner.setOnItemSelectedListener(this);

        createBtn.setOnClickListener(this);
        loginBtn.setOnClickListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        int id = adapterView.getId();
        if(id == R.id.title_spinner){
            this.newUser.setTitle(adapterView.getItemAtPosition(i).toString());
        }else{
            this.newUser.setProvince(adapterView.getItemAtPosition(i).toString());
        }
    }



    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        if(id == R.id.reg_btn){
            registerUser();
        }else{
            Intent login = new Intent(this, Login.class);
            startActivity(login);
        }
    }

    private void registerUser() {
        String password = passwordField.getText().toString();
        String confirmPassword = confirmPasswordField.getText().toString();
        UserModel regUser = getUser();
        if(isUserValid(regUser, password, confirmPassword)){
            auth.createUserWithEmailAndPassword(regUser.email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        FirebaseUser user = auth.getCurrentUser();
                        if(user != null){
                            user.sendEmailVerification();
                            DatabaseReference myRef = db.getReference("profiles");
                            myRef.child(regUser.id).setValue(regUser);
                            showToast("Successfully registered");
                            auth.signOut();
                            Intent loginIntent = new Intent(Register.this, Login.class);
                            startActivity(loginIntent);
                        }else{
                            showToast("Something went wrong try again");
                        }
                    }else{
                        showToast(task.getException().getMessage());
                    }
                }
            });
        }
    }

    private UserModel getUser() {
        String fname = fnameField.getText().toString();
        String lname = lnameField.getText().toString();
        String phone = phoneField.getText().toString();
        String email = emailField.getText().toString();
        this.newUser.setEmail(email);
        this.newUser.setfName(fname);
        this.newUser.setlName(lname);
        this.newUser.setPhone(phone);

        return this.newUser;
    }

    private boolean isUserValid(UserModel user, String password, String confirmPassword){

        if(user.fName.isEmpty()){
            showToast("First name cannot be empty");
            return false;
        }else if(user.lName.isEmpty()){
            showToast("Last name cannot be empty");
            return false;
        }else if(!UserValidation.isPhoneValid(user.phone)){
            showToast("Phone number is not valid, use valid Sri lankan number");
            return false;
        }else if(!UserValidation.isEmailValid(user.email)){
            showToast("Email is not valid");
            return false;
        }else if(!UserValidation.isPasswordValid(password)){
            showToast("Valid password should contain at least 8 characters with at least one number and one character.");
            return false;
        }else if(!UserValidation.isPasswordsMatching(password, confirmPassword)){
            showToast("Password and confirm password does not matching");
            return false;
        }

        return true;
    }

    private void showToast(String message){
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}