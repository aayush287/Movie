package com.codinguniverse.movie.viewmodels;

import android.content.Context;
import android.util.Log;
import android.widget.LinearLayout;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.codinguniverse.movie.models.MovieModel;
import com.codinguniverse.movie.repository.GetMovieData;
import com.codinguniverse.movie.repository.database.AppDatabase;

import java.util.List;

public class MainViewModel extends ViewModel {

    private MutableLiveData<List<MovieModel>> mPopularMovies;
    private MutableLiveData<List<MovieModel>> mTopRatedMovies;
    private LiveData<List<MovieModel>> mFavouriteMovies;

    private GetMovieData mGetMovieData;


    public void initializePopularMovies(){
        if (mPopularMovies != null){
            return;
        }

        mGetMovieData = GetMovieData.getInstance();

        mPopularMovies = mGetMovieData.getPopularMovies();
    }

    public void initializeTopRatedMovies(){
        if (mTopRatedMovies != null){
            return;
        }

        mGetMovieData = GetMovieData.getInstance();

        mTopRatedMovies = mGetMovieData.getTopRatedMovies();
    }

    public void initializeFavMovie(AppDatabase appDatabase){
        if (mFavouriteMovies != null){
            return;
        }

        mFavouriteMovies = appDatabase.movieDao().loadAllMovies();
    }

    public LiveData<List<MovieModel>> getFavouriteMovies(){
        return mFavouriteMovies;
    }
    public LiveData<List<MovieModel>> getPopularMovies() {
        return mPopularMovies;
    }

    public LiveData<List<MovieModel>> getTopRatedMovies() {
        return mTopRatedMovies;
    }
}
