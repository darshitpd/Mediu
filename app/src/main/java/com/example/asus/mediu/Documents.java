package com.example.asus.mediu;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Documents extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_documents);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
