package com.example.aman.smartparkingsystem;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener{

    TextView ID,username,email;
    EditText name,phone,LP1,LP2,LP3;
    Button save;

    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser() == null){
            finish();
            startActivity(new Intent(this,Login.class));
        }

        databaseReference = FirebaseDatabase.getInstance().getReference("UserInfo");
        if(databaseReference == null)
            Log.d("teesra", "Kya ho gaya?");


        ID = findViewById(R.id.tvID);
        email = findViewById(R.id.tvEmail);
        name = findViewById(R.id.etName);
        phone = findViewById(R.id.etPhone);
        LP1 = findViewById(R.id.etLP1);
        LP2 = findViewById(R.id.etLP2);
        LP3 = findViewById(R.id.etLP3);
        save = findViewById(R.id.btnSave);
        username = findViewById(R.id.usernametv);

        String act = getIntent().getStringExtra("Activity");
        if(act.equals("Login")) {

            if (firebaseAuth.getCurrentUser() != null) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                ID.setText(user.getUid());
                email.setText(user.getEmail());
                databaseReference.child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        int c = 0;
                        for (DataSnapshot d : dataSnapshot.getChildren()) {
                            switch (c){
                                case 0: LP1.setText(d.getValue(String.class));
                                        break;
                                case 1: LP2.setText(d.getValue(String.class));
                                    break;
                                case 2: LP3.setText(d.getValue(String.class));
                                    break;
                                case 3: email.setText(d.getValue(String.class));
                                    break;
                                case 4: ID.setText(d.getValue(String.class));
                                    break;
                                case 5: name.setText(d.getValue(String.class));
                                        username.setText(d.getValue(String.class));
                                    break;
                                case 6: phone.setText(d.getValue(String.class));
                                    break;
                            }
                            c++;
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        }

        save.setOnClickListener(this);
    }

    public void saveUserData() {
        User user = null;
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        String Name = name.getText().toString().trim();
        String Phone = phone.getText().toString().trim();
        String l1 = LP1.getText().toString().trim();
        if (TextUtils.isEmpty(l1)) {
            Toast.makeText(ProfileActivity.this, "License Plate 1 can't be empty", Toast.LENGTH_SHORT).show();
            return;
        }
        String l2 = LP2.getText().toString().trim();
        boolean flag =false;
        if (!TextUtils.isEmpty(l2)) {
            String l3 = LP3.getText().toString().trim();
            if (!TextUtils.isEmpty(l3)) {
                user = new User(Name,Phone,firebaseUser.getEmail(),firebaseUser.getUid(),l1,l2,l3);
            }
            else
                user = new User(Name,Phone,firebaseUser.getEmail(),firebaseUser.getUid(),l1,l2);
        }
        else{
            user = new User(Name, Phone, firebaseUser.getEmail(), firebaseUser.getUid(), l1);
        }

        if(user != null) {
            databaseReference.child(user.getId()).setValue(user);
            Toast.makeText(this, "Information Saved", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View v) {
        if(v == save) {
            saveUserData();
            finish();
            startActivity(new Intent(ProfileActivity.this, SlotActivity.class));
        }
    }
}
