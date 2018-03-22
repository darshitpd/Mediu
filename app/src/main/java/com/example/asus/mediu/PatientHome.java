package com.example.asus.mediu;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class PatientHome extends AppCompatActivity {
    private FirebaseAuth mAuth;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_home);

        mAuth = FirebaseAuth.getInstance(); // important Call



//Again check if the user is Already Logged in or Not
        if(mAuth.getCurrentUser() == null)
        {
//User NOT logged In
            finish();
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
        }

//Fetch the Display name of current User
        FirebaseUser user = mAuth.getCurrentUser();

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home_menu, menu);
        return super.onCreateOptionsMenu(menu);


    }
    public void appointments(View view) {
        Intent intent = new Intent(this, Appointments.class);
        startActivity(intent);    }

    public void healthfeed(View view) {
        Intent intent = new Intent(this, DisplayBlogsActivity.class);
        startActivity(intent);    }

    public void myhealth(View view) {
        Intent intent = new Intent(this, MyHealth.class);
        startActivity(intent);    }
    public void openuserlist(View view){
        Intent intent = new Intent(this, UsersList.class);
        startActivity(intent);
    }
    public void openconnecteduserslist(View view){
        Intent intent = new Intent(this, ConnectedDoctorList.class);
        startActivity(intent);
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {


            case R.id.action_accountsettings:
                // Code to run when the settings item is clicked
                Intent i = new Intent(this, PatientAccountSettings.class);
                startActivity(i);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    public void onBackPressed(){
        final AlertDialog.Builder builder=new AlertDialog.Builder(PatientHome.this);
        builder.setMessage("Do you want to exit?");
        builder.setCancelable(true);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
