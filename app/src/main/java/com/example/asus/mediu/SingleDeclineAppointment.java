package com.example.asus.mediu;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SingleDeclineAppointment extends AppCompatActivity {
    private DatabaseReference mDatabase, mPatientDatabase, mDocDatabase, mAptConfirmDocDatabase, mDeclinePatientDatabase;
    private FirebaseUser mCurrentUser;
    private ProgressDialog mProgressDialog;
    private TextView mReason, mDate, mTime, mDoctor_name, mStatus;
    private Button mConfirm, mDecline, mReschedule;
    private String patient_id, apt_id;
    private String reason, date, time, patientName, status, doctor_id,doctorName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_decline_appointment);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        apt_id = getIntent().getStringExtra("apt_id");
        mReason= (TextView)findViewById(R.id.tvReason);
        mDate= (TextView)findViewById(R.id.tvDate);
        mTime=(TextView)findViewById(R.id.tvTime);
        mDoctor_name=(TextView)findViewById(R.id.tvDoctorName);
        mStatus= (TextView)findViewById(R.id.tvStatus);
        mDecline = (Button)findViewById(R.id.btDeclintApt);

        mCurrentUser= FirebaseAuth.getInstance().getCurrentUser();
        mDatabase= FirebaseDatabase.getInstance().getReference().child("Appointments").child(apt_id);

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                reason= dataSnapshot.child("reason").getValue().toString();
                date= dataSnapshot.child("date").getValue().toString();
                time= dataSnapshot.child("time").getValue().toString();
                status= dataSnapshot.child("status").getValue().toString();
                patient_id= dataSnapshot.child("patient_id").getValue().toString();
                doctor_id=dataSnapshot.child("doctor_id").getValue().toString();
                doctorName= dataSnapshot.child("doctor_name").getValue().toString();

                mDoctor_name.setText(doctorName);
                mReason.setText(reason);
                mDate.setText(date);
                mTime.setText(time);
                mStatus.setText(status);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                mProgressDialog.dismiss();

            }
        });
    }
}
