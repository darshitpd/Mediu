package com.example.asus.mediu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MyHealth extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_health);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void history(View view) {
        Intent intent = new Intent(this, History.class);
        startActivity(intent);    }
    public void documents(View view) {
        Intent intent = new Intent(this, Documents.class);
        startActivity(intent);    }
    public void labtest(View view) {
        Intent intent = new Intent(this, LabTest.class);
        startActivity(intent);    }


}
