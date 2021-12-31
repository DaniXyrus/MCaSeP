package com.example.mcasep.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mcasep.R;
import com.example.mcasep.activity.MainActivity;
import com.example.mcasep.activity.menuCar;
import com.example.mcasep.activity.myCar;
import com.example.mcasep.model.Car;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;

public class AdapterCar extends RecyclerView.Adapter<AdapterCar.HolderCar> {

    private Context context;
    public ArrayList<Car> carsList;

    public AdapterCar(Context context, ArrayList<Car> carsList) {
        this.context = context;
        this.carsList = carsList;
    }

    @NonNull
    @Override
    public HolderCar onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.row_car, parent, false);
        return new HolderCar(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderCar holder, int position) {

        Car car = carsList.get(position);
        String register = car.getTime();
        String name = car.getCarName();
        String time = car.getTime();

        holder.registerTv.setText("Registration Number: "+register);

        holder.nameTv.setText("Name: "+name);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, menuCar.class);
                intent.putExtra("carRegistrationNumber", register);
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
        reference.child(firebaseAuth.getUid()).child("My Car").child(time).removeValue()
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
        return carsList.size();
    }

    class HolderCar extends RecyclerView.ViewHolder{

        private TextView  registerTv, nameTv, deleteTv;

        public HolderCar(@NonNull View itemView) {
            super(itemView);

            registerTv = itemView.findViewById(R.id.registerTv);
            nameTv = itemView.findViewById(R.id.nameTv);
            deleteTv = itemView.findViewById(R.id.deleteTv);

        }
    }
}
