package com.example.asus.mediu;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class User_List_Pincode extends AppCompatActivity {


    private EditText mSearchField;
    private ImageButton mSearchBtn;


    private RecyclerView mUsersList;
    private DatabaseReference mUsersDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user__list__pincode);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Doctors");

        mUsersDatabase = FirebaseDatabase.getInstance().getReference().child("Doctor_Users");

        mUsersList = (RecyclerView) findViewById(R.id.users_list);
        mUsersList.setHasFixedSize(true);
        mUsersList.setLayoutManager(new LinearLayoutManager(this));

        mSearchField = (EditText) findViewById(R.id.search_field);
        mSearchBtn = (ImageButton) findViewById(R.id.search_btn);

        mSearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String searchText = mSearchField.getText().toString();

                firebaseUserSearch(searchText);

            }
        });

    }


    private void firebaseUserSearch(String searchText) {

        Toast.makeText(User_List_Pincode.this, "Started Search", Toast.LENGTH_LONG).show();

        Query firebaseSearchQuery = mUsersDatabase.orderByChild("pincode").startAt(searchText).endAt(searchText + "\uf8ff");

        FirebaseRecyclerAdapter<UserSingle, UsersList.UsersViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<UserSingle, UsersList.UsersViewHolder>(
                UserSingle.class, R.layout.activity_user_list_single, UsersList.UsersViewHolder.class,firebaseSearchQuery
        ) {
            @Override
            protected void populateViewHolder(UsersList.UsersViewHolder usersViewHolder, UserSingle users, int i) {


                usersViewHolder.setSpecialization(users.getPincode());
                usersViewHolder.setDisplayName(users.getName());
                usersViewHolder.setLastName(users.getLastname());
                usersViewHolder.setUserImage(users.getThumb_image(), getApplicationContext());


            }
        };

        mUsersList.setAdapter(firebaseRecyclerAdapter);
    }


    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerAdapter<UserSingle, UsersList.UsersViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<UserSingle, UsersList.UsersViewHolder>(
                UserSingle.class, R.layout.activity_user_list_single, UsersList.UsersViewHolder.class, mUsersDatabase
        ) {
            @Override
            protected void populateViewHolder(UsersList.UsersViewHolder usersViewHolder, UserSingle users, int i) {

                usersViewHolder.setSpecialization(users.getPincode());
                usersViewHolder.setDisplayName(users.getName());
                usersViewHolder.setLastName(users.getLastname());
                usersViewHolder.setUserImage(users.getThumb_image(), getApplicationContext());

                final String user_id = getRef(i).getKey();

                usersViewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent profileIntent = new Intent(User_List_Pincode.this, ProfileActivity.class);
                        profileIntent.putExtra("user_id", user_id);
                        startActivity(profileIntent);
                    }
                });

            }
        };
        mUsersList.setAdapter(firebaseRecyclerAdapter);
    }



    public static class UsersViewHolder extends RecyclerView.ViewHolder {

        View mView;

        public UsersViewHolder(View itemView) {
            super(itemView);

            mView = itemView;

        }

        public void setDisplayName(String name) {

            TextView userNameView = (TextView) mView.findViewById(R.id.user_single_name);
            userNameView.setText(name);

        }
        public void setLastName(String lastname){

            TextView userLastNameView = (TextView) mView.findViewById(R.id.user_single_lastname);
            userLastNameView.setText(lastname);


        }
        public void setSpecialization(String specialization){

            TextView userSpecializationView = (TextView)mView.findViewById(R.id.user_single_specialization);
            userSpecializationView.setText(specialization);

        }




        public void setUserImage(String thumb_image, Context ctx){

            CircleImageView userImageView = (CircleImageView) mView.findViewById(R.id.user_single_image);

            Picasso.with(ctx).load(thumb_image).placeholder(R.drawable.default_avatar).into(userImageView);

        }

    }





}
