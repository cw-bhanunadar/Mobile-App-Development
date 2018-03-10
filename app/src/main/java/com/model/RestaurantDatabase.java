package com.model;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.location.places.Place;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;

/**
 * Created by Bhanugoban Nadar on 1/25/2018.
 */

public class RestaurantDatabase {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String req="https://maps.googleapis.com/maps/api/place/details/json?placeid=";
    String apikey="&key=AIzaSyA_PUIMp_dv0hwSIDAUIvooEVahgJktUIU";
    String real;
    public boolean AddtoDataBase(Place place)
    {

        final Map<String,Object> restaurant=new HashMap<>();
        restaurant.put("name",place.getName());
        restaurant.put("address",place.getAddress());
        restaurant.put("rating",0);
        restaurant.put("small_address",place.getLocale());
        restaurant.put("been_here",0);
        restaurant.put("reviews",0);
        restaurant.put("bookmark",0);
        restaurant.put("sum",0);
        restaurant.put("average_cost",500);
        restaurant.put("phonenumber",place.getPhoneNumber());
        restaurant.put("placeid",place.getId());

        final String id =place.getId();
        DocumentReference docRef = db.collection("Restaurant").document(place.getId());
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d(TAG, "DocumentSnapshot data: " + task.getResult().getData());
                    } else {

                        db.collection("Restaurant").document(id).set(restaurant);

                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
        return true;
    }
    public boolean incrementBeen_here(String placeId)
    {
        final String pl=placeId;
        DocumentReference PdocRef = db.collection("Restaurant").document(placeId);

        PdocRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d(TAG, "DocumentSnapshot data: " + task.getResult().getData());
                        int val=Integer.parseInt(document.get("been_here").toString());
                        DocumentReference ref =db.collection("Restaurant").document(pl);
                        Map <String,Object> tp=new HashMap();
                        tp.put("been_here",(val+1));
                        ref
                                .update(tp)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Log.d(TAG, "DocumentSnapshot successfully updated!");
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.w(TAG, "Error updating document", e);
                                    }
                                });
                    } else {



                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });

        return true;
    }
    public boolean incrementBookMark(String placeId)
    {
        final String pl=placeId;
        DocumentReference PdocRef = db.collection("Restaurant").document(placeId);

        PdocRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d(TAG, "DocumentSnapshot data: " + task.getResult().getData());
                        int val=Integer.parseInt(document.get("bookmark").toString());
                        DocumentReference ref =db.collection("Restaurant").document(pl);
                        Map <String,Object> tp=new HashMap();
                        tp.put("bookmark",(val+1));
                        ref
                                .update(tp)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Log.d(TAG, "DocumentSnapshot successfully updated!");
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.w(TAG, "Error updating document", e);
                                    }
                                });
                    } else {



                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });

        return true;
    }
    public boolean addReview(String Name, String placeId, int rating, final String review)
    {
        final String personEmail= FirebaseAuth.getInstance().getCurrentUser().getEmail();
        final String pl=placeId;
        final String name=Name;
        final int ratin=rating;


        DocumentReference docRef = db.collection("Restaurant").document(placeId).collection("Reviews").document(personEmail);
        final Map<String,String> Review=new HashMap<>();
        Review.put("Review",review);
        Review.put("rating",Integer.toString(rating));
        Review.put("email",personEmail);
        Review.put("name",Name);

        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d(TAG, "DocumentSnapshot data: " + task.getResult().getData());
                    } else {

                        db.collection("Restaurant").document(pl).collection("Reviews").document(personEmail).set(Review);

                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
        DocumentReference PdocRef = db.collection("Restaurant").document(placeId);

        PdocRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d(TAG, "DocumentSnapshot data: " + task.getResult().getData());
                        int val=Integer.parseInt(document.get("been_here").toString());
                        float rat=Float.parseFloat(document.get("rating").toString());
                        int reviews=Integer.parseInt(document.get("reviews").toString());
                        float sum=Float.parseFloat(document.get("sum").toString());
                        DocumentReference ref =db.collection("Restaurant").document(pl);
                        Map <String,Object> tp=new HashMap();
                        sum+=ratin;
                        tp.put("been_here",(val+1));
                        if(reviews>1)
                        tp.put("rating",String.format("%.1f",(1.0*sum)/(reviews+1)));
                        else
                            tp.put("rating",Integer.toString(ratin));

                        tp.put("reviews",(reviews+1));
                        ref
                                .update(tp)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Log.d(TAG, "DocumentSnapshot successfully updated!");
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.w(TAG, "Error updating document", e);
                                    }
                                });
                    } else {



                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });

        return true;
    }

}
