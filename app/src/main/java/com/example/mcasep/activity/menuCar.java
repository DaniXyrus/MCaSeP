package com.example.mcasep.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.mcasep.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class menuCar extends AppCompatActivity {

    private ImageButton doneBt;
    private EditText edtTxtName, edtTxtBrand, edtTxtModel, edtTxtRegister, edtTxtYear;

    private String register;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_car);

        doneBt = findViewById(R.id.doneBt);
        edtTxtName = findViewById(R.id.edtTxtName);
        edtTxtBrand = findViewById(R.id.edtTxtBrand);
        edtTxtModel = findViewById(R.id.edtTxtModel);
        edtTxtRegister = findViewById(R.id.edtTxtRegister);
        edtTxtYear = findViewById(R.id.edtTxtYear);

        final Intent intent = getIntent();
        register = intent.getStringExtra("carRegistrationNumber");

        firebaseAuth = FirebaseAuth.getInstance();
        loadMoreDetails();

        doneBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(menuCar.this, myCar.class));
            }
        });
    }

    private void loadMoreDetails() {

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users");
        ref.child(firebaseAuth.getUid()).child("My Car").child(register)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        String name = ""+dataSnapshot.child("carName").getValue();
                        String brand = ""+dataSnapshot.child("carBrand").getValue();
                        String model = ""+dataSnapshot.child("carModel").getValue();
                        String year = ""+dataSnapshot.child("carYearOfManufacture").getValue();
                        String registration = ""+dataSnapshot.child("carRegistrationNumber").getValue();

                        edtTxtName.setText(name);
                        edtTxtBrand.setText(brand);
                        edtTxtModel.setText(model);
                        edtTxtRegister.setText(registration);
                        edtTxtYear.setText(year);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

    }
}