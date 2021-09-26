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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UpdateProfile extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener{

    UserModel userDetails;
    Spinner titleSpinner, provinceSpinner;
    EditText fnameField, lnameField, phoneField;
    Button updateBtn, cancelBtn;
    FirebaseDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);
        userDetails = (UserModel) getIntent().getSerializableExtra("userDetails");

        titleSpinner = findViewById(R.id.up_title_spinner);
        provinceSpinner = findViewById(R.id.up_province_spinner);

        updateBtn = findViewById(R.id.update_profile_btn);
        cancelBtn = findViewById(R.id.update_profile_cancel_btn);

        fnameField = findViewById(R.id.update_profile_firstname);
        lnameField = findViewById(R.id.update_profile_lastname);
        phoneField = findViewById(R.id.update_profile_phone);

        ArrayAdapter<CharSequence> titleAdapter = ArrayAdapter.createFromResource(this, R.array.title_array, android.R.layout.simple_spinner_item);
        titleAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        titleSpinner.setAdapter(titleAdapter);
        int selectedTitle = titleAdapter.getPosition(userDetails.title);
        titleSpinner.setSelection(selectedTitle);


        ArrayAdapter<CharSequence> provinceAdapter = ArrayAdapter.createFromResource(this, R.array.province_array, android.R.layout.simple_spinner_item);
        provinceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        provinceSpinner.setAdapter(provinceAdapter);
        int selectedProvince = provinceAdapter.getPosition(userDetails.province);
        provinceSpinner.setSelection(selectedProvince);

        titleSpinner.setOnItemSelectedListener(this);
        provinceSpinner.setOnItemSelectedListener(this);

        updateBtn.setOnClickListener(this);
        cancelBtn.setOnClickListener(this);

        db = FirebaseDatabase.getInstance();
    }

    @Override
    protected void onStart() {
        super.onStart();
        fnameField.setText(userDetails.fName);
        lnameField.setText(userDetails.lName);
        phoneField.setText(userDetails.phone);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if(id == R.id.update_profile_cancel_btn){
            this.finish();
        }else if(id == R.id.update_profile_btn){
            updateProfile();
        }
    }
    private void showToast(String message){
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    private boolean validateAndSetNewDetails(){
        String fname = fnameField.getText().toString();
        String lname = lnameField.getText().toString();
        String phone = phoneField.getText().toString();
        if(fname.isEmpty()){
            showToast("First name cannot be empty");
            return false;
        }else{
            userDetails.setfName(fname);
        }
        if(lname.isEmpty()){
            showToast("First name cannot be empty");
            return false;
        }else{
            userDetails.setlName(lname);
        }
        if(!UserValidation.isPhoneValid(phone)){
           showToast("Phone number is not valid, use valid Sri lankan number");
           return false;
        }else{
            userDetails.setPhone(phone);
        }
        return true;
    }
    private void updateProfile() {
        if(validateAndSetNewDetails()){
            DatabaseReference dbRef = db.getReference("profiles");
            dbRef.child(userDetails.id).setValue(userDetails).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    showToast("Profile updated successfully");
                    Intent mainIntent = new Intent(UpdateProfile.this, MainActivity.class);
                    startActivity(mainIntent);
                    UpdateProfile.this.finish();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    showToast(e.getMessage().toString());
                }
            });
        }else{
            showToast("Something went wrong, please try again");
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        int id = adapterView.getId();
        if(id == R.id.up_title_spinner){
            this.userDetails.setTitle(adapterView.getItemAtPosition(i).toString());
        }else{
            this.userDetails.setProvince(adapterView.getItemAtPosition(i).toString());
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}