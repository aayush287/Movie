package com.codinguniverse.movie.utils;

import android.util.Log;

import static android.content.ContentValues.TAG;

public class YouTubeThumbnailPath {

    /**
     * This method takes the string of video id and build
     * a url string of image
     * @param videoId for which we want thumbnail
     * @return url to get image of movie
     */
    public static String buildThumbNailPath(String videoId){
        return "https://img.youtube.com" +
                "/vi/" +
                videoId +
                "/default.jpg";
    }
}
