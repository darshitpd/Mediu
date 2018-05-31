package com.example.asus.mediu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DeclinedAptListActivity extends AppCompatActivity {
    private RecyclerView mReqAptList;
    private DatabaseReference mPatientUsersDatabase;
    private FirebaseUser mCurrent_user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_declined_apt_list);

        mCurrent_user= FirebaseAuth.getInstance().getCurrentUser();
        mPatientUsersDatabase = FirebaseDatabase.getInstance().getReference().child("Patient_Users").child(mCurrent_user.getUid()).child("Appointments").child("Declined");

        mReqAptList = (RecyclerView) findViewById(R.id.decline_apt_list);
        mReqAptList.setHasFixedSize(true);
        mReqAptList.setLayoutManager(new LinearLayoutManager(this));
    }
    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerAdapter<AptSingle, DeclinedAptListActivity.AptViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<AptSingle, DeclinedAptListActivity.AptViewHolder>(
                AptSingle.class, R.layout.activity_apt_request_received_list_single, DeclinedAptListActivity.AptViewHolder.class, mPatientUsersDatabase
        ) {
            @Override
            protected void populateViewHolder(DeclinedAptListActivity.AptViewHolder aptsViewHolder, AptSingle apts, int i) {

                aptsViewHolder.setDisplayName(apts.getName());
                aptsViewHolder.setDate(apts.getDate());

                final String apt_id = getRef(i).getKey();
                aptsViewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent profileIntent = new Intent(DeclinedAptListActivity.this, SingleDeclineAppointment.class);
                        profileIntent.putExtra("apt_id", apt_id);
                        startActivity(profileIntent);
                    }
                });

            }
        };
        mReqAptList.setAdapter(firebaseRecyclerAdapter);
    }


    public static class AptViewHolder extends RecyclerView.ViewHolder {

        View mView;

        public AptViewHolder(View itemView) {
            super(itemView);

            mView = itemView;

        }

        public void setDisplayName(String name) {

            TextView userNameView = (TextView) mView.findViewById(R.id.apt_single_name);
            userNameView.setText(name);
        }
        public void setDate(String date) {

            TextView userDateView = (TextView) mView.findViewById(R.id.apt_single_date);
            userDateView.setText(date);
        }
    }
}
