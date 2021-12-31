package com.example.mcasep.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.mcasep.R;
import com.example.mcasep.adapter.AdapterCar;
import com.example.mcasep.model.Car;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class myCar extends AppCompatActivity {

    private ImageButton addBt, backBtn;
    private RecyclerView listTv;

    private ArrayList<Car> carsList;
    private AdapterCar adapterCar;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_car);

        addBt = findViewById(R.id.addBt);
        backBtn = findViewById(R.id.backBtn);
        listTv = findViewById(R.id.listTv);

        firebaseAuth = FirebaseAuth.getInstance();

        loadMyCar();

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(myCar.this, MainActivity.class));
            }
        });

        addBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(myCar.this, addCar.class));
            }
        });
    }

    private void loadMyCar() {


        carsList = new ArrayList<>();

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users");
        ref.child(firebaseAuth.getUid()).child("My Car")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        carsList.clear();
                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            Car car = new Car();
                            if(ds.child("carRegistrationNumber").getValue()!= null  )
                                car.setTime(ds.child("carRegistrationNumber").getValue().toString());
                            if(ds.child("carName").getValue()!=null)
                                car.setCarName(ds.child("carName").getValue().toString());

                            carsList.add(car);

                        }

                        adapterCar = new AdapterCar(myCar.this, carsList);
                        listTv.setAdapter(adapterCar);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }
}