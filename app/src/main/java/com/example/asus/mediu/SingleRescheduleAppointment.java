package com.example.asus.mediu;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SingleRescheduleAppointment extends AppCompatActivity {
    private DatabaseReference mDatabase, mPatientDatabase, mDocDatabase, mAptConfirmDocDatabase, mConfirmPatientDatabase;
    private FirebaseUser mCurrentUser;
    private ProgressDialog mProgressDialog;
    private TextView mReason, mDate, mTime, mDoctor_name, mStatus;
    private Button mConfirm;
    private String patient_id, apt_id;
    private String reason, date, time, patientName, status, doctor_id,doctorName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_reschedule_appointment);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        apt_id = getIntent().getStringExtra("apt_id");
        mReason= (TextView)findViewById(R.id.tvReason);
        mDate= (TextView)findViewById(R.id.tvDate);
        mTime=(TextView)findViewById(R.id.tvTime);
        mDoctor_name=(TextView)findViewById(R.id.tvDoctorName);
        mStatus= (TextView)findViewById(R.id.tvStatus);
        mConfirm = (Button)findViewById(R.id.btConfirmApt);


        mCurrentUser= FirebaseAuth.getInstance().getCurrentUser();
        mDatabase= FirebaseDatabase.getInstance().getReference().child("Appointments").child(apt_id);
        mDocDatabase = FirebaseDatabase.getInstance().getReference().child("Doctor_Users");
        mPatientDatabase= FirebaseDatabase.getInstance().getReference().child("Patient_Users").child(mCurrentUser.getUid()).child("Appointments").child("Reschedule").child(apt_id);
        mConfirmPatientDatabase= FirebaseDatabase.getInstance().getReference().child("Patient_Users").child(mCurrentUser.getUid()).child("Appointments").child("Confirm").child(apt_id);

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                reason= dataSnapshot.child("reason").getValue().toString();
                date= dataSnapshot.child("date").getValue().toString();
                time= dataSnapshot.child("time").getValue().toString();
                patientName= dataSnapshot.child("patient_name").getValue().toString();
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

        mConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDatabase.child("status").setValue("confirmed");
                mPatientDatabase.removeValue();
                mConfirmPatientDatabase.child("reason").setValue(reason);
                mConfirmPatientDatabase.child("date").setValue(date);
                mConfirmPatientDatabase.child("time").setValue(time);
                mConfirmPatientDatabase.child("doctor_id").setValue(doctor_id);
                mConfirmPatientDatabase.child("doctor_name").setValue(doctorName);
                mConfirmPatientDatabase.child("status").setValue("confirmed");


                final DatabaseReference mDocConfirmDatabase = mDocDatabase.child(doctor_id).child("Appointments").child("Confirm").child(apt_id);
                mDocConfirmDatabase.child("reason").setValue(reason);
                mDocConfirmDatabase.child("date").setValue(date);
                mDocConfirmDatabase.child("time").setValue(time);
                mDocConfirmDatabase.child("patient_id").setValue(patient_id);
                mDocConfirmDatabase.child("patient_name").setValue(patientName);
                mDocConfirmDatabase.child("status").setValue("confirmed");


                Toast.makeText(SingleRescheduleAppointment.this, "Appointment Confirmed", Toast.LENGTH_SHORT);
                startActivity(new Intent(SingleRescheduleAppointment.this, PatientHome.class));
            }
        });
    }
}
