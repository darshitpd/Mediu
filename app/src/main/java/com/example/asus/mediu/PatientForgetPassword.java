package com.example.asus.mediu;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class PatientForgetPassword extends AppCompatActivity {

    private EditText email;
    private Button submit;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_forget_password);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Forgot Password");


        email= (EditText)findViewById(R.id.etEmail);
        submit =(Button)findViewById(R.id.submit);
        mAuth = FirebaseAuth.getInstance();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userEmail = email.getText().toString().trim();
                if(userEmail.equals("")){
                    Toast.makeText(PatientForgetPassword.this, "Please enter your Email", Toast.LENGTH_SHORT).show();
                }else {
                    mAuth.sendPasswordResetEmail(userEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(PatientForgetPassword.this, "Password reset Email sent", Toast.LENGTH_SHORT).show();
                                finish();
                                startActivity(new Intent(getApplicationContext(), PatientLogin.class));
                            }
                            else {
                                Toast.makeText(PatientForgetPassword.this, "Please enter your registered Email", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

    }
}
