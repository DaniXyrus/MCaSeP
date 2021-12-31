package com.example.mcasep.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mcasep.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class addMotor extends AppCompatActivity {

    private EditText edtTxtName, edtTxtBrand, edtTxtModel, edtTxtYear;
    private Button btnAddMotor;

    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_motor);

        edtTxtName = findViewById(R.id.edtTxtName);
        edtTxtBrand = findViewById(R.id.edtTxtBrand);
        edtTxtModel = findViewById(R.id.edtTxtModel);
        edtTxtYear = findViewById(R.id.edtTxtYear);
        btnAddMotor = findViewById(R.id.btnAddMotor);

        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please Wait");
        progressDialog.setCanceledOnTouchOutside(false);

        btnAddMotor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputData();

            }
        });

    }

    private String motorName, motorBrand, motorModel, motorYear;
    private void inputData() {

        motorName = edtTxtName.getText().toString().trim();
        motorBrand = edtTxtBrand.getText().toString().trim();
        motorModel = edtTxtModel.getText().toString().trim();
        motorYear = edtTxtYear.getText().toString().trim();

        if(TextUtils.isEmpty(motorName)){
            Toast.makeText(this,"Enter Your Motor Name", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(motorBrand)){
            Toast.makeText(this,"Enter Your Motor Brand", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(motorModel)){
            Toast.makeText(this,"Enter Your Motor Model", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(motorYear)){
            Toast.makeText(this,"Enter Your Motor Year Of Manufacture", Toast.LENGTH_SHORT).show();
            return;
        }

        addMotorNow();

    }

    private void addMotorNow() {

        progressDialog.setMessage("Saving Motorcycle details");

        final String time = ""+System.currentTimeMillis();

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("motorName", "" + motorName);
        hashMap.put("motorBrand", "" + motorBrand);
        hashMap.put("motorModel", "" + motorModel);
        hashMap.put("motorYearOfManufacture", "" + motorYear);
        hashMap.put("motorRegistrationNumber", "" + time);

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users");
        ref.child(firebaseAuth.getUid()).child("My Motorcycle").child(time).setValue(hashMap)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        progressDialog.dismiss();
                        Toast.makeText(addMotor.this, "Added", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(addMotor.this, myMotor.class));
                        finishAffinity();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(addMotor.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });
    }
}