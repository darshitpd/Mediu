package com.example.asus.mediu;

import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class ProfileActivity extends AppCompatActivity {


    private ImageView mProfileImage;
    private TextView mProfileName, mProfileSpecialist;
    private Button mProfileSendReqBtn;

    private DatabaseReference mUsersDatabase;
    private DatabaseReference mUsersInfoDatabase;
    private DatabaseReference mConnectReqDatabase;

    private FirebaseUser mCurrent_user;

    private int mCurrent_state;  // 0= Connect Req not sent   1= Connect Req sent   2= Connected


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        final String user_id = getIntent().getStringExtra("user_id");

        mUsersDatabase = FirebaseDatabase.getInstance().getReference().child("Doctor_Users").child(user_id);
        mUsersInfoDatabase = FirebaseDatabase.getInstance().getReference().child("Patient_Users");
        mConnectReqDatabase = FirebaseDatabase.getInstance().getReference().child("Connect_Req");
        mCurrent_user= FirebaseAuth.getInstance().getCurrentUser();

        mProfileImage = (ImageView) findViewById(R.id.profile_image);
        mProfileName = (TextView) findViewById(R.id.profile_displayName);
        mProfileSpecialist = (TextView) findViewById(R.id.profile_specialist);
        mProfileSendReqBtn = (Button) findViewById(R.id.profile_send_req_btn);
        mCurrent_state = 0;

        mUsersDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String firstname = dataSnapshot.child("firstname").getValue().toString();
                String lastname = dataSnapshot.child("lastname").getValue().toString();
                String display_name = firstname+" "+lastname;
                String specialist = dataSnapshot.child("specialist").getValue().toString();
                String image = dataSnapshot.child("image").getValue().toString();

                mProfileName.setText(display_name);
                mProfileSpecialist.setText(specialist);

                Picasso.with(ProfileActivity.this).load(image).placeholder(R.drawable.default_avatar).into(mProfileImage);

                //--------------- FRIENDS LIST / REQUEST FEATURE -----

                mConnectReqDatabase.child(mCurrent_user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        if(dataSnapshot.hasChild(user_id)){

                            String req_type = dataSnapshot.child(user_id).child("request_type").getValue().toString();
//FOR DOCTOR SIDE
//                            if(req_type.equals("received")){
//
//                                mCurrent_state = 3;
//                                mProfileSendReqBtn.setText("Accept Friend Request");
//
//                                mDeclineBtn.setVisibility(View.VISIBLE);
//                                mDeclineBtn.setEnabled(true);
//
//
//                            }else
                                if(req_type.equals("sent")) {

                                mCurrent_state = 1;
                                mProfileSendReqBtn.setText("Cancel Friend Request");

                            }
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mProfileSendReqBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //---------------SEND CONNECT REQUEST ---------------
                if (mCurrent_state == 0){
                    mConnectReqDatabase.child(mCurrent_user.getUid()).child(user_id).child("request_type").setValue("sent").addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                mConnectReqDatabase.child(user_id).child(mCurrent_user.getUid()).child("request_type").setValue("received").addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){
                                            mUsersInfoDatabase.addValueEventListener(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(DataSnapshot dataSnapshot) {
                                                    String firstname= dataSnapshot.child(mCurrent_user.getUid()).child("firstname").getValue().toString();
                                                    String lastname= dataSnapshot.child(mCurrent_user.getUid()).child("lastname").getValue().toString();
                                                    String fullname = firstname+" "+lastname;
                                                    String image = dataSnapshot.child(mCurrent_user.getUid()).child("image").getValue().toString();
                                                    Toast.makeText(ProfileActivity.this, fullname,
                                                            Toast.LENGTH_SHORT).show();
                                                    mConnectReqDatabase.child(user_id).child(mCurrent_user.getUid()).child("name").setValue(fullname);
                                                    mConnectReqDatabase.child(user_id).child(mCurrent_user.getUid()).child("image").setValue(image);
                                                }

                                                @Override
                                                public void onCancelled(DatabaseError databaseError) {

                                                }
                                            });

                                            mCurrent_state = 1;
                                            mProfileSendReqBtn.setText("CANCEL CONNECT REQUEST");
                                            Toast.makeText(ProfileActivity.this, "Connect Request Sent.",
                                                    Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            }else {
                                Toast.makeText(ProfileActivity.this, "Error in sending request.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }

                //--------------CANCEL CONNECT REQUEST--------------
                if (mCurrent_state == 1){
                    mConnectReqDatabase.child(mCurrent_user.getUid()).child(user_id).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                mConnectReqDatabase.child(user_id).child(mCurrent_user.getUid()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){
                                            mCurrent_state = 0;
                                            mProfileSendReqBtn.setText("SEND CONNECT REQUEST");
                                            Toast.makeText(ProfileActivity.this, "Connect Request Cancelled.",
                                                    Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            }else {
                                Toast.makeText(ProfileActivity.this, "Error in cancelling request.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }

            }
        });

    }
}
