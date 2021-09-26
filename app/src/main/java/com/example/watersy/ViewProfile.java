package com.example.watersy;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.watersy.model.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ViewProfile extends AppCompatActivity implements View.OnClickListener{

    TextView titleView, fnameView, lnameView, emailView, provinceView, phoneView;
    FirebaseAuth auth;
    FirebaseUser user;
    FirebaseDatabase db;
    Button gotoUpdateBtn, deleteAccountBtn;
    UserModel userProfile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile);

        titleView = findViewById(R.id.title_view);
        fnameView = findViewById(R.id.firstName_view);
        lnameView = findViewById(R.id.lastname_view);
        emailView = findViewById(R.id.email_view);
        provinceView = findViewById(R.id.province_view);
        phoneView = findViewById(R.id.phone_view);

        gotoUpdateBtn = findViewById(R.id.goto_update_profile_btn);
        deleteAccountBtn =findViewById(R.id.delete_profile_btn);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        db = FirebaseDatabase.getInstance();

        gotoUpdateBtn.setOnClickListener(this);
        deleteAccountBtn.setOnClickListener(this);

    }

    @Override
    protected void onStart() {
        super.onStart();
        if(user == null){
            Intent loginIntent = new Intent(this, Login.class);
            finishAffinity();
            startActivity(loginIntent);
        }else {
            DatabaseReference dbRef = db.getReference("profiles");
            dbRef.orderByChild("email").equalTo(user.getEmail()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    if (!task.isSuccessful()) {

                        showToast(task.getException().getMessage());
                    } else {

                        for (DataSnapshot snapshot : task.getResult().getChildren()) {
                            userProfile = snapshot.getValue(UserModel.class);
                            updateUI(userProfile);
                            break;
                        }

                    }
                }
            });
        }
    }

    private void showToast(String message){
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    private void updateUI(UserModel upUser){
        titleView.setText(upUser.title);
        fnameView.setText(upUser.fName);
        lnameView.setText(upUser.lName);
        provinceView.setText(upUser.province);
        phoneView.setText(upUser.phone);
        emailView.setText(upUser.email);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if(id == R.id.goto_update_profile_btn){
            Intent updateProf = new Intent(this, UpdateProfile.class);
            updateProf.putExtra("userDetails", userProfile);
            startActivity(updateProf);
        }else if(id == R.id.delete_profile_btn){
            new AlertDialog.Builder(this)
                    .setTitle("Delete account")
                    .setMessage("Are you sure you want to delete this account? All your data will be deleted.You may need to logout and re-login if you haven't recently log in to this account")

                    // Specifying a listener allows you to take an action before dismissing the dialog.
                    // The dialog is automatically dismissed when a dialog button is clicked.
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            deleteUser();
                        }
                    })

                    // A null listener allows the button to dismiss the dialog and take no further action.
                    .setNegativeButton(android.R.string.no, null)
                    .setIcon(android.R.drawable.ic_menu_delete)
                    .show();
        }
    }

    private void deleteUser() {
        final String uid = user.getUid();
        user.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    DatabaseReference dbRef = db.getReference("profiles");
                    dbRef.child(userProfile.id).removeValue();
                    auth.signOut();
                    Intent loginIntent = new Intent(ViewProfile.this, Login.class);
                    finishAffinity();
                    startActivity(loginIntent);
                    showToast("Account deleted successfully");
                }else{
                    showToast("Error while deleting your profile please try again"+task.getException().getMessage());
                }
            }
        });
    }
}