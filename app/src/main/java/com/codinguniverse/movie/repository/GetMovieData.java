package com.codinguniverse.movie.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.codinguniverse.movie.models.MovieModel;
import com.codinguniverse.movie.models.MovieResponse;
import com.codinguniverse.movie.models.ReviewModel;
import com.codinguniverse.movie.models.ReviewResponse;
import com.codinguniverse.movie.models.TrailerResponse;
import com.codinguniverse.movie.models.TrailersModel;
import com.codinguniverse.movie.repository.network.GetMovieDataServices;
import com.codinguniverse.movie.repository.network.RetrofitInstance;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetMovieData {
    private static final String API_KEY = "80f9abf2cff2cd83ca0a3de930c9b6e6";

    private static GetMovieData getMovieData;

    private GetMovieDataServices mGetMovieDataService;

    public static GetMovieData getInstance(){
        if (getMovieData == null){
            getMovieData = new GetMovieData();
        }
        return getMovieData;
    }

    private GetMovieData(){
        mGetMovieDataService = RetrofitInstance.getInstance().create(GetMovieDataServices.class);
    }

    /**
     * Method which calls for the popular movies
     * @return list of top 20 popular movie
     */
    public MutableLiveData<List<MovieModel>> getPopularMovies(){

        final MutableLiveData<List<MovieModel>> mMovieList = new MutableLiveData<>();

        mGetMovieDataService.getPopularMovie(API_KEY).enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(@NonNull Call<MovieResponse> call,@NonNull Response<MovieResponse> response) {
                if (response.isSuccessful()){

                    if (response.body() != null && response.body().getResults() != null){
                        mMovieList.setValue(response.body().getResults());
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<MovieResponse> call,@NonNull Throwable t) {

            }
        });
        return mMovieList;
    }

    /**
     * Method which calls for the top rated movies
     * @return list of top 20 top rated movie
     */
    public MutableLiveData<List<MovieModel>> getTopRatedMovies(){

        final MutableLiveData<List<MovieModel>> mMovieList = new MutableLiveData<>();

        mGetMovieDataService.getTopRatedMovies(API_KEY).enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(@NonNull Call<MovieResponse> call,@NonNull Response<MovieResponse> response) {
                if (response.isSuccessful()){

                    if (response.body() != null && response.body().getResults() != null){
                        mMovieList.setValue(response.body().getResults());
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<MovieResponse> call,@NonNull Throwable t) {

            }
        });
        return mMovieList;
    }

    /**
     * Method which calls for videos of movie
     * @param movieId for which videos are requested
     * @return list of trailer models
     */
    public MutableLiveData<List<TrailersModel>> getMoviesTrailer(int movieId){
        final MutableLiveData<List<TrailersModel>> mTrailersList = new MutableLiveData<>();

        mGetMovieDataService.getMovieTrailers(movieId, API_KEY).enqueue(new Callback<TrailerResponse>() {
            @Override
            public void onResponse(@NonNull Call<TrailerResponse> call,@NonNull Response<TrailerResponse> response) {
                if (response.isSuccessful()){

                    if (response.body() != null && response.body().getResults() != null){
                       mTrailersList.setValue(response.body().getResults());
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<TrailerResponse> call,@NonNull Throwable t) {

            }
        });
        return mTrailersList;
    }

    /**
     * Method which call for review for the movie
     * @param movieId of movie for which review requested
     * @return list of reviews
     */
    public MutableLiveData<List<ReviewModel>> getMoviesReviews(int movieId){
        final MutableLiveData<List<ReviewModel>> mReviewList = new MutableLiveData<>();

        mGetMovieDataService.getMovieReview(movieId, API_KEY).enqueue(new Callback<ReviewResponse>() {
            @Override
            public void onResponse(@NonNull Call<ReviewResponse> call,@NonNull Response<ReviewResponse> response) {
                if (response.isSuccessful()){

                    if (response.body() != null && response.body().getResults() != null){
                        mReviewList.setValue(response.body().getResults());
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<ReviewResponse> call,@NonNull Throwable t) {

            }
        });

        return mReviewList;
    }
}
