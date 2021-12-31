package com.example.mcasep.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mcasep.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class Account extends AppCompatActivity {

    private EditText edtTxtName,edtTxtPhoneNum;
    private Button btnSave;

    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        edtTxtName = findViewById(R.id.edtTxtName);
        edtTxtPhoneNum = findViewById(R.id.edtTxtPhoneNum);
        btnSave =  findViewById(R.id.btnSave);

        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please Wait");
        progressDialog.setCanceledOnTouchOutside(false);

        loadAccount();

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputData();

            }
        });
    }

    private void loadAccount() {

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.child(firebaseAuth.getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        String name = ""+dataSnapshot.child("name").getValue();
                        String phone = ""+dataSnapshot.child("phone").getValue();

                        edtTxtName.setText(name);
                        edtTxtPhoneNum.setText(phone);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    private String fullName, phoneNumber;

    private void inputData() {

        fullName = edtTxtName.getText().toString().trim();
        phoneNumber = edtTxtPhoneNum.getText().toString().trim();

        if(TextUtils.isEmpty(fullName)){
            Toast.makeText(this,"Enter Your Name", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(phoneNumber)){
            Toast.makeText(this,"Enter Your Phone Number", Toast.LENGTH_SHORT).show();
            return;
        }

        updateAccount();

    }

    private void updateAccount() {

        progressDialog.setMessage("Updating Account..");
        progressDialog.show();

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("name", "" + fullName);
        hashMap.put("phone", "" + phoneNumber);

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.child(firebaseAuth.getUid())
                .updateChildren(hashMap)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        progressDialog.dismiss();
                        Toast.makeText(Account.this, "Updated", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Account.this, MainActivity.class));

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        progressDialog.dismiss();
                        Toast.makeText(Account.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });
    }
}