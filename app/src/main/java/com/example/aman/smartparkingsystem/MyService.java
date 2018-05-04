package com.example.aman.smartparkingsystem;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MyService extends Service {
    DatabaseReference databaseReference;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        /*try{
            Thread.sleep(120000);
            databaseReference = FirebaseDatabase.getInstance().getReference("Slots");
            final int num = intent.getIntExtra("Slot",-1);
            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                int c = 1;
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for(DataSnapshot d : dataSnapshot.getChildren()){
                        if(c == num){
                            Slots slots = new Slots(0,0);
                            databaseReference.child("S1").setValue(slots);
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });*/

        stopSelf();
        return super.onStartCommand(intent, flags, startId);
    }
}
