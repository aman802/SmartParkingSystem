package com.example.aman.smartparkingsystem;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity implements View.OnClickListener {

    EditText Email,pass;
    Button login;
    TextView signup;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        progressDialog = new ProgressDialog(this);

        firebaseAuth = FirebaseAuth.getInstance();

        /*if(firebaseAuth.getCurrentUser() != null) {
            //Start Profile Activity
            Toast.makeText(this, "User already logged in", Toast.LENGTH_SHORT).show();
            finish();
            //startActivity(new Intent(Login.this,ProfileActivity.class));
        }*/

        Email = findViewById(R.id.emailETLogin);
        pass = findViewById(R.id.passETLogin);
        login = findViewById(R.id.btnLogin);
        signup = findViewById(R.id.signUpTV);

        login.setOnClickListener(this);
        signup.setOnClickListener(this);
    }

    private void userLogin(){
        String email = Email.getText().toString().trim();
        String password = pass.getText().toString().trim();

        if(TextUtils.isEmpty(email)){
            Toast.makeText(Login.this,"Email is empty", Toast.LENGTH_SHORT).show();
            //stops the func from executing
            return;
        }

        if( TextUtils.isEmpty(password)){
            Toast.makeText(Login.this,"Password is empty", Toast.LENGTH_SHORT).show();
            //stops the func from executing
            return;
        }

        progressDialog.setMessage("Logging In User...");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if(task.isSuccessful()){
                            //Start Slots Activity
                            //Toast.makeText(LoginActivity.this,"Under Construction", Toast.LENGTH_SHORT).show();
                            //finish();
                            //startActivity(new Intent(Login.this,SlotsActivity.class));
                            Toast.makeText(Login.this,"Success",Toast.LENGTH_SHORT).show();
                            Log.d("test","yaha tak chala?");
                            Intent i = new Intent(Login.this,ProfileActivity.class);
                            i.putExtra("Activity","Login");
                            startActivity(i);
                        }
                        else{
                            Toast.makeText(Login.this,"Error in Logging in", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    public void onClick(View v) {
        if(v == login){
            userLogin();
        }

        else if(v == signup){
            finish();
            startActivity(new Intent(Login.this,MainActivity.class));
        }
    }
}
