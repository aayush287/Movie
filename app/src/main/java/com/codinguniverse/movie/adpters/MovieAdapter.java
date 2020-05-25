package com.codinguniverse.movie.adpters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.codinguniverse.movie.R;
import com.codinguniverse.movie.models.MovieModel;
import com.codinguniverse.movie.utils.ImagePath;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private List<MovieModel> mMovieModels;
    private Context mContext;

    /*
     * An on-click handler that we've defined to make it easy for an Activity to interface with
     * our RecyclerView
     */

    private final OnClickHandler mOnClickHandler;

    public MovieAdapter(OnClickHandler onClickHandler) {
        mOnClickHandler = onClickHandler;
    }

    public interface OnClickHandler{
        void onClick(MovieModel movie);
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_item, parent, false);
        mContext = parent.getContext();
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        String imagePath = mMovieModels.get(position).getPosterPath();
        holder.bind(imagePath);
    }

    @Override
    public int getItemCount() {
        if (mMovieModels == null){
            return 0;
        }
        return mMovieModels.size();
    }

    public void setList(List<MovieModel> movieModelList){
        mMovieModels = movieModelList;
        notifyDataSetChanged();
    }

    /**
     * Cache of the children views for a movie list item.
     */

    class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        final ImageView posterImage;
         MovieViewHolder(@NonNull View itemView) {
            super(itemView);

            posterImage = itemView.findViewById(R.id.movie_poster);
            itemView.setOnClickListener(this);
        }

        void bind(String imagePath){
            Picasso.with(mContext).load(ImagePath.buildImagePath(imagePath)).into(posterImage);
         }
        /**
         * This gets called by the child views during a click.
         *
         * @param v The View that was clicked
         */
        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            mOnClickHandler.onClick(mMovieModels.get(position));
        }
    }
}
