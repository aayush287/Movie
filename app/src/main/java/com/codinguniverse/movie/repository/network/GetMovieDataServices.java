package com.codinguniverse.movie.repository.network;

import com.codinguniverse.movie.models.MovieResponse;
import com.codinguniverse.movie.models.ReviewResponse;
import com.codinguniverse.movie.models.TrailerResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface GetMovieDataServices {

    /**
     * Query to get popular movies
     * @param apiKey provided by TmDb
     * @return movie response with top 20 movies of popular type
     */
    @GET("movie/popular")
    Call<MovieResponse> getPopularMovie(@Query("api_key") String apiKey);

    /**
     * Query to get top rated movies
     * @param apiKey provided by TmDb
     * @return movie response with top 20 movies of top rated type
     */
    @GET("movie/top_rated")
    Call<MovieResponse> getTopRatedMovies(@Query("api_key") String apiKey);

    /**
     * This Query gets the list of videos of given movie
     * @param id of movie
     * @param apiKey provided by TmDb
     * @return list of videos
     */
    @GET("movie/{id}/videos")
    Call<TrailerResponse> getMovieTrailers(@Path("id") int id, @Query("api_key") String apiKey);

    /**
     * This Query gets the list of movie review
     * @param id of movie for which review requested
     * @param apiKey provided by TmDb
     * @return list of reviews
     */
    @GET("movie/{id}/reviews")
    Call<ReviewResponse> getMovieReview(@Path("id") int id, @Query("api_key") String apiKey);

}
