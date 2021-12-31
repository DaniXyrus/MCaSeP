package com.example.mcasep.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mcasep.R;
import com.example.mcasep.activity.menuCar;
import com.example.mcasep.activity.menuMotor;
import com.example.mcasep.model.Car;
import com.example.mcasep.model.Motor;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class AdapterMotor extends RecyclerView.Adapter<AdapterMotor.HolderMotor> {

    private Context context;
    public ArrayList<Motor> motorsList;

    public AdapterMotor(Context context, ArrayList<Motor> motorsList) {
        this.context = context;
        this.motorsList = motorsList;
    }

    @NonNull
    @Override
    public HolderMotor onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.row_motor, parent, false);
        return new HolderMotor(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderMotor holder, int position) {

        Motor motor = motorsList.get(position);
        String register = motor.getTime();
        String name = motor.getMotorName();
        String time = motor.getTime();

        holder.registerTv.setText("Registration Number: "+register);

        holder.nameTv.setText("Name: "+name);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, menuMotor.class);
                intent.putExtra("motorRegistrationNumber", register);
                context.startActivity(intent);
            }
        });

        holder.deleteTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Delete")
                        .setMessage("Are you sure to delete this saved data?")
                        .setPositiveButton("DELETE", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                deleteSaved(time);
                            }
                        })
                        .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .show();

            }
        });

    }

    private void deleteSaved(String time) {

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.child(firebaseAuth.getUid()).child("My Motorcycle").child(time).removeValue()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        Toast.makeText(context,"Data Deleted", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        Toast.makeText(context,""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public int getItemCount() {
        return motorsList.size();
    }

    class HolderMotor extends RecyclerView.ViewHolder{

        private TextView  registerTv, nameTv, deleteTv;

        public HolderMotor(@NonNull View itemView) {
            super(itemView);

            registerTv = itemView.findViewById(R.id.registerTv);
            nameTv = itemView.findViewById(R.id.nameTv);
            deleteTv = itemView.findViewById(R.id.deleteTv);

        }
    }
}
