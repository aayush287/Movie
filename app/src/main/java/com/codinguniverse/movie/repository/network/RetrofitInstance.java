package com.codinguniverse.movie.repository.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * A singleton class so that no multiple
 * connection open to API
 */
public class RetrofitInstance {
    private static Retrofit retrofit;

    // Base Url of TmDb Api
    private static final String BASE_URL = "https://api.themoviedb.org/3/";

    public static Retrofit getInstance(){
        if (retrofit == null){
            retrofit = new retrofit2.Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofit;
    }



}
