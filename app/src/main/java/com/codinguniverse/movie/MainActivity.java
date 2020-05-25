package com.codinguniverse.movie;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.codinguniverse.movie.adpters.MovieAdapter;
import com.codinguniverse.movie.models.MovieModel;
import com.codinguniverse.movie.utils.MovieUtils;
import com.codinguniverse.movie.utils.NetworkUtils;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MovieAdapter.OnClickHandler {

    private MovieAdapter mMovieAdapter;
    private ProgressBar mLoadingIndicator;


    private static final String SORT_BY_POPULAR = "popular";
    private static final String SORT_BY_TOP_RATED = "top_rated";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView movieList = findViewById(R.id.movie_view);
        mMovieAdapter = new MovieAdapter(this);
        mLoadingIndicator = findViewById(R.id.loading_pb);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        movieList.setLayoutManager(gridLayoutManager);

        movieList.setAdapter(mMovieAdapter);

        loadData(SORT_BY_POPULAR);
    }

    private void loadData(String sortBy){
        new FetchMovieData().execute(sortBy);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.action_popular:
                if (!item.isChecked()){
                    item.setChecked(true);
                    mMovieAdapter.setList(null);
                    loadData(SORT_BY_POPULAR);
                    return true;
                }
            case R.id.action_top_rated:
                if (!item.isChecked()){
                    item.setChecked(true);
                    mMovieAdapter.setList(null);
                    loadData(SORT_BY_TOP_RATED);
                    return true;
                }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(MovieModel movie) {
        Intent intent = new Intent(this, MovieDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("movie",movie);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    /**
     * This class will fetch data from TmDb api in background
     * thread
     */

    class FetchMovieData extends AsyncTask<String, Void, List<MovieModel>>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadingIndicator.setVisibility(View.VISIBLE);
        }

        @Override
        protected List<MovieModel> doInBackground(String... strings) {
             /*
                If length of strings is 0, that means there is no given way
                to fetch data in that case return null
             */
            if (strings.length == 0){
                return null;
            }

            String sortBy = strings[0];
            URL url = NetworkUtils.buildUrl(sortBy);
            try {
                String response = NetworkUtils.getHttpUrlConnectionResponse(url);
                /*
                    parse string to movie model object
                 */

                return MovieUtils.getParsedJsonString(response);
            }catch (IOException | JSONException e){
                e.printStackTrace();
                return null;
            }

        }

        @Override
        protected void onPostExecute(List<MovieModel> movieModels) {
            mLoadingIndicator.setVisibility(View.INVISIBLE);
            if (movieModels != null){
               mMovieAdapter.setList(movieModels);
            }

        }
    }
}
