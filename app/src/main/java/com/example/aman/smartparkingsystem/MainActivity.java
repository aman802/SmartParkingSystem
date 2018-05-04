package com.example.aman.smartparkingsystem;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText email, pass;
    private Button SignUp;
    private TextView SignIn;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        email = findViewById(R.id.emailET);
        pass = findViewById(R.id.passET);
        SignUp = findViewById(R.id.btnSignUp);
        SignIn = findViewById(R.id.signInTV);

        firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser() != null){
            Toast.makeText(this,"User already logged in",Toast.LENGTH_SHORT).show();
            finish();
            startActivity(new Intent(this,SlotActivity.class));
        }

        progressDialog = new ProgressDialog(this);

        SignIn.setOnClickListener(this);
        SignUp.setOnClickListener(this);
    }

    public void registerUser(){
        String E = email.getText().toString().trim();
        String P = pass.getText().toString().trim();

        if(TextUtils.isEmpty(E)){
            Toast.makeText(MainActivity.this,"Email is empty",Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(P)){
            Toast.makeText(MainActivity.this,"Password is empty",Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog.setMessage("Registering User...");
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(E,P)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(MainActivity.this,"Registered Successfully",Toast.LENGTH_SHORT).show();
                            finish();
                            Intent i = new Intent(MainActivity.this,ProfileActivity.class);
                            i.putExtra("Activity","Main");
                            startActivity(i);
                        }
                        else{
                            Toast.makeText(MainActivity.this,"Couldn't register, please try again", Toast.LENGTH_SHORT).show();
                        }
                        progressDialog.dismiss();
                    }
                });

    }

    @Override
    public void onClick(View v) {
        if(v == SignIn){
            //start Login Activity
            startActivity(new android.content.Intent(MainActivity.this,Login.class));
        }
        else if(v == SignUp){
            registerUser();
        }
    }
}
