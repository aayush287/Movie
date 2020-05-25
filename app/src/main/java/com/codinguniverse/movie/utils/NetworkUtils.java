package com.codinguniverse.movie.utils;

import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class NetworkUtils {

    /*
        Base Url for TmDb Api
     */
    private static final String BASE_URL = "https://api.themoviedb.org/3/";

    private static final String API_QUERY_PARAMETER = "api_key";
    private static final String API_KEY = "YOUR_API_KEY";
    private static final String MOVIE_PATH = "movie";


    /**
     * This method returns url build by sort order which is passed to it
     * @param sortBy parameter to which movies are sorted
     * @return returns a url
     */
    public static URL buildUrl(String sortBy){
       Uri uri = Uri.parse(BASE_URL).buildUpon()
               .appendPath(MOVIE_PATH)
               .appendPath(sortBy)
               .appendQueryParameter(API_QUERY_PARAMETER, API_KEY)
               .build();

       URL url = null;
       try {
           url = new URL(uri.toString());
       }catch (MalformedURLException e){
           e.printStackTrace();
       }

       return url;
    }

    /**
     * This method returns the entire result from the HTTP response.
     *
     * @param url The URL to fetch the HTTP response from.
     * @return The contents of the HTTP response.
     * @throws IOException Related to network and stream reading
     */
    public static String getHttpUrlConnectionResponse(URL url) throws IOException {
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = httpURLConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");
            if (scanner.hasNext()){
                return scanner.next();
            }else{
                return null;
            }
        }finally {
            httpURLConnection.disconnect();
        }
    }
}
