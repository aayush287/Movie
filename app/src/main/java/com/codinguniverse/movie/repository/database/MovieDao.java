package com.codinguniverse.movie.repository.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.codinguniverse.movie.models.MovieModel;

import java.util.List;

@Dao
public interface MovieDao {

    @Query("SELECT * FROM fav_movie")
    LiveData<List<MovieModel>> loadAllMovies();

    @Insert
    void insertMovie(MovieModel movie);

    @Delete
    void deleteMovie(MovieModel movie);

    @Query("SELECT * FROM fav_movie WHERE id = :id")
    LiveData<MovieModel> loadMovieById(int id);

}
