package com.codinguniverse.movie;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.codinguniverse.movie.adpters.MovieAdapter;
import com.codinguniverse.movie.models.MovieModel;
import com.codinguniverse.movie.repository.database.AppDatabase;
import com.codinguniverse.movie.viewmodels.MainViewModel;


import java.util.List;

public class MainActivity extends AppCompatActivity implements MovieAdapter.OnClickHandler {

    private MovieAdapter mMovieAdapter;
    private ProgressBar mLoadingIndicator;
    private MainViewModel mViewModel;
    private AppDatabase mAppDatabase;


    private static final String SORT_BY_POPULAR = "popular";
    private static final String SORT_BY_TOP_RATED = "top_rated";
    private static final String SORT_BY_FAVOURITE = "favourite";
    private static final String SORT_ORDER = "short_order";
    /*
        To regularly check which menu item is checked
        otherwise whenever there is change in any of
        live data it will change the adapter no matter
        which menu item is checked
     */
    private String SORT_BY = SORT_BY_POPULAR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView movieList = findViewById(R.id.movie_view);
        mMovieAdapter = new MovieAdapter(this);
        mLoadingIndicator = findViewById(R.id.loading_pb);
        mViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        mAppDatabase = AppDatabase.getInstance(this);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, calculateNoOfColumns(this));
        movieList.setLayoutManager(gridLayoutManager);

        mViewModel.initializePopularMovies();
        mViewModel.initializeTopRatedMovies();
        mViewModel.initializeFavMovie(mAppDatabase);
        movieList.setAdapter(mMovieAdapter);
        mLoadingIndicator.setVisibility(View.VISIBLE);
        if (savedInstanceState != null){
            SORT_BY = savedInstanceState.getString(SORT_ORDER);
        }else{
            SORT_BY = SORT_BY_POPULAR;
        }
        loadData(SORT_BY);

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(SORT_ORDER, SORT_BY);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_popular:
                if (!item.isChecked()) {
                    item.setChecked(true);
                    SORT_BY = SORT_BY_POPULAR;
                    mMovieAdapter.setList(null);
                    mLoadingIndicator.setVisibility(View.VISIBLE);
                    loadData(SORT_BY_POPULAR);
                    return true;
                }
            case R.id.action_top_rated:
                if (!item.isChecked()) {
                    item.setChecked(true);
                    SORT_BY = SORT_BY_TOP_RATED;
                    mMovieAdapter.setList(null);
                    mLoadingIndicator.setVisibility(View.VISIBLE);
                    loadData(SORT_BY_TOP_RATED);
                    return true;
                }
            case R.id.actoin_favourite:
                if (!item.isChecked()){
                    item.setChecked(true);
                    SORT_BY = SORT_BY_FAVOURITE;
                    mMovieAdapter.setList(null);
                    mLoadingIndicator.setVisibility(View.VISIBLE);
                    loadData(SORT_BY_FAVOURITE);
                    return true;
                }
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * This method will observe the movies data and make a network call if data is not
     * present
     * @param sortBy sort order to define whether user asked for popular movies or top rated
     *               movies
     */
    private void loadData(String sortBy) {
        switch (sortBy){
            case SORT_BY_POPULAR:
                mViewModel.getPopularMovies().observe(this, new Observer<List<MovieModel>>() {
                    @Override
                    public void onChanged(List<MovieModel> movieModels) {
                        if (SORT_BY.equals(SORT_BY_POPULAR)){
                            mMovieAdapter.setList(movieModels);
                            mLoadingIndicator.setVisibility(View.INVISIBLE);
                        }

                    }
                });
                break;
            case SORT_BY_TOP_RATED:
                mViewModel.getTopRatedMovies().observe(this, new Observer<List<MovieModel>>() {
                    @Override
                    public void onChanged(List<MovieModel> movieModels) {

                        if (SORT_BY.equals(SORT_BY_TOP_RATED)){
                            mMovieAdapter.setList(movieModels);
                            mLoadingIndicator.setVisibility(View.INVISIBLE);
                        }

                    }
                });
                break;
            case SORT_BY_FAVOURITE:
                mViewModel.getFavouriteMovies().observe(this, new Observer<List<MovieModel>>() {
                    @Override
                    public void onChanged(List<MovieModel> movieModels) {
                        if (SORT_BY.equals(SORT_BY_FAVOURITE)){
                            mMovieAdapter.setList(movieModels);
                            mLoadingIndicator.setVisibility(View.INVISIBLE);
                        }

                    }
                });
                break;
            default:
                throw new IllegalStateException("Sort order is not recognized");
        }
    }

    @Override
    public void onClick(MovieModel movie) {
        Intent intent = new Intent(this, MovieDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("movie", movie);
        intent.putExtras(bundle);
        startActivity(intent);
    }
    public  int calculateNoOfColumns(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        int scalingFactor = 200;
        int noOfColumns = (int) (dpWidth / scalingFactor);
        if(noOfColumns < 2)
            noOfColumns = 2;
        return noOfColumns;
    }

}