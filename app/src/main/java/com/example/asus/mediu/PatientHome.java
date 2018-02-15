package com.example.asus.mediu;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class PatientHome extends AppCompatActivity {
    Button signout,openprofile;
    private FirebaseAuth mAuth;
    TextView username;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_home);

        mAuth = FirebaseAuth.getInstance(); // important Call
        signout = (Button)findViewById(R.id.signout);
        openprofile = (Button)findViewById(R.id.openprofile);
        username = (TextView) findViewById(R.id.tvName);




//Again check if the user is Already Logged in or Not
        if(mAuth.getCurrentUser() == null)
        {
//User NOT logged In
            finish();
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
        }

//Fetch the Display name of current User
        FirebaseUser user = mAuth.getCurrentUser();

        if (user != null) {
            username.setText("Welcome, " + user.getDisplayName());
        }


        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                finish();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });
        openprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(getApplicationContext(), PatientProfile.class));
            }
        });
    }
}
