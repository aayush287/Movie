package com.codinguniverse.movie.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class ReviewResponse implements Serializable {
    @SerializedName("results")
    private List<ReviewModel> results;


    public ReviewResponse() {
    }

    public ReviewResponse(List<ReviewModel> results) {
        this.results = results;
    }

    public List<ReviewModel> getResults() {
        return results;
    }

    public void setResults(List<ReviewModel> results) {
        this.results = results;
    }
}
