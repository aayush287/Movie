package com.codinguniverse.movie.viewmodels;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.codinguniverse.movie.adpters.TrailerAdapter;
import com.codinguniverse.movie.models.MovieModel;
import com.codinguniverse.movie.models.ReviewModel;
import com.codinguniverse.movie.models.TrailersModel;
import com.codinguniverse.movie.repository.GetMovieData;
import com.codinguniverse.movie.repository.database.AppDatabase;

import java.util.List;

import static android.content.ContentValues.TAG;

public class MovieDetailViewModel extends ViewModel  {
    private MutableLiveData<List<TrailersModel>> mTrailerLiveData;
    private MutableLiveData<List<ReviewModel>> mReviewLiveData;
    private GetMovieData mGetMovieData;
    private LiveData<MovieModel> mMovieById;

    public void initializeTrailerRequest(int movieId){
        if (mTrailerLiveData != null){
            return;
        }

        mGetMovieData = GetMovieData.getInstance();

        mTrailerLiveData = mGetMovieData.getMoviesTrailer(movieId);
    }

    public void initializeReviewData(int movieId){
        if (mReviewLiveData != null){
            return;
        }

        mGetMovieData = GetMovieData.getInstance();

        mReviewLiveData = mGetMovieData.getMoviesReviews(movieId);
    }

    public void initializeFavouriteMovie(AppDatabase appDatabase, int id){
        if (mMovieById != null){
            return;
        }

        mMovieById = appDatabase.movieDao().loadMovieById(id);
    }

    public LiveData<MovieModel> getMovieById(){
        return mMovieById;
    }

    public LiveData<List<ReviewModel>> getReviewLiveData(){
        return mReviewLiveData;
    }

    public LiveData<List<TrailersModel>> getTrailerLiveData() {
        return mTrailerLiveData;
    }
}
