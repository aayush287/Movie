package com.codinguniverse.movie.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ReviewModel implements Serializable {
    @SerializedName("author")
    private String author;
    @SerializedName("content")
    private String  content;

    public ReviewModel() {
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
