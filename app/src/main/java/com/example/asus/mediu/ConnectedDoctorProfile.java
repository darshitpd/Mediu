package com.example.asus.mediu;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class ConnectedDoctorProfile extends AppCompatActivity {

    private ImageView mProfileImage;
    private TextView mProfileName,mSpecialization,mExp,mClinicAddress, mDescription, mWorkingTime;
    private Button mProfileDeclineReqBtn, mProfileMakeAppointment;
    private String docName;

    private DatabaseReference mUsersDatabase;
    private DatabaseReference mConnectDatabase;

    private FirebaseUser mCurrent_user;

    private int mCurrent_state;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connected_doctor_profile);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        final String user_id = getIntent().getStringExtra("user_id");

        mUsersDatabase = FirebaseDatabase.getInstance().getReference().child("Doctor_Users").child(user_id);
        mConnectDatabase = FirebaseDatabase.getInstance().getReference().child("ConnectedList");
        mCurrent_user= FirebaseAuth.getInstance().getCurrentUser();

        mProfileImage = (ImageView) findViewById(R.id.profile_image);
        mProfileName = (TextView) findViewById(R.id.profile_displayName);
        mSpecialization = (TextView)findViewById(R.id.profile_specialization);
        mExp  = (TextView)findViewById(R.id.profile_exp);
        mClinicAddress = (TextView)findViewById(R.id.profile_clinc_address);
        mDescription = (TextView)findViewById(R.id.profile_description);
        mWorkingTime =(TextView)findViewById(R.id.profile_working_time);
        mProfileDeclineReqBtn=(Button)findViewById(R.id.profile_decline_req_btn);
        mProfileMakeAppointment=(Button)findViewById(R.id.profile_make_appointment);

        mCurrent_state= 3;
        mUsersDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String firstname = dataSnapshot.child("firstname").getValue().toString();
                String lastname = dataSnapshot.child("lastname").getValue().toString();
                String display_name = firstname+" "+lastname;
                docName = display_name;
                String image = dataSnapshot.child("image").getValue().toString();

                String specialization = dataSnapshot.child("specialist").getValue().toString();
                String exp = dataSnapshot.child("experience").getValue().toString();
                String clinicAddress = dataSnapshot.child("address").getValue().toString();
                String description = dataSnapshot.child("description").getValue().toString();
                String workingTime = dataSnapshot.child("working").getValue().toString();

                mProfileName.setText(display_name);

                mSpecialization.setText(specialization);
                mExp.setText(exp);
                mClinicAddress.setText(clinicAddress);
                mDescription.setText(description);
                mWorkingTime.setText(workingTime);

                Picasso.with(ConnectedDoctorProfile.this).load(image).placeholder(R.drawable.default_avatar).into(mProfileImage);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mProfileDeclineReqBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mConnectDatabase.child(mCurrent_user.getUid()).child(user_id).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            mConnectDatabase.child(user_id).child(mCurrent_user.getUid()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        mCurrent_state = 0;
                                        Toast.makeText(ConnectedDoctorProfile.this, "Declined.",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }else {
                            Toast.makeText(ConnectedDoctorProfile.this, "Error in declining.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        mProfileMakeAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), MakeAppointmentActivity.class);
                intent.putExtra("EXTRA_DOCTOR_ID", user_id);
                intent.putExtra("EXTRA_DOCTOR_NAME", docName);
                startActivity(intent);
            }
        })
        ;
    }
}
