package com.codinguniverse.movie.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class TrailerResponse implements Serializable {
    @SerializedName("results")
    private List<TrailersModel> results;

    public TrailerResponse() {
    }

    public List<TrailersModel> getResults() {
        return results;
    }

    public void setResults(List<TrailersModel> results) {
        this.results = results;
    }
}
