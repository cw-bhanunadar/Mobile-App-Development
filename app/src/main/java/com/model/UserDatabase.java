package com.model;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;

/**
 * Created by Bhanugoban Nadar on 1/26/2018.
 */

public class UserDatabase {
    Map<String,Object> user=new HashMap<>();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    static public String personName;
    String personEmail= FirebaseAuth.getInstance().getCurrentUser().getEmail();
    public boolean addUserToDatabase(GoogleSignInAccount acct)
    {
        personName = acct.getDisplayName();
        String personGivenName = acct.getGivenName();
        String personFamilyName = acct.getFamilyName();
        String personEmail2 = acct.getEmail();
        String personId = acct.getId();
        Uri personPhoto = acct.getPhotoUrl();

        user.put("email",personEmail2);
        user.put("name",personGivenName);
        user.put("location","undefined");
        user.put("bio","undefined");
        user.put("handle","undefined");
        user.put("phonenumber","undefined");
        DocumentReference docRef = db.collection("users").document(personEmail2);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d(TAG, "DocumentSnapshot data: " + task.getResult().getData());
                    } else {
                        db.collection("users").document(personEmail).set(user);
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });


        return true;
    }
    public boolean addUserToDatabase(FirebaseUser acct) {
        final String personEmail2=acct.getEmail();

        user.put("email",personEmail2);
        user.put("name","name");
        user.put("location","location");
        user.put("bio","bio");
        user.put("handle","username");
        user.put("phonenumber","phonenumber");
        DocumentReference docRef = db.collection("users").document(personEmail2);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d(TAG, "DocumentSnapshot data: " + task.getResult().getData());
                    } else {
                        db.collection("users").document(personEmail2).set(user);
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
        return true;
    }
    public boolean addReview(final String Restaurantname,int rating, String review)
    {
        final String personEmail= FirebaseAuth.getInstance().getCurrentUser().getEmail();
        DocumentReference docRef = db.collection("users").document(personEmail).collection("Reviews").document(Restaurantname);
        final Map<String,String> Review=new HashMap<>();
        Review.put("Review",review);
        Review.put("rating",Integer.toString(rating));
        Review.put("email",personEmail);
        Review.put("name",Restaurantname);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d(TAG, "DocumentSnapshot data: " + task.getResult().getData());
                    } else {

                        db.collection("users").document(personEmail).collection("Reviews").document(Restaurantname).set(Review);

                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
        return true;
    }
    public Map<String,Object> getDataOfUser(FirebaseUser firebaseUser)
    {

        String email=firebaseUser.getEmail();

        db.collection("users").document(email).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
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

                    } else {
                        Log.w(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
        return user;

    }
    public boolean editUser(String name,String handle,String phonenumber,String location,String bio)
    {
        Map<String,Object> user=new HashMap<>();
        personName=name;
        user.put("name",name);
        user.put("handle",handle);
        user.put("phonenumber",phonenumber);
        user.put("location",location);
        user.put("bio",bio);
        db.collection("users").document(personEmail).set(user);
        personName=name;
        Log.w("Edit",personName);
        return true;
    }
}
