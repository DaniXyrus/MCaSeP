package com.example.mcasep.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.mcasep.R;
import com.example.mcasep.adapter.AdapterCar;
import com.example.mcasep.adapter.AdapterMotor;
import com.example.mcasep.model.Car;
import com.example.mcasep.model.Motor;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class myMotor extends AppCompatActivity {

    private ImageButton addBt, backBtn;
    private RecyclerView listMotorTv;

    private ArrayList<Motor> motorsList;
    private AdapterMotor adapterMotor;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_motor);

        addBt = findViewById(R.id.addBt);
        backBtn = findViewById(R.id.backBtn);
        listMotorTv = findViewById(R.id.listMotorTv);

        firebaseAuth = FirebaseAuth.getInstance();

        loadMyMotor();

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(myMotor.this, MainActivity.class));
            }
        });

        addBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(myMotor.this, addMotor.class));
            }
        });
    }

    private void loadMyMotor() {

        motorsList = new ArrayList<>();

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users");
        ref.child(firebaseAuth.getUid()).child("My Motorcycle")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        motorsList.clear();
                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            Motor motor = new Motor();
                            if(ds.child("motorRegistrationNumber").getValue()!= null  )
                                motor.setTime(ds.child("motorRegistrationNumber").getValue().toString());
                            if(ds.child("motorName").getValue()!=null)
                                motor.setMotorName(ds.child("motorName").getValue().toString());

                            motorsList.add(motor);

                        }

                        adapterMotor = new AdapterMotor(myMotor.this, motorsList);
                        listMotorTv.setAdapter(adapterMotor);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

    }
}