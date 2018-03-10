package com.model;

/**
 * Created by Bhanugoban Nadar on 2/16/2018.
 */

public class Review {
    String name;
    String email;
    String rating;
    String review;

   public Review(String name,String email,String rating,String review)
    {
        this.name=name;
        this.email=email;
        this.rating=rating;
        this.review=review;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getRating() {
        return rating;
    }

    public String getReview() {
        return review;
    }
}
