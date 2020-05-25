package com.codinguniverse.movie;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.codinguniverse.movie.models.MovieModel;
import com.codinguniverse.movie.utils.ImagePath;
import com.squareup.picasso.Picasso;


public class MovieDetailActivity extends AppCompatActivity {

    private ImageView mPosterImage;
    private TextView mTitle;
    private TextView mOverview;
    private TextView mReleaseDate;
    private TextView mUserRating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        mPosterImage = findViewById(R.id.poster_image);
        mTitle = findViewById(R.id.original_title);
        mOverview = findViewById(R.id.overview);
        mReleaseDate = findViewById(R.id.release_date);
        mUserRating = findViewById(R.id.user_rating);

        MovieModel movie = null;

        if (getIntent() != null && getIntent().getExtras() != null){
            movie = (MovieModel) getIntent().getExtras().getSerializable("movie");
        }

       if (movie != null){
           populateUI(movie);
       }else{
           String message = "Something wrong happened, Please Try Again!";
           Toast.makeText(this,message,Toast.LENGTH_LONG).show();
           finish();
       }


    }

    private void populateUI(MovieModel movie){
        if (movie.getOriginalTitle() != null && !movie.getOriginalTitle().isEmpty()){
            mTitle.setText(movie.getOriginalTitle());
        }else{
            mTitle.setText(R.string.default_txt);
        }

        if (movie.getPosterPath() != null && !movie.getPosterPath().isEmpty()){
            Picasso.with(this).load(ImagePath.buildImagePath(movie.getPosterPath())).into(mPosterImage);
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
}
