package com.example.bhanu.controller;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.model.UserDatabase;

import java.util.Map;

/**
 * Created by Bhanugoban Nadar on 1/26/2018.
 */

public class profileEdit extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_edit);
        Toolbar topToolBar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(topToolBar);
        ActionBar actionBar = getSupportActionBar();;
        actionBar.setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        ////*******Id
        final UserDatabase userDatabase =new UserDatabase();
       Map<String,Object> user= userDatabase.getDataOfUser(FirebaseAuth.getInstance().getCurrentUser());

            final EditText editname = (EditText) findViewById(R.id.edit_name);
            //editname.setText(user.get("name").toString(), TextView.BufferType.EDITABLE);

            final EditText edithandle = (EditText) findViewById(R.id.edit_handle);
            //edithandle.setText(user.get("handle").toString(), TextView.BufferType.EDITABLE);

            final EditText editphonenumber = (EditText) findViewById(R.id.edit_phonenumber);
           // editphonenumber.setText(user.get("phonenumber").toString(), TextView.BufferType.EDITABLE);

            final EditText editLocation = (EditText) findViewById(R.id.edit_location);
          //  editLocation.setText(user.get("location").toString(), TextView.BufferType.EDITABLE);

            final EditText editbio = (EditText) findViewById(R.id.edit_bio);
           // editbio.setText(user.get("bio").toString(), TextView.BufferType.EDITABLE);

            Button button = findViewById(R.id.done_edit);
            button.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    // Code here executes on main thread after user presses button
                    boolean back = userDatabase.editUser(editname.getText().toString(), edithandle.getText().toString(), editphonenumber.getText().toString(), editLocation.getText().toString(), editbio.getText().toString());

                    if (back) {
                        finish();
                    }

                }
            });
        }

}
