package com.example.asus.mediu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class patient_connect_doctor extends AppCompatActivity {

    private TextView openconnecteduserslist,openuserlist ,openuserlistpincode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_connect_doctor);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Connections");

        openconnecteduserslist = (TextView) findViewById(R.id.action_openconnecteduserslist);
        openuserlist = (TextView) findViewById(R.id.action_openuserlist);
        openuserlistpincode = (TextView)findViewById(R.id.action_openuserslistpincode);

        openuserlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),UsersList.class));
            }
        });


        openuserlistpincode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), User_List_Pincode.class));
            }
        });
        openconnecteduserslist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),ConnectedDoctorList.class));
            }
        });

    }
}
