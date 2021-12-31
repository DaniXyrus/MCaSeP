package com.example.mcasep.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mcasep.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class addCar extends AppCompatActivity {

    private EditText edtTxtName, edtTxtBrand, edtTxtModel, edtTxtYear;
    private Button btnAddCar;

    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_car);

        edtTxtName = findViewById(R.id.edtTxtName);
        edtTxtBrand = findViewById(R.id.edtTxtBrand);
        edtTxtModel = findViewById(R.id.edtTxtModel);
        edtTxtYear = findViewById(R.id.edtTxtYear);
        btnAddCar = findViewById(R.id.btnAddCar);

        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please Wait");
        progressDialog.setCanceledOnTouchOutside(false);

        btnAddCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputData();

            }
        });


    }

    private String carName, carBrand, carModel, carYear;
    private void inputData() {

        carName = edtTxtName.getText().toString().trim();
        carBrand = edtTxtBrand.getText().toString().trim();
        carModel = edtTxtModel.getText().toString().trim();
        carYear = edtTxtYear.getText().toString().trim();

        if(TextUtils.isEmpty(carName)){
            Toast.makeText(this,"Enter Your Car Name", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(carBrand)){
            Toast.makeText(this,"Enter Your Car Brand", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(carModel)){
            Toast.makeText(this,"Enter Your Car Model", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(carYear)){
            Toast.makeText(this,"Enter Your Car Year Of Manufacture", Toast.LENGTH_SHORT).show();
            return;
        }

        addCarNow();

    }

    private void addCarNow() {

        progressDialog.setMessage("Saving Car details");

        final String time = ""+System.currentTimeMillis();

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("carName", "" + carName);
        hashMap.put("carBrand", "" + carBrand);
        hashMap.put("carModel", "" + carModel);
        hashMap.put("carYearOfManufacture", "" + carYear);
        hashMap.put("carRegistrationNumber", "" + time);

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users");
        ref.child(firebaseAuth.getUid()).child("My Car").child(time).setValue(hashMap)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        progressDialog.dismiss();
                        Toast.makeText(addCar.this, "Added", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(addCar.this, myCar.class));
                        finishAffinity();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(addCar.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });
    }
}