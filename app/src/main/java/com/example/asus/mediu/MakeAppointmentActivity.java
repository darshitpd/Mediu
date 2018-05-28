package com.example.asus.mediu;

import android.accessibilityservice.GestureDescription;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class MakeAppointmentActivity extends AppCompatActivity {
    private DatabaseReference mDatabase,mPatientDatabase, mDoctorDatabase;
    private FirebaseUser mCurrentUser;
    private TextView selectDate, selectTime;
    private EditText reason;
    private Button confirmApt;
    private String patientName;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private TimePickerDialog.OnTimeSetListener mTimeSetListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_appointment);

        final String doctor_id = getIntent().getStringExtra("EXTRA_DOCTOR_ID");
        final String doctorName = getIntent().getStringExtra("EXTRA_DOCTOR_NAME");
        mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();
        final String current_uid = mCurrentUser.getUid();
        mPatientDatabase = FirebaseDatabase.getInstance().getReference().child("Patient_Users").child(current_uid);
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Patient_Users").child(current_uid).child("Appointments");
        mDoctorDatabase = FirebaseDatabase.getInstance().getReference().child("Doctor_Users").child(doctor_id).child("Appointments").child("Request");

        selectDate= (TextView)findViewById(R.id.tvSelectDate);
        selectTime = (TextView)findViewById(R.id.tvSelectTime);
        reason= (EditText)findViewById(R.id.etReason);
        confirmApt = (Button) findViewById(R.id.btConfirmApt);

        mPatientDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String patientFirstName= dataSnapshot.child("firstname").getValue().toString();
                String patientLastName= dataSnapshot.child("lastname").getValue().toString();
                patientName= patientFirstName+" "+patientLastName;
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        selectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        MakeAppointmentActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,
                        year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });
        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String date = month + "/" + day + "/" + year;
                selectDate.setText(date);
                //mUserDatabase.child("dob").setValue(date);
            }
        };

        selectTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get Current Time
                final Calendar c = Calendar.getInstance();
                int mHour = c.get(Calendar.HOUR_OF_DAY);
                int mMinute = c.get(Calendar.MINUTE);
                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(MakeAppointmentActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mTimeSetListener, mHour, mMinute, false);
                timePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                timePickerDialog.show();

            }
        });
        mTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hr, int min) {

                String time= hr+ ":"+ min;
                selectTime.setText(time);
            }
        };

        confirmApt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String aptReason= reason.getEditableText().toString();
                String aptDate = selectDate.getText().toString();
                String aptTime = selectTime.getText().toString();
                final DatabaseReference newApt=mDatabase.push();

                newApt.child("reason").setValue(aptReason);
                newApt.child("date").setValue(aptDate);
                newApt.child("time").setValue(aptTime);
                newApt.child("doctor_id").setValue(doctor_id);
                newApt.child("doctor_name").setValue(doctorName);

                mDoctorDatabase.child(newApt.getKey()).child("reason").setValue(aptReason);
                mDoctorDatabase.child(newApt.getKey()).child("date").setValue(aptDate);
                mDoctorDatabase.child(newApt.getKey()).child("time").setValue(aptTime);
                mDoctorDatabase.child(newApt.getKey()).child("patient_id").setValue(current_uid);
                mDoctorDatabase.child(newApt.getKey()).child("patient_name").setValue(patientName);

//                final DatabaseReference newReqApt= mDoctorDatabase.push();

//                mUserDatabase.child("Appointments").child("Apt 1").child("reason").setValue(aptReason);
//                mUserDatabase.child("Appointments").child("Apt 1").child("date").setValue(aptDate);
//                mUserDatabase.child("Appointments").child("Apt 1").child("time").setValue(aptTime);
//                mUserDatabase.child("Appointments").child("Apt 1").child("doctor_id").setValue(doctor_id);
//                mUserDatabase.child("Appointments").child("Apt 1").child("doctor_name").setValue(doctorName);


            }
        });
    }
}
