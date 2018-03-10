package com.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.bhanu.controller.R;
import com.example.bhanu.controller.Review_Adapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.model.Review;

import java.util.ArrayList;

/**
 * Created by Bhanugoban Nadar on 1/21/2018.
 */

public class Reviews extends Fragment {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.reviews_fragment, container, false);
        final String personEmail= FirebaseAuth.getInstance().getCurrentUser().getEmail();
        DocumentReference PdocRef = db.collection("users").document(personEmail);
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
                        Review_Adapter adapter = new Review_Adapter(getActivity(), word);

                        ListView listView = (ListView) rootView.findViewById(R.id.Listp);

                        listView.setAdapter(adapter);
                        Log.w("Repeat","check");
                    }

                });
        return rootView;
    }
}
