package com.example.asus.mediu;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class LabTest extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab_test);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Lab Test");

    }
}
