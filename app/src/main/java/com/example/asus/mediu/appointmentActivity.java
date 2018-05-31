package com.example.asus.mediu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class appointmentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }
    public void openRequestedAptList(View view) {
        Intent intent = new Intent(this, RequestedAptListActivity.class);
        startActivity(intent);    }

    public void openConfirmedAptList(View view) {
        Intent intent = new Intent(this, ConfirmedAptListActivity.class);
        startActivity(intent);    }
}
