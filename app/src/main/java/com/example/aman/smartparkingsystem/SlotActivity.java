package com.example.aman.smartparkingsystem;

import android.annotation.TargetApi;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class SlotActivity extends AppCompatActivity implements View.OnClickListener, MyDialog.Communicator{

    View s1,s2,s3,s4;
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slot);

        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("Slots");

        s1 = findViewById(R.id.slot1);
        s2 = findViewById(R.id.slot2);

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int c = 1;
                for(DataSnapshot d : dataSnapshot.getChildren()){
                    int a;
                    if(c == 1){
                        a = 1;
                        for(DataSnapshot ds : d.getChildren()){
                            int booked, filled;
                            if(a == 1){
                                booked = ds.getValue(Integer.class);
                                if(booked == 0){
                                    s1.setBackground(getDrawable(R.drawable.rectangle_available));
                                    break;
                                }
                                else{
                                    //s1.setBackground(getDrawable(R.drawable.rectangle_booked));
                                }
                            }
                            if(a == 2){
                                filled = ds.getValue(Integer.class);
                                if(filled == 0){
                                    //s1.setBackground(getDrawable(R.drawable.rectangle_booked));
                                }
                                else{
                                    s1.setBackground(getDrawable(R.drawable.rectangle_filled));
                                }
                            }
                            a++;
                        }
                    }

                    if(c == 2){
                        a = 1;
                        for(DataSnapshot ds : d.getChildren()){
                            int booked = -1, filled = -1;
                            if(a == 1){
                                booked = ds.getValue(Integer.class);
                                if(booked == 0){
                                    s2.setBackground(getDrawable(R.drawable.rectangle_available));
                                    break;
                                }
                                else{
                                    s2.setBackground(getDrawable(R.drawable.rectangle_booked));
                                }
                            }
                            if(a == 2){
                                filled = ds.getValue(Integer.class);
                                if(filled == 0){
                                    s2.setBackground(getDrawable(R.drawable.rectangle_booked));
                                }
                                else{
                                    s2.setBackground(getDrawable(R.drawable.rectangle_filled));
                                }
                            }
                            a++;
                        }
                    }

                    c++;
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        s1.setOnClickListener(this);
        s2.setOnClickListener(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_slot,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        if(id == R.id.refresh){
            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    int c = 1;
                    for(DataSnapshot d : dataSnapshot.getChildren()){
                        int a;
                        if(c == 1){
                            a = 1;
                            for(DataSnapshot ds : d.getChildren()){
                                int booked = -1, filled = -1;
                                if(a == 1){
                                    booked = ds.getValue(Integer.class);
                                    if(booked == 0){
                                        s1.setBackground(getDrawable(R.drawable.rectangle_available));
                                        break;
                                    }
                                    else{
                                        s1.setBackground(getDrawable(R.drawable.rectangle_booked));
                                    }
                                }
                                if(a == 2){
                                    filled = ds.getValue(Integer.class);
                                    if(filled == 0){
                                        s1.setBackground(getDrawable(R.drawable.rectangle_booked));
                                    }
                                    else{
                                        s1.setBackground(getDrawable(R.drawable.rectangle_filled));
                                    }
                                }
                                a++;
                            }
                        }

                        if(c == 2){
                            a = 1;
                            for(DataSnapshot ds : d.getChildren()){
                                int booked = -1, filled = -1;
                                if(a == 1){
                                    booked = ds.getValue(Integer.class);
                                    if(booked == 0){
                                        s2.setBackground(getDrawable(R.drawable.rectangle_available));
                                        break;
                                    }
                                    else{
                                        s2.setBackground(getDrawable(R.drawable.rectangle_booked));
                                    }
                                }
                                if(a == 2){
                                    filled = ds.getValue(Integer.class);
                                    if(filled == 0){
                                        s2.setBackground(getDrawable(R.drawable.rectangle_booked));
                                    }
                                    else{
                                        s2.setBackground(getDrawable(R.drawable.rectangle_filled));
                                    }
                                }
                                a++;
                            }
                        }

                        c++;
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
            return true;
        }

        if(id == R.id.logout){
            firebaseAuth.signOut();
            if(firebaseAuth.getCurrentUser() == null) {
                Toast.makeText(this, "Logout Successful", Toast.LENGTH_SHORT).show();
                finish();
                startActivity(new Intent(this, Login.class));
            }
            else
                Toast.makeText(this,"Logout Unsuccessful", Toast.LENGTH_SHORT).show();
        }

        if(id == R.id.profile){
            finish();
            Intent i = new Intent(SlotActivity.this,ProfileActivity.class);
            i.putExtra("Activity","Login");
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        if(v == s1){
            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    int c = 1;
                    for(DataSnapshot d : dataSnapshot.getChildren()){
                        int a;
                        if(c == 1){
                            a = 1;
                            for(DataSnapshot ds : d.getChildren()){
                                int booked = -1, filled = -1;
                                if(a == 1){
                                    booked = ds.getValue(Integer.class);
                                    if(booked == 0){
                                        s1.setBackground(getDrawable(R.drawable.rectangle_available));
                                        FragmentManager manager = getFragmentManager();
                                        MyDialog myDialog = new MyDialog();
                                        myDialog.show(manager,"MyDialog");
                                        break;
                                    }
                                    else{
                                        Toast.makeText(SlotActivity.this,"Can't proceed", Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                }
                                if(a == 2){
                                    filled = ds.getValue(Integer.class);
                                    if(filled == 0){
                                        Toast.makeText(SlotActivity.this,"Can't proceed", Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                    else{
                                        Toast.makeText(SlotActivity.this,"Can't proceed", Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                }
                                a++;
                            }
                        }

                        c++;
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
        else if(v == s2){

        }
    }

    @Override
    public void onDialogMessage(String message, int num) {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
        databaseReference.child("S1").child("booked").setValue(1);
        /*Intent service = new Intent(getApplicationContext(), MyService.class);
        service.putExtra("Slot",1);
        startService(service);*/
        final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleAtFixedRate(new Runnable() {
                                                @Override
                                                public void run() {
                                                    background();
                                                }
                                            }
                , 1, 0, TimeUnit.MINUTES);
    }

    @Override
    public void onDialogMessage(String message) {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }

    public void background(){
        databaseReference.child("S1").child("booked").setValue(0);
        databaseReference.child("S1").child("filled").setValue(0);
    }
}
