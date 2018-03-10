package com.example.bhanu.controller;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.model.Review;
import com.model.UserDatabase;

import java.util.ArrayList;

/**
 * Created by Bhanugoban Nadar on 2/16/2018.
 */

public class Review_List extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    final String name=UserDatabase.personName;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.list_view);
        String placeId;
        Bundle extras = getIntent().getExtras();
        placeId = extras.getString("placeId");
        DocumentReference PdocRef = db.collection("Restaurant").document(placeId);
        PdocRef.collection("Reviews")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        ArrayList<Review> word=new ArrayList<>();
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot document : task.getResult()) {
                                Log.w("Multiple", document.getId() + " => " + document.getData());
                                String review=document.get("Review").toString();
                                String email=document.get("email").toString();
                                String name=document.get("name").toString();
                                String rating=document.get("rating").toString();
                                word.add(new Review(name,email,rating,review));
                            }
                        } else {
                            Log.w("Multiple", "Error getting documents: ", task.getException());
                        }
                        Review_Adapter adapter = new Review_Adapter(Review_List.this, word);

                        ListView listView = (ListView) findViewById(R.id.List);

                        listView.setAdapter(adapter);
                        Log.w("Repeat","check");
                    }

                });


    }
}
