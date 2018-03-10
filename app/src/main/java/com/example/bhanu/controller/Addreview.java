package com.example.bhanu.controller;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.model.RestaurantDatabase;
import com.model.UserDatabase;

/**
 * Created by Bhanugoban Nadar on 2/15/2018.
 */

public class Addreview extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);
        Toolbar topToolBar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(topToolBar);
        ActionBar actionBar = getSupportActionBar();;
        actionBar.setDisplayHomeAsUpEnabled(true);
        final String nameUser= UserDatabase.personName;
        getSupportActionBar().setHomeButtonEnabled(true);
        final Button button =(Button)findViewById(R.id.restaurant_name);
        Bundle extras = getIntent().getExtras();
        final String id=extras.getString("placeId");;
        if (extras != null) {
            String value = extras.getString("name");
            //The key argument here must match that used in the other activity

            button.setText(value);
        }
        Button next=(Button) findViewById(R.id.add_review_to_restaurant);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText rating=(EditText)findViewById(R.id.rating);
                String val=rating.getText().toString();
                if(val.length()==0)
                {
                    Toast.makeText(Addreview.this,"unexpected rating",Toast.LENGTH_SHORT).show();
                }
                int p=Integer.parseInt(val);
                EditText rev=(EditText)findViewById(R.id.the_review);
                String review=rev.getText().toString();
                int l=review.length();
                if(val.length()==0||p>5||p<0||l==0)
                {
                    Toast.makeText(Addreview.this,"unexpected rating",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(Addreview.this,"review added",Toast.LENGTH_SHORT).show();
                    RestaurantDatabase restaurantDatabase=new RestaurantDatabase();
                    restaurantDatabase.addReview(nameUser,id,p,review);
                    UserDatabase userDatabase=new UserDatabase();
                    userDatabase.addReview(button.getText().toString(),p,review);
                    finish();
                }

            }
        });

    }
}
