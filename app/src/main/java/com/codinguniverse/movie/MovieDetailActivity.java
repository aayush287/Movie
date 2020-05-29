package com.codinguniverse.movie;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.codinguniverse.movie.adpters.ReviewAdapter;
import com.codinguniverse.movie.adpters.TrailerAdapter;
import com.codinguniverse.movie.models.MovieModel;
import com.codinguniverse.movie.models.ReviewModel;
import com.codinguniverse.movie.models.TrailersModel;
import com.codinguniverse.movie.repository.database.AppDatabase;
import com.codinguniverse.movie.utils.AppExecutors;
import com.codinguniverse.movie.utils.ImagePath;
import com.codinguniverse.movie.viewmodels.MovieDetailViewModel;
import com.squareup.picasso.Picasso;

import java.util.List;


public class MovieDetailActivity extends AppCompatActivity implements TrailerAdapter.TrailerClickHandler {
    private static final String TAG = "MovieDetailActivity";
    
    private ImageView mPosterImage;
    private TextView mTitle;
    private TextView mOverview;
    private TextView mReleaseDate;
    private TextView mUserRating;
    private MovieDetailViewModel mViewModel;
    private TrailerAdapter mTrailerAdapter;
    private ReviewAdapter mReviewAdapter;
    private Button mMarkFavourite;
    private MovieModel movie;
    private AppDatabase appDatabase;
    private boolean isFavourite = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        mPosterImage = findViewById(R.id.poster_image);
        mTitle = findViewById(R.id.original_title);
        mOverview = findViewById(R.id.overview);
        mReleaseDate = findViewById(R.id.release_date);
        mUserRating = findViewById(R.id.user_rating);
        RecyclerView trailerView = findViewById(R.id.trailer_view);
        RecyclerView review = findViewById(R.id.review_view);
        mMarkFavourite = findViewById(R.id.mark_fav);

        /*
        Creating instance of adapters and setting layout
        managers to them
         */
        mTrailerAdapter = new TrailerAdapter(this);
        mReviewAdapter = new ReviewAdapter();

        appDatabase = AppDatabase.getInstance(this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        trailerView.setLayoutManager(linearLayoutManager);
        trailerView.setAdapter(mTrailerAdapter);

        LinearLayoutManager reviewLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        review.setLayoutManager(reviewLayoutManager);
        review.setAdapter(mReviewAdapter);

        movie = null;

       mViewModel = new ViewModelProvider(this).get(MovieDetailViewModel.class);
        if (getIntent() != null && getIntent().getExtras() != null){
            movie = (MovieModel) getIntent().getExtras().getSerializable("movie");
        }

       if (movie != null){
           populateUI();
           mViewModel.initializeTrailerRequest(movie.getId());
           mViewModel.initializeReviewData(movie.getId());
           mViewModel.initializeFavouriteMovie(appDatabase, movie.getId());
           observeTrailer();
           observeReview();
           observeFavourite();
       }else{
           String message = "Something wrong happened, Please Try Again!";
           Toast.makeText(this,message,Toast.LENGTH_LONG).show();
           finish();
       }



       mMarkFavourite.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               changeFavouriteStatus();
           }
       });


    }

    private void observeFavourite() {
        mViewModel.getMovieById().observe(this, new Observer<MovieModel>() {
            @Override
            public void onChanged(MovieModel movieModel) {
                if (movieModel == null){
                    mMarkFavourite.setText(getString(R.string.mark_fav));
                    isFavourite = false;
                }else {
                    mMarkFavourite.setText(getString(R.string.un_mark_fav));
                    isFavourite = true;
                }
            }
        });
    }

    private void changeFavouriteStatus() {
        if (isFavourite){
            AppExecutors.getInstance().diskIO().execute(new Runnable() {
                @Override
                public void run() {
                    appDatabase.movieDao().deleteMovie(movie);
                    isFavourite = false;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mMarkFavourite.setText(getString(R.string.un_mark_fav));
                        }
                    });
                }
            });
        }else {
            AppExecutors.getInstance().diskIO().execute(new Runnable() {
                @Override
                public void run() {
                    appDatabase.movieDao().insertMovie(movie);
                    isFavourite = true;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mMarkFavourite.setText(getString(R.string.mark_fav));
                        }
                    });
                }
            });
        }

    }

    private void observeReview() {
        mViewModel.getReviewLiveData().observe(this, new Observer<List<ReviewModel>>() {
            @Override
            public void onChanged(List<ReviewModel> reviewModels) {
                mReviewAdapter.setReviewList(reviewModels);
            }
        });
    }

    private void observeTrailer() {
        mViewModel.getTrailerLiveData().observe(this, new Observer<List<TrailersModel>>() {
            @Override
            public void onChanged(List<TrailersModel> trailersModels) {
                mTrailerAdapter.setTrailersList(trailersModels);
            }
        });
    }

    /*
        Populate the UI from given movie
     */
    private void populateUI(){
        if (movie.getOriginalTitle() != null && !movie.getOriginalTitle().isEmpty()){
            mTitle.setText(movie.getOriginalTitle());
        }else{
            mTitle.setText(R.string.default_txt);
        }

        if (movie.getPosterPath() != null && !movie.getPosterPath().isEmpty()){
            Picasso.get().load(ImagePath.buildImagePath(movie.getPosterPath())).into(mPosterImage);
        }

        if (movie.getOverview() != null && !movie.getOverview().isEmpty()){
            mOverview.setText(movie.getOverview());
        }else {
            mOverview.setText(R.string.default_txt);
        }

        if (movie.getReleaseDate() != null && !movie.getReleaseDate().isEmpty()){
            mReleaseDate.setText(movie.getReleaseDate());
        }else{
            mReleaseDate.setText(R.string.default_txt);
        }

        String voteAverage = Double.toString(movie.getVoteAverage());
        mUserRating.setText(voteAverage);
    }

    @Override
    public void onTrailerClickHandle(String videoId) {
        Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + videoId));
        Intent webIntent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("http://www.youtube.com/watch?v=" + videoId));
        try {
            this.startActivity(appIntent);
        } catch (ActivityNotFoundException ex) {
            this.startActivity(webIntent);
        }
    }
}
