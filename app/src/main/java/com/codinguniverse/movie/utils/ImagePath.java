package com.codinguniverse.movie.utils;

public class ImagePath {

    /**
     * This method takes the string of image path and build
     * a url string of image
     * @param imagePath in response
     * @return url to get image of movie
     */
    public static String buildImagePath(String imagePath){
        return "https://image.tmdb.org/t/p/" +
                "w185" +
                imagePath;
    }
}
