package com.example.asus.mediu;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class Doctor_Certificate extends AppCompatActivity {
    private DatabaseReference mUserDatabase;
    private FirebaseUser mCurrentUser;
    private ImageView image_profile;
    private DatabaseReference mUsersDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor__certificate);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        final String user_id = getIntent().getStringExtra("user_id");

        image_profile = (ImageView)findViewById(R.id.doctor_certificate_pic);
        mUsersDatabase = FirebaseDatabase.getInstance().getReference().child("Doctor_Users").child(user_id);

        mUsersDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String image = dataSnapshot.child("degree_certificate").getValue().toString();

                Picasso.with(Doctor_Certificate.this).load(image).placeholder(R.drawable.default_avatar).into(image_profile);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }


        });
    }

}