package com.example.mcasep.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

public class menuMotor extends AppCompatActivity {

    private ImageButton doneBt;
    private EditText edtTxtName, edtTxtBrand, edtTxtModel, edtTxtRegister, edtTxtYear;

    private String register;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_motor);

        doneBt = findViewById(R.id.doneBt);
        edtTxtName = findViewById(R.id.edtTxtName);
        edtTxtBrand = findViewById(R.id.edtTxtBrand);
        edtTxtModel = findViewById(R.id.edtTxtModel);
        edtTxtRegister = findViewById(R.id.edtTxtRegister);
        edtTxtYear = findViewById(R.id.edtTxtYear);

        final Intent intent = getIntent();
        register = intent.getStringExtra("motorRegistrationNumber");

        firebaseAuth = FirebaseAuth.getInstance();
        loadMoreMotorDetails();

        doneBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(menuMotor.this, myMotor.class));
            }
        });
    }

    private void loadMoreMotorDetails() {

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users");
        ref.child(firebaseAuth.getUid()).child("My Motorcycle").child(register)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        String name = ""+dataSnapshot.child("motorName").getValue();
                        String brand = ""+dataSnapshot.child("motorBrand").getValue();
                        String model = ""+dataSnapshot.child("motorModel").getValue();
                        String year = ""+dataSnapshot.child("motorYearOfManufacture").getValue();
                        String registration = ""+dataSnapshot.child("motorRegistrationNumber").getValue();

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