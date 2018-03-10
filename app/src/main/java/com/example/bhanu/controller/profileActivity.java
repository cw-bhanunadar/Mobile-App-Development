package com.example.bhanu.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.model.UserDatabase;

import java.util.Map;

import static android.content.ContentValues.TAG;

/**
 * Created by Bhanugoban Nadar on 1/21/2018.
 */

public class profileActivity extends FragmentActivity {
    private ViewPager mPager;
    private static final int NUM_PAGES = 3;
    private TabsPagerAdapter mPagerAdapter;
    FirebaseUser a=FirebaseAuth.getInstance().getCurrentUser();
    TextView location,bio,name;
    Map<String,Object>user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        mPager = (ViewPager) findViewById(R.id.pager);
        mPagerAdapter = new TabsPagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);
         a=FirebaseAuth.getInstance().getCurrentUser();
        String p=a.getEmail();
        Log.w("hello",p);
        ImageView img = (ImageView) findViewById(R.id.edit);
        img.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // your code here
                Intent i = new Intent(profileActivity.this, profileEdit.class);
                startActivity(i);
            }
        });
        UserDatabase userDatabase=new UserDatabase();
        user=userDatabase.getDataOfUser(a);
        setData();
    }
    @Override
    public void onBackPressed() {
        if (mPager.getCurrentItem() == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed();
        } else {
            // Otherwise, select the previous step.
            mPager.setCurrentItem(mPager.getCurrentItem() - 1);
        }
    }
    @Override
    protected void onStart() {
        super.onStart();



    }
    public void setData(){

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        location=(TextView)findViewById(R.id.profile_location);
        name=(TextView)findViewById(R.id.profile_name);
        bio=(TextView)findViewById(R.id.profile_bio);
        db.collection("users").document(a.getEmail()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.w(TAG, "DocumentSnapshot data: " + task.getResult().getData());
                        user.put("name",document.get("name"));
                        Log.w(TAG, "DocumentSnapshot data: "+document.get("name"));
                        user.put("location",document.get("location"));
                        Log.w(TAG, "DocumentSnapshot data: "+document.get("location"));
                        user.put("bio",document.get("bio"));
                        user.put("phonenumber",document.get("phonenumber"));
                        user.put("handle",document.get("handle"));
                        location.setText(document.get("location").toString());
                        name.setText(document.get("name").toString());
                        bio.setText(document.get("bio").toString());
                    } else {
                        Log.w(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });

    }
    /**
     * A simple pager adapter that represents 5 ScreenSlidePageFragment objects, in
     * sequence
     * */
}
