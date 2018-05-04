package com.example.aman.smartparkingsystem;

import android.app.Activity;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;

public class MyDialog extends DialogFragment implements View.OnClickListener {
    Button OK,Cancel;
    RadioGroup radioGroup;
    Communicator communicator;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        communicator = (Communicator) activity;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_dialog,null);
        OK = view.findViewById(R.id.dialogOK);
        Cancel = view.findViewById(R.id.dialogCancel);
        OK.setOnClickListener(this);
        Cancel.setOnClickListener(this);
        radioGroup = view.findViewById(R.id.radioGroup);
        setCancelable(false);
        return view;
    }

    @Override
    public void onClick(View v) {
        if(v == OK){
            int car = radioGroup.getCheckedRadioButtonId();
            int num = -1;
            if(car == R.id.radio1)
                num = 1;
            else if(car == R.id.radio2)
                num = 2;
            else if(car == R.id.radio3)
                num = 3;
            communicator.onDialogMessage("OK was clicked",num);
            dismiss();
        }
        else if(v == Cancel){
            communicator.onDialogMessage("Cancel was clicked");
            dismiss();
        }
    }

    public interface Communicator {
        public void onDialogMessage(String message,final int num);
        public void onDialogMessage(String message);
    }
}
