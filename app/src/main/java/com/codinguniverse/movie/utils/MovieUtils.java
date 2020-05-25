package com.codinguniverse.movie.utils;

import com.codinguniverse.movie.models.MovieModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MovieUtils {

    /**
     * This method is used to to parse the string got from response of
     * movie database api and convert it to List of MovieModel
     * @param json response from TmDb api
     * @return list of movie model
     * @throws JSONException throws a json exception
     */

    public static List<MovieModel> getParsedJsonString(String json) throws JSONException {

        final  String RESULT = "results";
        final String  ORIGINAL_TITLE = "original_title";
        final String POSTER_PATH = "poster_path";
        final String OVERVIEW = "overview";
        final String USER_RATING = "vote_average";
        final String RELEASE_DATE = "release_date";
        final String DEFAULT_STRING = "No data found";

        JSONObject jsonObject = new JSONObject(json);

        if (!jsonObject.has(RESULT)){
            return null;
        }

        JSONArray resultArray = jsonObject.getJSONArray(RESULT);

        List<MovieModel> movieModelList = new ArrayList<>();

        for (int i = 0;i<resultArray.length();i++){
            /*
                Retrieving movie object from json array
             */
            JSONObject resultObject = resultArray.getJSONObject(i);

            /*
                Creating movieModel with default constructor and set
                the required field
             */

            MovieModel movieModel = new MovieModel();

            // retrieving title of movie and setting it to movie model
            String originalTitle = resultObject.optString(ORIGINAL_TITLE, DEFAULT_STRING);
            movieModel.setOriginalTitle(originalTitle);

            // retrieving poster path of the movie and setting it to
            // movie model
            String posterPath = resultObject.optString(POSTER_PATH, "");
            movieModel.setPosterPath(posterPath);

            // retrieving overview of movie and setting it to movie model
            String overview = resultObject.optString(OVERVIEW, DEFAULT_STRING);
            movieModel.setOverview(overview);

            // retrieving vote average of movie and setting it to movie model
            double voteAverage = resultObject.optDouble(USER_RATING, 0);
            movieModel.setVoteAverage(voteAverage);

            // retrieving release date of movie and setting it to movie model
            String releaseDate = resultObject.optString(RELEASE_DATE, DEFAULT_STRING);
            movieModel.setReleaseDate(releaseDate);

            movieModelList.add(movieModel);

        }



        return movieModelList;
    }

}
